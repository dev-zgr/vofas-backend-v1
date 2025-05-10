package org.example.vofasbackendv1.presentationlayer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "FeedbackRequestDTO",
        description = "Schema representing common feedback requests"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequestDTO {
    MultipartFile content;

    @NotNull
    @Min(0)
    Long feedbackSourceId;

    @NotNull
    @Pattern(regexp = "^(WEBSITE|STATIC_QR)$", message = "Invalid method type")
    @Schema(description = "Type of the source", example = "STATIC_QR")
    private String feedbackMethod;

    @NotNull
    @Pattern(regexp = "^(VOICE|TEXT)$", message = "Invalid source type")
    @Schema(description = "Type of the source", example = "VOICE")
    private String feedbackType;

    private UUID validationToken;
}
