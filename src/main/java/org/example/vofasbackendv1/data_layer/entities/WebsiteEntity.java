package org.example.vofasbackendv1.data_layer.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.vofasbackendv1.data_layer.enums.FeedbackSourceStateEnum;
import org.example.vofasbackendv1.data_layer.enums.SourceTypeEnum;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "website_table")
@PrimaryKeyJoinColumn(name = "feedback_source_id")  // Joins on the primary key from FeedbackSourceEntity
@Data
@ToString
public class WebsiteEntity extends FeedbackSourceEntity {


    @Column(name = "url", nullable = false)
    private String url;


    @NotNull
    @Size(max = 255)
    @Column(name = "informative_text", nullable = false)
    @NotBlank
    private String informativeText;

    public WebsiteEntity() {
        super();
        this.url = "";
        this.informativeText = "";
    }


    public WebsiteEntity(String sourceName, SourceTypeEnum sourceType, String description, FeedbackSourceStateEnum state, String url, String informativeText) {
        super(sourceName, sourceType, description, java.time.LocalDateTime.now(), state, new ArrayList<>());
        this.url = url;
        this.informativeText = informativeText;
    }
}
