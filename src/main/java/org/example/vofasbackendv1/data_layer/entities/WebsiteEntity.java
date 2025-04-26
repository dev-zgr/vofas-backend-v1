package org.example.vofasbackendv1.data_layer.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "website_table")
@Data
@ToString
public class WebsiteEntity extends FeedbackSourceEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "url", length = 255, nullable = false)
    private String url;

    public WebsiteEntity() {}

    public WebsiteEntity(String sourceName, String sourceType, String description, String state, String url) {
        super(sourceName, sourceType, description, state);
        this.url = url;
    }
}
