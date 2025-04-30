package org.example.vofasbackendv1.presentationlayer.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.vofasbackendv1.presentationlayer.dto.BaseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.ResponseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.StaticQRDTO;
import org.example.vofasbackendv1.servicelayer.interfaces.StaticQRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        summary = "Fetch All Static QRs by Status",
        description = "Fetches all Static QRs based on their status (active or passive), with sorting and pagination."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
        @ApiResponse(responseCode = "204", description = "HTTP Status No Content"),
        @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
        @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
        @ApiResponse(responseCode = "403", description = "HTTP Status Forbidden"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @GetMapping(path = "/static-qr/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<Page<StaticQRDTO>>> getAllStaticQRs(
            @PathVariable("status") String status,
            @RequestParam(value = "sort-by", defaultValue = "qrID") String sortBy,
            @RequestParam(value = "ascending", defaultValue = "true") boolean ascending,
            @RequestParam(value = "page-no", defaultValue = "0") int pageNo) {
        // TODO: Validate 'status'. Allowed values are 'active', 'passive'.
        //       If invalid, throw BadRequestException with a clear message.

        // TODO: Validate 'sortBy' parameter. Allowed values: 'qrID', 'sourceName', 'createAt' and  'location'.
        //       If invalid, throw BadRequestException with a descriptive message.

        // TODO: Validate 'ascending'. It should be true or false.
        //       If invalid, throw BadRequestException with a descriptive message.

        // TODO: Implement pagination. Use 'pageNo' and return a page of StaticQRs per page. Each page should contain 10 StaticQRs.
        //       If the 'pageNo' exceeds available pages, return an empty list with 204 status code.

        // TODO: Fetch StaticQRs based on 'status' (active/passive), 'sortBy', and 'ascending' from the database.

        // TODO: Map StaticQR entities to StaticQRDTOs and wrap in BaseDTO.

        return null; // Return the list wrapped in BaseDTO with HTTP 200 OK.
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
        // TODO: Validate 'feedbackSourceID'. It must not be null or less than 1.
        //       If invalid, throw BadRequestException with a descriptive message.

        // TODO: Fetch StaticQR entity by 'feedbackSourceID' from the database.
        //       If not found, throw ResourceNotFoundException or return 404.

        // TODO: Map the retrieved StaticQREntity to StaticQRDTO.

        // TODO: Wrap the StaticQRDTO in BaseDTO and return with HTTP 200 OK.
        return null;
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
    public ResponseEntity<BaseDTO<StaticQRDTO>> createStaticQR(@RequestBody @Valid StaticQRDTO staticQRDTO) {
        // TODO: Validate StaticQRDTO fields using annotations or custom logic if necessary.
        //       If any validation fails, throw BadRequestException with a descriptive message.

        // TODO: Map StaticQRDTO to StaticQREntity.

        // TODO: Save the StaticQREntity to the database.

        // TODO: Convert the saved StaticQREntity back to StaticQRDTO.

        // TODO: Wrap the StaticQRDTO in BaseDTO and return it with HTTP 201 Created.
        return null;
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
    public ResponseEntity<BaseDTO<ResponseDTO>> updateStaticQRByFeedbackSourceID(
            @PathVariable("feedbackSourceID") Long feedbackSourceID,
            @RequestBody @Valid StaticQRDTO staticQRDTO) {
        // TODO: Validate 'feedbackSourceID'. It must not be null or less than 1.
        //       If invalid, throw BadRequestException with a descriptive message.

        // TODO: Fetch StaticQR entity by 'feedbackSourceID'.
        //       If not found, throw ResourceNotFoundException (or return 404).

        // TODO: Ensure that only 'sourceName', 'description', 'state', and 'informativeText' are updated.
        //       If other fields are changed, throw BadRequestException with a descriptive message.

        // TODO: Update the allowed fields (sourceName, description, state, informativeText) in the StaticQR entity.

        // TODO: Save the updated StaticQR entity to the database.

        // TODO: Convert the updated StaticQR entity back to StaticQRDTO.

        // TODO: Wrap the updated StaticQRDTO in BaseDTO and return it with HTTP 200 OK.
        return null;
    }

}
