package org.example.vofasbackendv1.data_layer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "kiosk_table")
@Data
@ToString
public class KioskEntity extends FeedbackSourceEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kiosk_id", nullable = false)
    private Long kioskId;

    @NotNull
    @Column(name = "serial_number", nullable = false, columnDefinition = "UUID")
    private UUID serialNumber;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @NotNull
    @Column(name = "kiosk_key", nullable = false, columnDefinition = "UUID")
    private UUID kioskKey;

    @Size(min = 2, max = 64)
    @Column(name = "location", length = 64)
    private String location;

    public KioskEntity() {}

    public KioskEntity(String sourceName, String sourceType, String description, String state,
            UUID serialNumber, LocalDateTime creationDate, UUID kioskKey, String location) {

        super(sourceName, sourceType, description, state);
        this.serialNumber = serialNumber;
        this.creationDate = creationDate;
        this.kioskKey = kioskKey;
        this.location = location;
    }
}
