package org.example.vofasbackendv1.presentationlayer.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.example.vofasbackendv1.constants.StaticQRConstants;
import org.example.vofasbackendv1.data_layer.enums.FeedbackSourceStateEnum;
import org.example.vofasbackendv1.data_layer.enums.SourceTypeEnum;
import org.example.vofasbackendv1.exceptions.InvalidParametersException;
import org.example.vofasbackendv1.exceptions.NoContentException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.BaseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.ResponseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.StaticQRDTO;
import org.example.vofasbackendv1.servicelayer.interfaces.StaticQRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

@Tag(name = "Static QR API endpoints", description = "Static QR API endpoints")
@RestController
@RequestMapping(value = "/vofas/api/v1", produces = "application/json")
@CrossOrigin(origins = "*")
public class StaticQRController {

    private StaticQRService staticQRService;

    @Autowired
    public StaticQRController(StaticQRService staticQRService) {
        this.staticQRService = staticQRService;
    }

    @Operation(
        summary = "Fetch All Static QRs by states",
        description = "Fetches all Static QRs based on their state (active or passive), with sorting and pagination."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status No Content"),
        @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
        @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
        @ApiResponse(responseCode = "403", description = "HTTP Status Forbidden"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @GetMapping(path = "/static-qr/state/{state}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<Page<StaticQRDTO>>> getAllStaticQRs(
            @PathVariable("state") FeedbackSourceStateEnum state,
            @RequestParam(value = "sort-by", defaultValue = "qrID") String sortBy,
            @RequestParam(value = "ascending", defaultValue = "true") boolean ascending,
            @RequestParam(value = "page-no", defaultValue = "0") int pageNo) throws BadRequestException {

        try {
            Page<StaticQRDTO> resultPage = staticQRService.getAllStaticQRs(state, sortBy, ascending, pageNo);

            BaseDTO<Page<StaticQRDTO>> response = new BaseDTO<>();
            response.setSourceName(SourceConstants.STATIC_QR);
            response.setMessage(StaticQRConstants.STATICQRS_FETCH_SUCCESS);
            response.setRequestedAt(LocalDateTime.now());
            response.setContent(resultPage);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (InvalidParametersException e) {
            throw new BadRequestException(e.getMessage());
        } catch (NoContentException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(
            summary = "Fetch StaticQR by Feedback Source ID",
            description = "Fetches a single Static QR entity using its feedbackSourceID. " +
                    "Primarily used for detail views or admin  and  company representative queries."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
            @ApiResponse(responseCode = "403", description = "HTTP Status Forbidden"),
            @ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @GetMapping(path = "/static-qr/{feedbackSourceID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<StaticQRDTO>> getStaticQRByFeedbackSourceID(@PathVariable("feedbackSourceID") Long feedbackSourceID) {
        try {
            StaticQRDTO staticQRDTO = staticQRService.getStaticQRByFeedbackSourceID(feedbackSourceID);

            BaseDTO<StaticQRDTO> response = new BaseDTO<>();
            response.setContent(staticQRDTO);
            response.setMessage(StaticQRConstants.STATICQR_FETCH_SUCCESS);
            response.setRequestedAt(LocalDateTime.now());
            response.setSourceName("SYSTEM");

            return ResponseEntity.ok(response);
        } catch (InvalidParametersException e) {
            BaseDTO<StaticQRDTO> errorResponse = new BaseDTO<>();
            errorResponse.setMessage(StaticQRConstants.INVALID_STATICQR_ID);
            errorResponse.setRequestedAt(LocalDateTime.now());
            errorResponse.setSourceName("SYSTEM");

            return ResponseEntity.badRequest().body(errorResponse);
        } catch (ResourceNotFoundException e) {
            BaseDTO<StaticQRDTO> errorResponse = new BaseDTO<>();
            errorResponse.setMessage(StaticQRConstants.STATICQR_NOT_FOUND);
            errorResponse.setRequestedAt(LocalDateTime.now());
            errorResponse.setSourceName("SYSTEM");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            BaseDTO<StaticQRDTO> errorResponse = new BaseDTO<>();
            errorResponse.setMessage("Internal Server Error");
            errorResponse.setRequestedAt(LocalDateTime.now());
            errorResponse.setSourceName("SYSTEM");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @Operation(
            summary = "Create a New Static QR",
            description = "Creates a new Static QR using the provided StaticQRDTO. " +
                    "This endpoint is used for creating a new Static QR entry."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "HTTP Status Created"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
            @ApiResponse(responseCode = "403", description = "HTTP Status Forbidden"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @PostMapping(path = "/static-qr", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<StaticQRDTO>> createStaticQR(@RequestBody @Valid StaticQRDTO staticQRDTO) throws BadRequestException {
        try {
            StaticQRDTO savedQR = staticQRService.createStaticQR(staticQRDTO);

            BaseDTO<StaticQRDTO> response = new BaseDTO<>();
            response.setSourceName("SYSTEM");
            response.setMessage(StaticQRConstants.STATICQR_CREATED_SUCCESS);
            response.setRequestedAt(LocalDateTime.now());
            response.setContent(savedQR);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (InvalidParameterException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(
            summary = "Update Static QR by Feedback Source ID",
            description = "Updates the details of an existing Static QR using the provided StaticQRDTO and feedbackSourceID. " +
                    "Only the fields sourceName, description, state, and informativeText can be updated."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
            @ApiResponse(responseCode = "403", description = "HTTP Status Forbidden"),
            @ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @PutMapping(path = "/static-qr/{feedbackSourceID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<StaticQRDTO>> updateStaticQRByFeedbackSourceID(
            @PathVariable("feedbackSourceID") Long feedbackSourceID,
            @RequestBody @Valid StaticQRDTO staticQRDTO) {

        try {
            Boolean updateSuccess = staticQRService.updateStaticQRByFeedbackSourceID(feedbackSourceID, staticQRDTO);

            if (updateSuccess) {
                StaticQRDTO updatedStaticQRDTO = staticQRService.getStaticQRByFeedbackSourceID(feedbackSourceID);

                BaseDTO<StaticQRDTO> baseDTO = new BaseDTO<>(
                        "SYSTEM",
                        StaticQRConstants.STATICQR_UPDATED_SUCCESS,
                        LocalDateTime.now(),
                        updatedStaticQRDTO
                );

                return ResponseEntity.ok(baseDTO);
            } else {
                throw new InvalidParametersException("Failed to update StaticQR.");
            }
        } catch (InvalidParametersException e) {
            BaseDTO<StaticQRDTO> errorResponse = new BaseDTO<>(
                    "SYSTEM",
                    e.getMessage(),
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (ResourceNotFoundException e) {
            BaseDTO<StaticQRDTO> errorResponse = new BaseDTO<>(
                    "SYSTEM",
                    e.getMessage(),
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            BaseDTO<StaticQRDTO> errorResponse = new BaseDTO<>(
                    "SYSTEM",
                    "An error occurred while updating StaticQR.",
                    LocalDateTime.now(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
