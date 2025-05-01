package org.example.vofasbackendv1.presentationlayer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "UserDTO",
        description = "Schema to represent user information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @Schema(description = "Unique identifier of the user", example = "1")
    private Long userID;

    @NotBlank
    @Size(min = 2, max = 32)
    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 32)
    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;

    @NotBlank
    @Email
    @Size(min = 8, max = 64)
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @NotBlank
    @Size(min = 8, max = 64)
    @Schema(description = "Password of the user (hashed or plaintext)", example = "secret123")
    private String password;

    @NotBlank
    @Size(max = 64)
    @Schema(description = "First line of the user's address", example = "123 Main St")
    private String addressFirstLine;

    @NotBlank
    @Size(max = 64)
    @Schema(description = "Second line of the user's address", example = "Apt 4B")
    private String addressLine2;

    @NotBlank
    @Schema(description = "District of the user", example = "Kadikoy")
    private String district;

    @NotBlank
    @Schema(description = "City of the user", example = "Istanbul")
    private String city;

    @NotBlank
    @Schema(description = "Postal code of the user", example = "34000")
    private String postalCode;

    @NotBlank
    @Schema(description = "Country of the user", example = "Turkey")
    private String country;

    @Schema(description = "Role of the user", example = "ADMIN")
    @NotBlank
    private String roleEnum;
}
