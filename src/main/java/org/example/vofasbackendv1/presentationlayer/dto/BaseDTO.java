package org.example.vofasbackendv1.presentationlayer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "BaseDTO",
        description = "Schema to represent generic data transfer object"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDTO<T> {
    @Schema(
            description = "Indicates the source of the request using SourceConstants",
            example = "USER"
    )
    String sourceName;

    @Schema(
            description = "A descriptive message associated with the request or response",
            example = "Request processed successfully"
    )
    String message;


    @Schema(
            description = "Time representing when source requested",
            example = "2021-08-08T12:12:12"
    )
    private LocalDateTime requestedAt;


    @Schema(
            description = "The actual content payload of the response, such as a page of Course data",
            example = "Page<Course>"
    )
    T content;
}
