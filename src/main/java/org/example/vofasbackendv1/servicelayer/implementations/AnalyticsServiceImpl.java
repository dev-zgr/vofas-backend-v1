package org.example.vofasbackendv1.servicelayer.implementations;

import org.example.vofasbackendv1.data_layer.entities.FeedbackEntity;
import org.example.vofasbackendv1.data_layer.repositories.FeedbackRepository;
import org.example.vofasbackendv1.presentationlayer.dto.*;
import org.example.vofasbackendv1.data_layer.enums.FeedbackStatusEnum;
import org.example.vofasbackendv1.servicelayer.interfaces.AnalyticsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final FeedbackRepository feedbackRepository;

    public AnalyticsServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public AnalyticsDTO getAnalytics(LocalDateTime startDate, LocalDateTime endDate, int intervalDays) {
        List<Object[]> results = feedbackRepository.findFeedbackAggregatedByInterval(startDate, endDate, intervalDays);

        Map<LocalDateTime, DataPointDTO> timeSeriesStatistics = new TreeMap<>();

        for (Object[] row : results) {
            Object dateObj = row[0];
            LocalDateTime intervalStart;

            if (dateObj instanceof java.sql.Timestamp) {
                intervalStart = ((java.sql.Timestamp) dateObj).toLocalDateTime();
            } else if (dateObj instanceof java.sql.Date) {
                intervalStart = ((java.sql.Date) dateObj).toLocalDate().atStartOfDay();
            } else {
                throw new IllegalArgumentException("Unexpected date type: " + dateObj.getClass());
            }

            IntervalAccumulator acc = new IntervalAccumulator();

            acc.positive = ((Number) row[1]).longValue();
            acc.neutral = ((Number) row[2]).longValue();
            acc.negative = ((Number) row[3]).longValue();

            acc.kiosksSource = ((Number) row[4]).longValue();
            acc.websitesSource = ((Number) row[5]).longValue();
            acc.staticQRSource = ((Number) row[6]).longValue();

            acc.kiosksMethod = ((Number) row[7]).longValue();
            acc.websitesMethod = ((Number) row[8]).longValue();
            acc.staticQRMethod = ((Number) row[9]).longValue();
            acc.dynamicQRMethod = ((Number) row[10]).longValue();

            acc.voiceType = ((Number) row[11]).longValue();
            acc.textType = ((Number) row[12]).longValue();

            acc.validated = ((Number) row[13]).longValue();
            acc.nonValidated = ((Number) row[14]).longValue();

            timeSeriesStatistics.put(intervalStart, acc.toDataPointDTO());
        }

        DataPointDTO aggregateStatistics = aggregateStatistics(timeSeriesStatistics);

        return new AnalyticsDTO(
                startDate,
                endDate,
                null,
                timeSeriesStatistics,
                aggregateStatistics
        );
    }

    private DataPointDTO aggregateStatistics(Map<LocalDateTime, DataPointDTO> timeSeriesStatistics) {
        long totalPositive = 0;
        long totalNeutral = 0;
        long totalNegative = 0;

        for (DataPointDTO dataPoint : timeSeriesStatistics.values()) {
            if (dataPoint.getSentiment() != null) {
                totalPositive += dataPoint.getSentiment().getNumberOfPositiveFeedbacks();
                totalNeutral += dataPoint.getSentiment().getNumberOfNeutralFeedbacks();
                totalNegative += dataPoint.getSentiment().getNumberOfNegativeFeedbacks();
            }
        }

        return new DataPointDTO(
                new SentimentPointDTO(totalPositive, totalNeutral, totalNegative),
                null,
                null,
                null,
                null
        );
    }

    private static class IntervalAccumulator {
        long positive = 0;
        long neutral = 0;
        long negative = 0;

        long kiosksSource = 0;
        long websitesSource = 0;
        long staticQRSource = 0;

        long kiosksMethod = 0;
        long websitesMethod = 0;
        long staticQRMethod = 0;
        long dynamicQRMethod = 0;

        long voiceType = 0;
        long textType = 0;

        long validated = 0;
        long nonValidated = 0;

        void addFeedback(FeedbackEntity fb) {
            if (fb.getSentiment() != null) {
                switch (fb.getSentiment()) {
                    case POSITIVE -> positive++;
                    case NEUTRAL -> neutral++;
                    case NEGATIVE -> negative++;
                }
            }

            if (fb.getFeedbackSource() != null) {
                switch (fb.getFeedbackSource().getSourceType()) {
                    case KIOSK -> kiosksSource++;
                    case WEBSITE -> websitesSource++;
                    case STATIC_QR -> staticQRSource++;
                }
            }

            if (fb.getMethodEnum() != null) {
                switch (fb.getMethodEnum()) {
                    case KIOSK -> kiosksMethod++;
                    case WEBSITE -> websitesMethod++;
                    case STATIC_QR -> staticQRMethod++;
                    case DYNAMIC_QR -> dynamicQRMethod++;
                }
            }

            if (fb.getTypeEnum() != null) {
                switch (fb.getTypeEnum()) {
                    case VOICE -> voiceType++;
                    case TEXT -> textType++;
                }
            }

            if (fb.getFeedbackStatus() != null) {
                if (fb.getFeedbackStatus() == FeedbackStatusEnum.READY) {
                    validated++;
                } else {
                    nonValidated++;
                }
            }
        }

        DataPointDTO toDataPointDTO() {
            return new DataPointDTO(
                    new SentimentPointDTO(positive, neutral, negative),
                    new FeedbackSourcePointDTO(kiosksSource, websitesSource, staticQRSource),
                    new FeedbackMethodPointDTO(kiosksMethod, websitesMethod, staticQRMethod, dynamicQRMethod),
                    new FeedbackTypePointDTO(voiceType, textType),
                    new FeedbackValidationPointDTO(validated, nonValidated)
            );
        }
    }
}
