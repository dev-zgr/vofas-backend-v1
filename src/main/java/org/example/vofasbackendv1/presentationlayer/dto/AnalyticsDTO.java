package org.example.vofasbackendv1.presentationlayer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "Analytics DTO",
        description = "Schema to represent analytics over a certain period of time"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime endDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime interval;

    private Map<LocalDateTime, DataPointDTO> timeSeriesStatistics;

    private DataPointDTO aggregateStatistics;

}
