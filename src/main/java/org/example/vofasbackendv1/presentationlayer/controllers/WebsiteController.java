package org.example.vofasbackendv1.presentationlayer.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.example.vofasbackendv1.constants.SourceConstants;
import org.example.vofasbackendv1.constants.WebsiteConstants;
import org.example.vofasbackendv1.presentationlayer.dto.BaseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.WebsiteDTO;
import org.example.vofasbackendv1.servicelayer.interfaces.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Website API endpoints", description = "Website API endpoints")
@RestController
@RequestMapping(value = "/vofas/api/v1", produces = "application/json")
@CrossOrigin(origins = "*")
public class WebsiteController {

    private final WebsiteService websiteService;

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
        WebsiteDTO createdWebsite = websiteService.createWebsite(websiteDTO);
        BaseDTO<WebsiteDTO> response = new BaseDTO<>();
        response.setSourceName(SourceConstants.WEBSITE);
        response.setMessage(WebsiteConstants.WEBSITE_CREATED_SUCCESS);
        response.setRequestedAt(LocalDateTime.now());
        response.setContent(createdWebsite);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
            @PathVariable("websiteID") @Min(0) Long websiteID,
            @RequestBody @Valid WebsiteDTO websiteDTO) {

        WebsiteDTO updatedWebsite = websiteService.updateWebsiteByID(websiteID, websiteDTO);
        BaseDTO<WebsiteDTO> response = new BaseDTO<>();
        response.setSourceName(SourceConstants.WEBSITE);
        response.setMessage(WebsiteConstants.WEBSITE_UPDATED_SUCCESS);
        response.setRequestedAt(LocalDateTime.now());
        response.setContent(updatedWebsite);
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
        WebsiteDTO websiteDTO = websiteService.getWebsite();
        BaseDTO<WebsiteDTO> response = new BaseDTO<>();
        response.setSourceName(SourceConstants.WEBSITE);
        response.setMessage(WebsiteConstants.WEBSITE_FETCH_SUCCESS);
        response.setRequestedAt(LocalDateTime.now());
        response.setContent(websiteDTO);

        return ResponseEntity.ok(response);
    }
}
