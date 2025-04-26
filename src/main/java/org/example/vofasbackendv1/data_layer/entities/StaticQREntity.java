package org.example.vofasbackendv1.data_layer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "static_qr_table")
@Data
@ToString
public class StaticQREntity extends FeedbackSourceEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "static_qr_id", nullable = false)
    private Long staticQRId;

    @NotNull
    @Size(max = 255)
    @Column(name = "url", nullable = false, length = 255)
    private String url;

    @NotNull
    @Size(max = 255)
    @Column(name = "text", nullable = false, length = 255)
    private String text;

    public StaticQREntity() {}

    public StaticQREntity(String sourceName, String sourceType, String description, String state, String url, String text) {
        super(sourceName, sourceType, description, state);
        this.url = url;
        this.text = text;
    }
}