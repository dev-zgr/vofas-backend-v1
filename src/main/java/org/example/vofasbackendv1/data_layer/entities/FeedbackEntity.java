package org.example.vofasbackendv1.data_layer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.example.vofasbackendv1.data_layer.enums.FeedbackStatusEnum;
import org.example.vofasbackendv1.data_layer.enums.SentimentStateEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback_table")
@Data
@ToString
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id", nullable = false)
    private Long feedbackId;

    @NotNull
    @Column(name = "feedback_date", nullable = false)
    private LocalDateTime feedbackDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Size(min = 5, max = 20)
    @Column(name = "feedback_status", nullable = false, length = 20)
    private FeedbackStatusEnum feedbackStatus;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Size(min = 5, max = 32)
    @Column(name = "sentiment", length = 20)
    private SentimentStateEnum sentiment;

    @NotNull
    @Column(name = "feedback_source_id", nullable = false)
    private Long feedbackSourceId;

    @NotNull
    @Size(min = 3, max = 32)
    @Column(name = "method", nullable = false, length = 32)
    private String method;

    @Column(name = "validation_token_id")
    private Long validationTokenId;

    @Column(name = "sent_to_sentiment_analysis")
    private LocalDateTime sentToSentimentAnalysis;

    @Column(name = "received_from_sentiment_analysis")
    private LocalDateTime receivedFromSentimentAnalysis;

    public FeedbackEntity() {}

    public FeedbackEntity(LocalDateTime feedbackDate, FeedbackStatusEnum feedbackStatus, String content, SentimentStateEnum sentiment, Long feedbackSourceId, String method, Long validationTokenId, LocalDateTime sentToSentimentAnalysis, LocalDateTime receivedFromSentimentAnalysis) {
        this.feedbackDate = feedbackDate;
        this.feedbackStatus = feedbackStatus;
        this.content = content;
        this.sentiment = sentiment;
        this.feedbackSourceId = feedbackSourceId;
        this.method = method;
        this.validationTokenId = validationTokenId;
        this.sentToSentimentAnalysis = sentToSentimentAnalysis;
        this.receivedFromSentimentAnalysis = receivedFromSentimentAnalysis;
    }
}
