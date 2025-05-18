package org.example.vofasbackendv1.presentationlayer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "FeedbackValidationPointDTO",
        description = "Captures feedback counts based on validation status: validated or non-validated."
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackValidationPointDTO {
    private Long numberOfValidatedFeedbacks;
    private Long numberOfNonValidatedFeedbacks;
}
