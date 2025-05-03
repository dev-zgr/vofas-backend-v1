package org.example.vofasbackendv1.presentationlayer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "AuthenticationRequestDTO",
        description = "Schema to represent authentication request information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDTO {

    @NotBlank
    @Email
    @Size(min = 8, max = 64)
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;


    @NotBlank
    @Size(min = 8, max = 64)
    @Schema(description = "Password of the user (hashed or plaintext)", example = "secret123")
    private String password;

}
