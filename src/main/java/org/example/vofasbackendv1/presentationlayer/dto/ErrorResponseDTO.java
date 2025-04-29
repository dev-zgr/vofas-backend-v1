package org.example.vofasbackendv1.presentationlayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
public class ErrorResponseDTO {

    @Schema(
            description = "API path invoked by client",
            example = "/api/v1/users"
    )
    private String apiPath;

    @Schema(
            description = "Error code representing the error happened",
            example = "BAD_REQUEST"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message representing the error happened",
            example = "userName name can not be a null or empty"
    )
    private String errorMessage;

    @Schema(
            description = "Time representing when the error happened",
            example = "2021-08-08T12:12:12"
    )
    private LocalDateTime errorTime;

}
