package org.example.vofasbackendv1.presentationlayer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "FeedbackTypePointDTO",
        description = "Shows how feedbacks are categorized by input type, e.g., voice or text."
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackTypePointDTO {
    private Long numberOfVoiceFeedbacks;
    private Long numberOfTextFeedbacks;
}
