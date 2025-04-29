package org.example.vofasbackendv1.presentationlayer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vofasbackendv1.data_layer.enums.FeedbackSourceStateEnum;
import org.example.vofasbackendv1.data_layer.enums.SourceTypeEnum;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "FeedbackSourceDTO",
    description = "Schema representing common feedback source fields"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackSourceDTO {

    @Schema(description = "Unique identifier of the feedback source", example = "1")
    private Long feedbackSourceId;

    @NotBlank
    @Size(min = 2, max = 32)
    @Schema(description = "Name of the feedback source", example = "Entrance Kiosk")
    private String sourceName;

    @NotNull
    @Schema(description = "Type of the source", example = "STATIC_QR")
    private SourceTypeEnum sourceType;

    @NotBlank
    @Size(min = 2, max = 255)
    @Schema(description = "Description of the feedback source", example = "QR code placed at the main gate")
    private String description;

    @Schema(description = "Date and time when the source was created", example = "2025-04-28T10:15:30")
    private LocalDateTime createdAt;

    @NotNull
    @Schema(description = "Current state of the feedback source", example = "ACTIVE")
    private FeedbackSourceStateEnum state;
}
