package org.example.vofasbackendv1.servicelayer.interfaces;

import org.example.vofasbackendv1.presentationlayer.dto.AnalyticsDTO;

import java.time.LocalDateTime;

public interface AnalyticsService {
    AnalyticsDTO getAnalytics(LocalDateTime startDate, LocalDateTime endDate,  int intervalDays);
}
