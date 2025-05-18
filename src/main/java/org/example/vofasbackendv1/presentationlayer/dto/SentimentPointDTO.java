package org.example.vofasbackendv1.presentationlayer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "SentimentPointDTO",
        description = "Contains the count of feedback entries classified by sentiment: positive, neutral, or negative."
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SentimentPointDTO {
    private Long numberOfPositiveFeedbacks;
    private Long numberOfNeutralFeedbacks;
    private Long numberOfNegativeFeedbacks;
}
