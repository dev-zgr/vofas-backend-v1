package org.example.vofasbackendv1.presentationlayer.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.vofasbackendv1.presentationlayer.dto.BaseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.StaticQRDTO;
import org.example.vofasbackendv1.presentationlayer.dto.WebsiteDTO;
import org.example.vofasbackendv1.servicelayer.interfaces.SourceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Feedback source availability API endpoints", description = "Feedback source availability API endpoints")
@RestController
@RequestMapping(value = "/vofas/api/v1/source", produces = "application/json")
@CrossOrigin(origins = "*")
public class SourceStatusController {

    private final SourceStatusService sourceStatusService;

    @Autowired
    public SourceStatusController(SourceStatusService sourceStatusService) {
        this.sourceStatusService = sourceStatusService;
    }

    @Operation(
            summary = "Fetch Website status",
            description = "Fetches the current status of the website."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @GetMapping(path = "/status/website", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<WebsiteDTO>> getWebsiteStatus() {
        // TODO returns 200 with existing website details. Only set feedbackSourceID, sourceName, sourceType, description, state, url and informative text fields.
        // TODO if the website found return 200 with the data above.
        //TODO return 404 if no website found keep in mind there is only one or no website.
        return null;
    }

    @Operation(
            summary = "Fetch Website status",
            description = "Fetches the current status of the website."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @GetMapping(path = "/status/static-qr/{qr-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<StaticQRDTO>> getStaticQRStatusByQRID(@PathVariable("qr-id") UUID staticQRID) {
        // TODO returns 200 with existing static qr details. Only set feedbackSourceID, sourceName, sourceType, description, state, qrID ,location and informative text fields.
        // TODO if the static found return 200 with the data above.
        //TODO return 404 if no static found with given qrID.
        return null;
    }


}
