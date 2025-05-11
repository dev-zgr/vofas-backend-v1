package org.example.vofasbackendv1.presentationlayer.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.vofasbackendv1.constants.SourceConstants;
import org.example.vofasbackendv1.constants.StaticQRConstants;
import org.example.vofasbackendv1.constants.WebsiteConstants;
import org.example.vofasbackendv1.presentationlayer.dto.BaseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.StaticQRDTO;
import org.example.vofasbackendv1.presentationlayer.dto.WebsiteDTO;
import org.example.vofasbackendv1.servicelayer.interfaces.SourceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        WebsiteDTO websiteDTO = sourceStatusService.getWebsiteStatus();
        BaseDTO<WebsiteDTO> response = new BaseDTO<>();
        response.setSourceName(SourceConstants.WEBSITE);
        response.setMessage(WebsiteConstants.WEBSITE_FETCH_SUCCESS);
        response.setRequestedAt(LocalDateTime.now());
        response.setContent(websiteDTO);
        return ResponseEntity.ok(response);
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
        StaticQRDTO staticQRDTO = sourceStatusService.getStaticQRStatusByQRID(staticQRID);
        BaseDTO<StaticQRDTO> response = new BaseDTO<>();
        response.setSourceName(SourceConstants.STATIC_QR);
        response.setMessage(StaticQRConstants.STATICQR_FETCH_SUCCESS);
        response.setRequestedAt(LocalDateTime.now());
        response.setContent(staticQRDTO);
        return ResponseEntity.ok(response);
    }


}
