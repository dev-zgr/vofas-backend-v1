package org.example.vofasbackendv1.presentationlayer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "FeedbackSourcePointDTO",
        description = "Details the number of feedbacks received from different sources like kiosks, websites, and static QR codes."
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackSourcePointDTO {
    private Long numberOfKiosks;
    private Long numberOfWebsites;
    private Long numberOfStaticQR;
}
