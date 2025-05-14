package org.example.vofasbackendv1.presentationlayer.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(
        name = "Feedback Filter DTO",
        description = "Schema representing filter for feedbacks"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackFilterDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    LocalDate endDate;

    // Check if it contains only values RECEIVED|WAITING_TRANSCRIPTION|WAITING_SENTIMENT_ANALYSIS|READY
    String[] feedbackStatuses;

    //Check if it contains values only: WEBSITE, KIOSK, STATIC_QR, DYNAMIC_QR
    String[] feedbackMethods;

    // Check if it contains values only: POSITIVE, NEUTRAL, NEGATIVE
    String[] sentiments;

    // CHECK if it contains values only TEXT or VOICE
    String[] types;


}
