package org.example.vofasbackendv1.data_layer.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.example.vofasbackendv1.data_layer.enums.FeedbackStatusEnum;
import org.example.vofasbackendv1.data_layer.enums.SentimentStateEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "voice_feedback_table")
@Data
@ToString
public class VoiceFeedbackEntity extends FeedbackEntity {

    @Column(name = "file_path", length = 255)
    private String filePath;

    @Column(name = "sent_for_transcription")
    private LocalDateTime sentForTranscription;

    @Column(name = "received_from_transcription")
    private LocalDateTime receivedFromTranscription;

    public VoiceFeedbackEntity() {}

    // Constructor of VoiceFeedbackEntity
    public VoiceFeedbackEntity(LocalDateTime feedbackDate, FeedbackStatusEnum feedbackStatus, String content,
                               SentimentStateEnum sentiment, Long feedbackSourceId, String method, Long validationTokenId,
                               LocalDateTime sentToSentimentAnalysis, LocalDateTime receivedFromSentimentAnalysis,
                               String filePath, LocalDateTime sentForTranscription, LocalDateTime receivedFromTranscription) {

        super(feedbackDate, feedbackStatus, content, sentiment, feedbackSourceId, method, validationTokenId,
                sentToSentimentAnalysis, receivedFromSentimentAnalysis);

        this.filePath = filePath;
        this.sentForTranscription = sentForTranscription;
        this.receivedFromTranscription = receivedFromTranscription;
    }
}
