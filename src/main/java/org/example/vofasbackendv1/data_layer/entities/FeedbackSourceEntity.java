package org.example.vofasbackendv1.data_layer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "feedback_source_table")
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
    private String sourceType;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Size(min = 2, max = 16)
    @Column(name = "state", nullable = false, length = 16)
    private String state;

    public FeedbackSourceEntity() {}

    public FeedbackSourceEntity(String sourceName, String sourceType, String description, String state) {
        this.sourceName = sourceName;
        this.sourceType = sourceType;
        this.description = description;
        this.state = state;
    }
}
