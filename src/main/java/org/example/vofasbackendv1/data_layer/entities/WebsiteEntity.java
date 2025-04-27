package org.example.vofasbackendv1.data_layer.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "website_table")
@PrimaryKeyJoinColumn(name = "feedback_source_id")  // Joins on the primary key from FeedbackSourceEntity
@Data
@ToString
public class WebsiteEntity extends FeedbackSourceEntity{


    @Column(name = "url", nullable = false)
    private String url;

    public WebsiteEntity() {
        super();
        this.url = "";
    }

    public WebsiteEntity(String sourceName, org.example.vofasbackendv1.data_layer.enums.SourceTypeEnum sourceType, String description, org.example.vofasbackendv1.data_layer.enums.FeedbackSourceStateEnum state, String url) {
        super(sourceName, sourceType, description, java.time.LocalDateTime.now(), state, new java.util.ArrayList<>());
        this.url = url;
    }
}
