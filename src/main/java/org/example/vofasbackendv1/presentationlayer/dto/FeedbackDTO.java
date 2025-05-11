package org.example.vofasbackendv1.presentationlayer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vofasbackendv1.data_layer.enums.FeedbackMethodEnum;
import org.example.vofasbackendv1.data_layer.enums.FeedbackStatusEnum;
import org.example.vofasbackendv1.data_layer.enums.FeedbackTypeEnum;
import org.example.vofasbackendv1.data_layer.enums.SentimentStateEnum;

import java.time.LocalDateTime;

@Schema(
        name = "FeedbackDTO",
        description = "Schema representing general feedback information"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "dtoType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = VoiceFeedbackDTO.class, name = "voiceFeedback")
})
public class FeedbackDTO {

    @Schema(description = "Feedback ID", example = "123")
    private Long feedbackId;

    @Schema(description = "Date of feedback submission", example = "2024-05-07T12:30:00")
    private LocalDateTime feedbackDate;

    @Schema(description = "Status of the feedback", example = "PENDING")
    private FeedbackStatusEnum feedbackStatus;

    @Schema(description = "Feedback content (text or reference to file)", example = "Great service!")
    private String content;

    @Schema(description = "Sentiment of the feedback", example = "POSITIVE")
    private SentimentStateEnum sentiment;

    @Schema(description = "Feedback source details")
    private FeedbackSourceDTO feedbackSource;

    @Schema(description = "Method used to submit feedback", example = "WEB_FORM")
    private FeedbackMethodEnum methodEnum;

    @Schema(description = "Type of feedback", example = "TEXT")
    private FeedbackTypeEnum typeEnum;

    @Schema(description = "Validation token ID if exists", example = "789")
    private Long validationTokenId;

    @Schema(description = "Time when feedback was sent to sentiment analysis", example = "2024-05-07T12:31:00")
    private LocalDateTime sentToSentimentAnalysis;

    @Schema(description = "Time when sentiment analysis result was received", example = "2024-05-07T12:31:10")
    private LocalDateTime receivedFromSentimentAnalysis;
}