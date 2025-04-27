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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "kiosk_table")
@PrimaryKeyJoinColumn(name = "feedback_source_id")  // Joins on the primary key from FeedbackSourceEntity
@Data
@ToString
public class KioskEntity extends FeedbackSourceEntity{

    @NotNull
    @Column(name = "serial_number", nullable = false)
    private UUID serialNumber;

    @NotNull
    @Column(name = "kiosk_key", nullable = false)
    private UUID kioskKey;

    @Size(min = 2, max = 64)
    @Column(name = "location", length = 64)
    @NotBlank
    private String location;

    @OneToMany(mappedBy = "producerKiosk", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ValidationTokenEntity> validationTokens;

    public KioskEntity() {
        super();
        this.serialNumber = null;
        this.kioskKey = null;
        this.location = "";
        this.validationTokens = new ArrayList<>();
    }

    public KioskEntity(String sourceName, String description, FeedbackSourceStateEnum state,
                       UUID serialNumber, LocalDateTime creationDate, UUID kioskKey, String location,ArrayList<ValidationTokenEntity> validationTokens) {

        super(sourceName, SourceTypeEnum.KIOSK, description, creationDate, state, new ArrayList<>());  // Passing parameters to the superclass constructor
        this.serialNumber = serialNumber;
        this.kioskKey = kioskKey;
        this.location = location;
        this.validationTokens = validationTokens;
    }
}
