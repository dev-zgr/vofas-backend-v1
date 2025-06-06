package org.example.vofasbackendv1.presentationlayer.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.vofasbackendv1.constants.AuthenticationConstants;
import org.example.vofasbackendv1.constants.SourceConstants;
import org.example.vofasbackendv1.constants.UserConstants;
import org.example.vofasbackendv1.presentationlayer.dto.AuthenticationDTO;
import org.example.vofasbackendv1.presentationlayer.dto.AuthenticationRequestDTO;
import org.example.vofasbackendv1.presentationlayer.dto.BaseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.UserDTO;
import org.example.vofasbackendv1.servicelayer.interfaces.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Authentication", description = "Authentication API endpoints")
@RestController
@RequestMapping(value = "/vofas/api/v1", produces = "application/json")
@CrossOrigin(origins = "*")
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Operation(
            summary = "User login",
            description = "Authenticates a user and returns a JWT token if credentials are valid."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful, JWT token returned"),
            @ApiResponse(responseCode = "400", description = "Bad request - invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - wrong credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<AuthenticationDTO>> login(@RequestBody @Valid AuthenticationRequestDTO authenticationDTO) {
        AuthenticationDTO authenticationResponse = authenticationService.authenticate(authenticationDTO);
        BaseDTO<AuthenticationDTO> response = new BaseDTO<>(
                SourceConstants.User,
                AuthenticationConstants.LOGIN_SUCCESS,
                LocalDateTime.now(),
                authenticationResponse
        );
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get User by User validation token",
            description = "Used to get user details for given JWT token."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
            @ApiResponse(responseCode = "403", description = "HTTP Status Forbidden"),
            @ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @GetMapping(path = "/my-account", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<UserDTO>> getUserByUserToken(@RequestHeader("Authorization") String userToken){
        UserDTO userResponse = authenticationService.getUserByUserToken(userToken);
        BaseDTO<UserDTO> response = new BaseDTO<>(
                SourceConstants.User,
                AuthenticationConstants.MY_ACCOUNT_SUCCESS,
                LocalDateTime.now(),
                userResponse
        );
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Update the User by user validation token",
            description = "Used to update user details for given JWT token."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
            @ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @PutMapping(path = "/my-account", consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<UserDTO>> updateUserByUserToken(@RequestHeader("Authorization") String userToken, @RequestBody @Valid UserDTO userDTO){
        UserDTO updatedUserDTO = authenticationService.updateUserByToken(userToken, userDTO);
        BaseDTO<UserDTO> baseDTO = new BaseDTO<>(
                SourceConstants.User,
                UserConstants.USER_UPDATED_SUCCESS,
                LocalDateTime.now(),
                updatedUserDTO
        );
        return ResponseEntity.ok(baseDTO);
    }

}
