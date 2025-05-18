package org.example.vofasbackendv1.presentationlayer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "DataPointDTO",
        description = "Encapsulates feedback statistics over a time period, grouped by sentiment, source, method, type, and validation."
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataPointDTO {
    private SentimentPointDTO sentiment;
    private FeedbackSourcePointDTO source;
    private FeedbackMethodPointDTO method;
    private FeedbackTypePointDTO type;
    private FeedbackValidationPointDTO validation;
}