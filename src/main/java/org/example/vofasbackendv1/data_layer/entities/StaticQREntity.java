package org.example.vofasbackendv1.data_layer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.vofasbackendv1.data_layer.enums.FeedbackSourceStateEnum;
import org.example.vofasbackendv1.data_layer.enums.SourceTypeEnum;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "static_qr_table")
@PrimaryKeyJoinColumn(name = "feedback_source_id")
@Data
@ToString
public class StaticQREntity extends FeedbackSourceEntity{

    @NotNull
    @Column(name = "qr_id", nullable = false)
    private UUID qrID;

    @NotNull
    @Size(max = 255)
    @Column(name = "location", nullable = false)
    @NotBlank
    private String location;

    @NotNull
    @Size(max = 255)
    @Column(name = "informative_text", nullable = false)
    @NotBlank
    private String informativeText;

    // Default constructor initializing default values
    public StaticQREntity() {
        super();
        this.qrID = null;
        this.location = "";
        this.informativeText = "";
    }

    // Constructor with arguments (no QR ID generation here)
    public StaticQREntity(String sourceName, String description, FeedbackSourceStateEnum state, UUID qrID, String location, String informativeText) {
        super(sourceName, SourceTypeEnum.STATIC_QR, description, java.time.LocalDateTime.now(), state, new java.util.ArrayList<>());
        this.qrID = qrID; // qrID passed via argument
        this.location = location;
        this.informativeText = informativeText;
    }
}