package org.example.vofasbackendv1.data_layer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "validation_token_table")
@Data
@ToString
public class ValidationTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "validation_token_id", nullable = false)
    private Long validationTokenId;

    @Size(max = 255)
    @Column(name = "token", length = 255)
    private String token;

    @Column(name = "producer_kiosk_id")
    private Long producerKioskId;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Size(max = 32)
    @Column(name = "status", length = 32)
    private String status;

    @Column(name = "using_date")
    private LocalDateTime usingDate;

    public ValidationTokenEntity() {}

    public ValidationTokenEntity(String token, Long producerKioskId, LocalDateTime creationDate, String status) {
        this.token = token;
        this.producerKioskId = producerKioskId;
        this.creationDate = creationDate;
        this.status = status;
    }
}