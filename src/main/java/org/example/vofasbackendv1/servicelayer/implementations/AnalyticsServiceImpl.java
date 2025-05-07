package org.example.vofasbackendv1.servicelayer.implementations;

import org.example.vofasbackendv1.presentationlayer.dto.AnalyticsDTO;
import org.example.vofasbackendv1.servicelayer.interfaces.AnalyticsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    @Override
    public AnalyticsDTO getAnalytics(LocalDateTime startDate, LocalDateTime endDate, int intervalDays) {
        return null;
        //TODO this method needs to be implemented
    }
}
