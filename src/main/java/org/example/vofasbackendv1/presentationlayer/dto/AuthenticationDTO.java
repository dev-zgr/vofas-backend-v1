package org.example.vofasbackendv1.presentationlayer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "UserDTO",
        description = "Schema to represent user information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDTO {

    LocalDateTime createdAt;
    String jwtToken;
    LocalDateTime expiresAt;
    String role;

}
