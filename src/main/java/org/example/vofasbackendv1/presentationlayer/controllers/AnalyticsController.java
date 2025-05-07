package org.example.vofasbackendv1.presentationlayer.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.example.vofasbackendv1.presentationlayer.dto.AnalyticsDTO;
import org.example.vofasbackendv1.presentationlayer.dto.BaseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.UserDTO;
import org.example.vofasbackendv1.servicelayer.interfaces.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Analytics API endpoints", description = "Analytics API endpoints")
@RestController
@RequestMapping(value = "/vofas/api/v1", produces = "application/json")
@CrossOrigin(origins = "*")
public class AnalyticsController {
    private AnalyticsService analyticsService;

    @Autowired
    public AnalyticsController (AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }


    @Operation(summary = "Fetch Analytics Data", description = "Fetch analytics data between start and end date with given interval in days")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "204", description = "HTTP Status No Content"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @GetMapping(path = "/analytics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<AnalyticsDTO>> getAnalytics(
            @RequestParam("start-date") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDateTime startDate,
            @RequestParam("end-date") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDateTime endDate,
            @RequestParam("interval-days") @Min(1) int intervalDays
    ) {
        return null;
        //TODO this controller needs to be implemented
    }

}
