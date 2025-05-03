package org.example.vofasbackendv1.presentationlayer.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.example.vofasbackendv1.constants.WebsiteConstants;
import org.example.vofasbackendv1.exceptions.InvalidParametersException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.BaseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.ResponseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.WebsiteDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.vofasbackendv1.servicelayer.interfaces.StaticQRService;
import org.example.vofasbackendv1.servicelayer.interfaces.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Tag(name = "Website API endpoints", description = "Website API endpoints")
@RestController
@RequestMapping(value = "/vofas/api/v1", produces = "application/json")
@CrossOrigin(origins = "*")
public class WebsiteController {

    private WebsiteService websiteService;

    @Autowired
    public WebsiteController(WebsiteService websiteService) {
        this.websiteService = websiteService;
    }

    @Operation(
            summary = "Create a new Website",
            description = "Creates a new website with the provided WebsiteDTO."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "HTTP Status Created"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
            @ApiResponse(responseCode = "403", description = "HTTP Status Forbidden"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @PostMapping(path = "/website", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<WebsiteDTO>> createWebsite(@RequestBody @Valid WebsiteDTO websiteDTO){
        try {
            try {
                WebsiteDTO existingWebsite = websiteService.getWebsite();
                if (existingWebsite != null) {
                    throw new BadRequestException(WebsiteConstants.WEBSITE_ALREADY_EXISTS);
                }
            } catch (ResourceNotFoundException e) {
                Boolean isCreated = websiteService.createWebsite(websiteDTO);

                BaseDTO<WebsiteDTO> response = new BaseDTO<>();
                response.setSourceName("SYSTEM");
                response.setMessage(WebsiteConstants.WEBSITE_CREATED_SUCCESS);
                response.setRequestedAt(LocalDateTime.now());
                response.setContent(websiteDTO);

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        } catch (InvalidParametersException e) {
            BaseDTO<WebsiteDTO> errorResponse = new BaseDTO<>(
                    "USER",
                    e.getMessage(),
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            BaseDTO<WebsiteDTO> errorResponse = new BaseDTO<>(
                    "USER",
                    "An unexpected error occurred",
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

        BaseDTO<WebsiteDTO> fallbackResponse = new BaseDTO<>();
        fallbackResponse.setSourceName("SYSTEM");
        fallbackResponse.setMessage("Unexpected issue occurred during website creation.");
        fallbackResponse.setRequestedAt(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(fallbackResponse);
    }

    @Operation(
            summary = "Update Website by ID",
            description = "Updates the details of an existing Website using the provided WebsiteDTO and websiteID. " +
                    "Only the fields url and informativeText can be updated."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
            @ApiResponse(responseCode = "403", description = "HTTP Status Forbidden"),
            @ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @PutMapping(path = "/website/{websiteID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<WebsiteDTO>> updateWebsiteByID(
            @PathVariable("websiteID") Long websiteID,
            @RequestBody @Valid WebsiteDTO websiteDTO) {
        try {
            WebsiteDTO updatedWebsite = websiteService.updateWebsiteByID(websiteID, websiteDTO);

            BaseDTO<WebsiteDTO> response = new BaseDTO<>();
            response.setSourceName("SYSTEM");
            response.setMessage("Website updated successfully");
            response.setRequestedAt(LocalDateTime.now());
            response.setContent(updatedWebsite);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (InvalidParametersException e) {
            BaseDTO<WebsiteDTO> errorResponse = new BaseDTO<>(
                    "USER",
                    e.getMessage(),
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (ResourceNotFoundException e) {
            BaseDTO<WebsiteDTO> errorResponse = new BaseDTO<>(
                    "USER",
                    e.getMessage(),
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            BaseDTO<WebsiteDTO> errorResponse = new BaseDTO<>(
                    "USER",
                    "An unexpected error occurred",
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @Operation(
            summary = "Get the Website",
            description = "Fetches the single Website entity."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
            @ApiResponse(responseCode = "403", description = "HTTP Status Forbidden"),
            @ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @GetMapping(path = "/website", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<WebsiteDTO>> getWebsite() {
        try {
            WebsiteDTO websiteDTO = websiteService.getWebsite();

            BaseDTO<WebsiteDTO> response = new BaseDTO<>();
            response.setSourceName("SYSTEM");
            response.setMessage(WebsiteConstants.WEBSITE_FETCH_SUCCESS);
            response.setRequestedAt(LocalDateTime.now());
            response.setContent(websiteDTO);

            return ResponseEntity.ok(response);

        } catch (ResourceNotFoundException e) {
            BaseDTO<WebsiteDTO> errorResponse = new BaseDTO<>();
            errorResponse.setSourceName("SYSTEM");
            errorResponse.setMessage(e.getMessage());
            errorResponse.setRequestedAt(LocalDateTime.now());
            errorResponse.setContent(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            BaseDTO<WebsiteDTO> errorResponse = new BaseDTO<>();
            errorResponse.setSourceName("SYSTEM");
            errorResponse.setMessage("Internal server error: " + e.getMessage());
            errorResponse.setRequestedAt(LocalDateTime.now());
            errorResponse.setContent(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
