package org.example.vofasbackendv1.data_layer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.vofasbackendv1.data_layer.enums.FeedbackMethodEnum;
import org.example.vofasbackendv1.data_layer.enums.FeedbackStatusEnum;
import org.example.vofasbackendv1.data_layer.enums.FeedbackTypeEnum;
import org.example.vofasbackendv1.data_layer.enums.SentimentStateEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "voice_feedback_table")
@Data
@ToString
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class VoiceFeedbackEntity extends FeedbackEntity {

    @Column(name = "file_path")
    @Size(max = 255)
    @NotBlank
    private String filePath;

    @Column(name = "sent_for_transcription")
    private LocalDateTime sentForTranscription;

    @Column(name = "received_from_transcription")
    private LocalDateTime receivedFromTranscription;

    public VoiceFeedbackEntity() {
        super();
        this.filePath = "";
        this.sentForTranscription = null;
        this.receivedFromTranscription = null;
    }

    public VoiceFeedbackEntity(LocalDateTime feedbackDate, FeedbackStatusEnum feedbackStatus, String content,
                               SentimentStateEnum sentiment, FeedbackMethodEnum method, FeedbackTypeEnum typeEnum, Long validationTokenId,
                               LocalDateTime sentToSentimentAnalysis, LocalDateTime receivedFromSentimentAnalysis,
                               String filePath, LocalDateTime sentForTranscription, LocalDateTime receivedFromTranscription) {

        super(feedbackDate, feedbackStatus, content, sentiment, method, typeEnum, validationTokenId,
                sentToSentimentAnalysis, receivedFromSentimentAnalysis);

        this.filePath = filePath;
        this.sentForTranscription = sentForTranscription;
        this.receivedFromTranscription = receivedFromTranscription;
    }
}
