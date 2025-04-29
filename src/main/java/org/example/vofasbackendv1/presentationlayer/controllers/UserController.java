package org.example.vofasbackendv1.presentationlayer.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.vofasbackendv1.presentationlayer.dto.BaseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.ResponseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.UserDTO;
import org.example.vofasbackendv1.servicelayer.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BaseDTO<Page<UserDTO>>> getAllUsers(@RequestParam(value = "sort-by", defaultValue = "userID") String sortBy, @RequestParam(value = "ascending", defaultValue = "true") boolean ascending, @RequestParam(value = "page-no", defaultValue = "0") int pageNo) {
        return null;
        // TODO: Validate 'sortBy' parameter. Allowed values are 'userID', 'firstName', 'lastName', 'email'.
        //  If the value is invalid, throw a BadRequestException with a clear message.

        // TODO: Validate 'ascending' parameter. It should be either 'true' or 'false'.
        //       If the value is invalid, throw a BadRequestException with a clear message.

        // TODO: Implement pagination. Use 'pageNo' and return a page of 10 users per page.
        //       If the 'pageNo' exceeds available pages, return an empty list with a 204 status code (No Content).

        // TODO: Sort the user list based on the 'sortBy' and 'ascending' parameters.
        //       Ensure the sorting is applied on the correct field and order.
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
        // TODO: Validate 'userID'. It must not be null or less than 1.
        //       If invalid, throw BadRequestException with a descriptive message.

        // TODO: Fetch user from the database by 'userID'.
        //       If user is not found, throw ResourceNotFoundException (or return 404 status).

        // TODO: Map the retrieved UserEntity to UserDTO.

        // TODO: Wrap the UserDTO in BaseDTO and return with HTTP 200 OK.
        return null;
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
        // TODO: Validate the fields of UserDTO using annotations and custom logic if necessary.

        // TODO: Convert UserDTO to UserEntity.

        // TODO: Save the UserEntity to the database.

        // TODO: Convert the saved UserEntity back to UserDTO.

        // TODO: Wrap the UserDTO in a BaseDTO and return it with HTTP 201 Created.
        return null;
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
        // TODO: Validate 'userID'. It must not be null or less than 1.
        //       If invalid, throw BadRequestException with a descriptive message.

        // TODO: Check if user exists in the database by 'userID'.
        //       If not found, throw ResourceNotFoundException (or return 404).

        // TODO: Delete the user from the database.

        // TODO: Return a ResponseDTO indicating successful deletion with HTTP 200 OK.
        return null;
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
        // TODO: Validate 'userID'. It must not be null or less than 1.
        //       If invalid, throw BadRequestException with a descriptive message.

        // TODO: Check if user exists in the database by 'userID'.
        //       If not found, throw ResourceNotFoundException (or return 404).

        // TODO: Update the user entity fields with data from UserDTO.

        // TODO: Save the updated UserEntity to the database.

        // TODO: Convert the updated UserEntity back to UserDTO.

        // TODO: Wrap the updated UserDTO in a BaseDTO and return it with HTTP 200 OK.
        return null;
    }

    //TODO: Add update account by jwt token


}
