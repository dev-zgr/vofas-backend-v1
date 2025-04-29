package org.example.vofasbackendv1.presentationlayer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "StaticQRDTO",
        description = "Schema to represent static QR feedback source information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaticQRDTO extends FeedbackSourceDTO {

    @Schema(description = "Unique QR identifier", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID qrID;

    @Schema(description = "Location of the QR", example = "Main Lobby Wall")
    @NotBlank
    @Size(max = 255)
    private String location;

    @Schema(description = "Informative text shown near the QR", example = "Scan here to provide feedback")
    @NotBlank
    @Size(max = 255)
    private String informativeText;
}