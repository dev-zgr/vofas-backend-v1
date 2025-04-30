package org.example.vofasbackendv1.presentationlayer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "WebsiteDTO",
        description = "Schema to represent website feedback source information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebsiteDTO extends FeedbackSourceDTO {

    @Schema(description = "URL of the website", example = "https://www.example.com")
    @NotBlank
    @Size(max = 255)
    private String url;

    @Schema(description = "Informative text displayed on the website", example = "Visit our website to provide feedback")
    @NotBlank
    @Size(max = 255)
    private String informativeText;
}
