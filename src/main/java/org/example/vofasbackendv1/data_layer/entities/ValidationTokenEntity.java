package org.example.vofasbackendv1.data_layer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "validation_token_table")
@Data
@ToString
@EqualsAndHashCode
public class ValidationTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "validation_token_id", nullable = false)
    private Long validationTokenId;

    @Size(max = 255)
    @Column(name = "validation_token")
    private UUID token;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "producer_kiosk_id", referencedColumnName = "feedback_source_id")
    private KioskEntity producerKiosk;

    @Column(name = "creation_date")
    private LocalDateTime createdAt;

    @Size(max = 32)
    @Column(name = "status", length = 32)
    private String status;

    @Column(name = "using_date")
    private LocalDateTime usedAt;

    public ValidationTokenEntity() {
        this.token = null;
        this.producerKiosk = null;
        this.createdAt = null;  // Default to the current time
        this.status = "";
        this.usedAt = null;
    }

    public ValidationTokenEntity(UUID token, KioskEntity producerKiosk, LocalDateTime createdAt, String status) {
        this.token = token;
        this.producerKiosk = producerKiosk;
        this.createdAt = createdAt;
        this.status = status;
    }
}