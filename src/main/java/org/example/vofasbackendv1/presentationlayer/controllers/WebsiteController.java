package org.example.vofasbackendv1.presentationlayer.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.vofasbackendv1.presentationlayer.dto.BaseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.ResponseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.WebsiteDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.vofasbackendv1.servicelayer.interfaces.StaticQRService;
import org.example.vofasbackendv1.servicelayer.interfaces.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;

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
    public ResponseEntity<BaseDTO<WebsiteDTO>> createWebsite(@RequestBody @Valid WebsiteDTO websiteDTO) {
        // TODO: Validate WebsiteDTO fields using annotations or custom logic if necessary.
        // TODO: Map WebsiteDTO to Website entity.
        // TODO: Save the Website entity in the database.
        // TODO: Convert the saved Website entity to WebsiteDTO.
        // TODO: Wrap the WebsiteDTO in BaseDTO and return it with HTTP 201 Created.
        // TODO: Throw bad request exception if user tries to create more than one website

        return null; // Implement the actual logic for creating the website.
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
    public ResponseEntity<BaseDTO<ResponseDTO>> updateWebsiteById(
            @PathVariable("websiteID") Long websiteID,
            @RequestBody @Valid WebsiteDTO websiteDTO) {

        // TODO: Validate 'websiteID'

        // TODO: Fetch Website entity by 'websiteID'

        // TODO: Ensure only 'url', 'informativeText' and 'state' are updated

        // TODO: Update the allowed fields in the Website entity

        // TODO: Save the updated Website entity

        // TODO: Convert the saved Website entity back to WebsiteDTO

        // TODO: Wrap the WebsiteDTO in BaseDTO and return with HTTP 200 OK
        return null;
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
    @PutMapping(path = "/website", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<ResponseDTO>> updateWebsiteById(
            @RequestBody @Valid WebsiteDTO websiteDTO) {

        // TODO: Validate 'websiteID'. It must not be null or less than 1.
        //       If invalid, throw BadRequestException with a descriptive message.

        // TODO: Fetch Website entity since there is just one allowed you can fetch all anyways.

        // TODO: Ensure that only 'informativeText' and 'state' are updated.
        //       If other fields are changed, throw BadRequestException with a descriptive message.

        // TODO: Update the allowed fields in the Website entity.

        // TODO: Save the updated Website entity.

        // TODO: Convert the saved Website entity back to WebsiteDTO.

        // TODO: Wrap the WebsiteDTO in BaseDTO and return with HTTP 200 OK.
        return null;
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
        // TODO: Fetch the single Website entity from the database.
        //       If not found, throw ResourceNotFoundException (or return 404).

        // TODO: Map the Website entity to WebsiteDTO.

        // TODO: Wrap the WebsiteDTO in BaseDTO and return with HTTP 200 OK.
        // TODO: If multiple websites are found, throw BadRequestException with a descriptive message.
        return null;
    }


}
