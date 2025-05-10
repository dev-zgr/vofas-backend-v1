package org.example.vofasbackendv1.data_layer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import org.example.vofasbackendv1.data_layer.enums.FeedbackMethodEnum;
import org.example.vofasbackendv1.data_layer.enums.FeedbackStatusEnum;
import org.example.vofasbackendv1.data_layer.enums.FeedbackTypeEnum;
import org.example.vofasbackendv1.data_layer.enums.SentimentStateEnum;

import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Data
@Entity
@Table(name = "feedback_table")
@Inheritance(strategy = InheritanceType.JOINED)
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
    @Column(name = "feedback_status", nullable = false, length = 20)
    private FeedbackStatusEnum feedbackStatus;

    @Column(name = "content")
    @Size(max = 1024)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "sentiment", length = 20)
    private SentimentStateEnum sentiment;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "feedback_source_id", referencedColumnName = "feedback_source_id")
    private FeedbackSourceEntity feedbackSource;

    @NotNull
    @Column(name = "method", nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private FeedbackMethodEnum methodEnum;

    @NotNull
    @Column(name = "type", nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private FeedbackTypeEnum typeEnum;

    @Column(name = "validation_token_id")
    private Long validationTokenId;

    @Column(name = "sent_to_sentiment_analysis")
    private LocalDateTime sentToSentimentAnalysis;

    @Column(name = "received_from_sentiment_analysis")
    private LocalDateTime receivedFromSentimentAnalysis;

    public FeedbackEntity() {
        this.feedbackDate = LocalDateTime.now();
        this.feedbackStatus = null;
        this.content = "";
        this.sentiment = null;
        this.feedbackSource = null;
        this.methodEnum = null;
        this.validationTokenId = null;
        this.sentToSentimentAnalysis = null;
        this.receivedFromSentimentAnalysis = null;
    }

    public FeedbackEntity(LocalDateTime feedbackDate, FeedbackStatusEnum feedbackStatus, String content, SentimentStateEnum sentiment, FeedbackMethodEnum methodEnum, FeedbackTypeEnum typeEnum ,Long validationTokenId, LocalDateTime sentToSentimentAnalysis, LocalDateTime receivedFromSentimentAnalysis) {
        this.feedbackDate = feedbackDate;
        this.feedbackStatus = feedbackStatus;
        this.content = content;
        this.sentiment = sentiment;
        this.feedbackSource = null;
        this.methodEnum = methodEnum;
        this.typeEnum = typeEnum;
        this.validationTokenId = validationTokenId;
        this.sentToSentimentAnalysis = sentToSentimentAnalysis;
        this.receivedFromSentimentAnalysis = receivedFromSentimentAnalysis;
    }
}
