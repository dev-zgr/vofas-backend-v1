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

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "SentimentPointDTO",
        description = "Contains the count of feedback entries classified by sentiment: positive, neutral, or negative."
)
@Data
@AllArgsConstructor
@NoArgsConstructor
class SentimentPointDTO {
    private Long numberOfPositiveFeedbacks;
    private Long numberOfNeutralFeedbacks;
    private Long numberOfNegativeFeedbacks;
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "FeedbackSourcePointDTO",
        description = "Details the number of feedbacks received from different sources like kiosks, websites, and static QR codes."
)
@Data
@AllArgsConstructor
@NoArgsConstructor
class FeedbackSourcePointDTO {
    private Long numberOfKiosks;
    private Long numberOfWebsites;
    private Long numberOfStaticQRCodes;
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "FeedbackMethodPointDTO",
        description = "Represents feedback distribution by method such as kiosk, website, static QR, and dynamic QR codes."
)
@Data
@AllArgsConstructor
@NoArgsConstructor
class FeedbackMethodPointDTO {
    private Long numberOfKiosks;
    private Long numberOfWebsites;
    private Long numberOfStaticQRCodes;
    private Long numberOfDynamicQRCodes;
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "FeedbackTypePointDTO",
        description = "Shows how feedbacks are categorized by input type, e.g., voice or text."
)
@Data
@AllArgsConstructor
@NoArgsConstructor
class FeedbackTypePointDTO {
    private Long numberOfVoiceFeedbacks;
    private Long numberOfTextFeedbacks;
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "FeedbackValidationPointDTO",
        description = "Captures feedback counts based on validation status: validated or non-validated."
)
@Data
@AllArgsConstructor
@NoArgsConstructor
class FeedbackValidationPointDTO {
    private Long numberOfValidatedFeedbacks;
    private Long numberOfNonValidatedFeedbacks;
}