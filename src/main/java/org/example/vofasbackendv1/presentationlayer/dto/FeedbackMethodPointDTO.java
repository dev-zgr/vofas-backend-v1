package org.example.vofasbackendv1.presentationlayer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "FeedbackMethodPointDTO",
        description = "Represents feedback distribution by method such as kiosk, website, static QR, and dynamic QR codes."
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackMethodPointDTO {
    private Long numberOfKiosks;
    private Long numberOfWebsites;
    private Long numberOfStaticQR;
    private Long numberOfDynamicQR;
}