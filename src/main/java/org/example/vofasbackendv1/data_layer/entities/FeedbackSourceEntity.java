package org.example.vofasbackendv1.data_layer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.example.vofasbackendv1.data_layer.enums.FeedbackSourceStateEnum;
import org.example.vofasbackendv1.data_layer.enums.SourceTypeEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "feedback_source_table")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "source_type_discriminator", discriminatorType = DiscriminatorType.STRING)
@Data
@ToString
public class FeedbackSourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_source_id", nullable = false)
    private Long feedbackSourceId;

    @NotNull
    @Size(min = 2, max = 32)
    @Column(name = "source_name", nullable = false, length = 32)
    private String sourceName;

    @NotNull
    @Size(min = 2, max = 64)
    @Column(name = "source_type", nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private SourceTypeEnum sourceType;

    @Column(name = "description")
    @NotBlank
    @Size(min = 2, max = 255)
    private String description;

    @Column(name = "created_at")
    @NotBlank
    private LocalDateTime createdAt;

    @NotNull
    @Size(min = 2, max = 16)
    @Column(name = "state", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private FeedbackSourceStateEnum state;

    @OneToMany(mappedBy = "feedbackSource", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ArrayList<FeedbackEntity> feedbacks = new ArrayList<>();

    public FeedbackSourceEntity() {
        this.sourceName = "";
        this.sourceType = null; // assuming you have a default enum value
        this.description = "";
        this.createdAt = null;
        this.state = null; // assuming ACTIVE is a default state
        this.feedbacks = new ArrayList<>();
    }

    public FeedbackSourceEntity(String sourceName, SourceTypeEnum sourceType, String description, LocalDateTime createdAt, FeedbackSourceStateEnum state, ArrayList<FeedbackEntity> feedbacks) {
        this.sourceName = sourceName;
        this.sourceType = sourceType;
        this.description = description;
        this.createdAt = createdAt;
        this.state = state;
        this.feedbacks = feedbacks;
    }


}
