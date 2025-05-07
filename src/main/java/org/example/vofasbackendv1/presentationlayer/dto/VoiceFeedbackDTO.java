package org.example.vofasbackendv1.presentationlayer.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "VoiceFeedbackDTO",
        description = "Schema representing voice-based feedback information"
)
public class VoiceFeedbackDTO extends FeedbackDTO {

    @Schema(description = "Path to the stored voice file", example = "/data/audio/voice1234.wav")
    private String filePath;

    @Schema(description = "Timestamp when voice was sent for transcription", example = "2024-05-07T12:32:00")
    private LocalDateTime sentForTranscription;

    @Schema(description = "Timestamp when transcription was received", example = "2024-05-07T12:35:00")
    private LocalDateTime receivedFromTranscription;
}
