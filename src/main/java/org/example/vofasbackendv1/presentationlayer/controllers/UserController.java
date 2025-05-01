package org.example.vofasbackendv1.presentationlayer.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.example.vofasbackendv1.constants.UserConstants;
import org.example.vofasbackendv1.exceptions.InvalidParametersException;
import org.example.vofasbackendv1.exceptions.NoContentException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.BaseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.ResponseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.UserDTO;
import org.example.vofasbackendv1.servicelayer.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "User API endpoints", description = "User API endpoints")
@RestController
@RequestMapping(value = "/vofas/api/v1", produces = "application/json")
@CrossOrigin(origins = "*")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "Fetch All Users with REST API", description = "Fetch all Users details from the VoFAS application" + "This will be mainly used to show all the users in the UI")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "204", description = "HTTP Status No Content"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request "),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized "),
            @ApiResponse(responseCode = "403", description = "HTTP  Status Forbidden "),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")})
    @GetMapping(path = "/user", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseDTO<Page<UserDTO>>> getAllUsers(
            @RequestParam(value = "sort-by", defaultValue = "userID") String sortBy,
            @RequestParam(value = "ascending", defaultValue = "true") boolean ascending,
            @RequestParam(value = "page-no", defaultValue = "0") int pageNo) throws BadRequestException {

        try {
            Page<UserDTO> userPage = userService.getAllUser(sortBy, ascending, pageNo);

            BaseDTO<Page<UserDTO>> response = new BaseDTO<>(
                    "USER",
                    UserConstants.USERS_FETCH_SUCCESS,
                    LocalDateTime.now(),
                    userPage
            );

            return ResponseEntity.ok(response);

        } catch (InvalidParametersException e) {
            throw new BadRequestException("Invalid parameters: " + e.getMessage());
        } catch (NoContentException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Fetch User by User ID", description = "Fetches a single user's details using their user ID from the VoFAS application. " + "Primarily used for profile display or admin-level queries.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
            @ApiResponse(responseCode = "403", description = "HTTP Status Forbidden"),
            @ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")})
    @GetMapping(path = "/user/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<UserDTO>> getUserByUserID(@PathVariable("userID") Long userID) {
        try {
            UserDTO userDTO = userService.getUserByUserID(userID);

            BaseDTO<UserDTO> responseDTO = new BaseDTO<>(
                    "USER",
                    UserConstants.USER_FETCH_SUCCESS,
                    LocalDateTime.now(),
                    userDTO
            );

            return ResponseEntity.ok(responseDTO);

        } catch (InvalidParametersException e) {
            BaseDTO<UserDTO> errorResponse = new BaseDTO<>(
                    "USER",
                    e.getMessage(),
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (ResourceNotFoundException e) {
            BaseDTO<UserDTO> errorResponse = new BaseDTO<>(
                    "USER",
                    e.getMessage(),
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            BaseDTO<UserDTO> errorResponse = new BaseDTO<>(
                    "USER",
                    "An unexpected error occurred",
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @Operation(summary = "Create a New User", description = "Creates a new user in the VoFAS application using the provided UserDTO. " + "This endpoint is used for user registration or admin-level user creation.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "HTTP Status Created"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
            @ApiResponse(responseCode = "403", description = "HTTP Status Forbidden"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")})
    @PostMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            Boolean userCreated = userService.createUser(userDTO);

            if (userCreated) {
                ResponseDTO responseDTO = new ResponseDTO(
                        "201",
                        UserConstants.USER_CREATED_SUCCESS
                );
                return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(new ResponseDTO("400", "Failed to create user."), HttpStatus.BAD_REQUEST);
            }
        } catch (InvalidParametersException e) {
            return new ResponseEntity<>(new ResponseDTO("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO("500", "Unexpected error: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Delete User by User ID",
            description = "Deletes a user from the VoFAS application using their user ID. This is typically used by admin users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
            @ApiResponse(responseCode = "403", description = "HTTP Status Forbidden"),
            @ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @DeleteMapping(path = "/user/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> deleteUserByUserID(@PathVariable("userID") Long userID) {
        try {
            Boolean isDeleted = userService.deleteUserByUserID(userID);

            ResponseDTO responseDTO = new ResponseDTO(
                    "200",
                    UserConstants.USER_DELETED_SUCCESS
            );

            return ResponseEntity.ok(responseDTO);

        } catch (InvalidParametersException e) {
            ResponseDTO errorResponse = new ResponseDTO(
                    "400",
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (ResourceNotFoundException e) {
            ResponseDTO errorResponse = new ResponseDTO(
                    "404",
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            ResponseDTO errorResponse = new ResponseDTO(
                    "500",
                    "An unexpected error occurred"
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @Operation(
        summary = "Update User by User ID",
        description = "Updates the details of an existing user in the VoFAS application using the provided UserDTO and user ID. " +
                      "Typically used by admin users or during profile updates."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
        @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
        @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
        @ApiResponse(responseCode = "403", description = "HTTP Status Forbidden"),
        @ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @PutMapping(path = "/user/{userID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<UserDTO>> updateUserByUserID(@PathVariable("userID") Long userID, @RequestBody @Valid UserDTO userDTO) {
        try {
            UserDTO updatedUserDTO = userService.updateUserByUserID(userID, userDTO);

            BaseDTO<UserDTO> baseDTO = new BaseDTO<>(
                    "USER",
                    UserConstants.USER_UPDATED_SUCCESS,
                    LocalDateTime.now(),
                    updatedUserDTO
            );

                return ResponseEntity.ok(baseDTO);
        } catch (InvalidParametersException e) {
            BaseDTO<UserDTO> errorResponse = new BaseDTO<>(
                    "USER",
                    e.getMessage(),
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (ResourceNotFoundException e) {
            BaseDTO<UserDTO> errorResponse = new BaseDTO<>(
                    "USER",
                    e.getMessage(),
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            BaseDTO<UserDTO> errorResponse = new BaseDTO<>(
                    "USER",
                    "An error occurred while updating User.",
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    //TODO: Add update account by jwt token


}
