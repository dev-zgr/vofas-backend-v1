package org.example.vofasbackendv1.presentationlayer.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.example.vofasbackendv1.presentationlayer.dto.*;
import org.example.vofasbackendv1.servicelayer.interfaces.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Feedback API endpoints", description = "Feedback QR API endpoints")
@RestController
@RequestMapping(value = "/vofas/api/v1", produces = "application/json")
@CrossOrigin(origins = "*")
public class FeedbackController {

    private FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }


    @Operation(
            summary = "Create a new Feedback Entity or VoiceFeedbackEntity ",
            description = "Creates a new Feedback Entity or VoiceFeedbackEntity based on the provided request.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "HTTP Status Created"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Forbidden"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @PostMapping(path = "/feedback", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<ResponseDTO>> processFeedback(@Valid @RequestBody FeedbackRequestDTO feedbackRequestDTO) {
        // TODO returns 201 with the created static qr details. Only set feedbackSourceID, sourceName, sourceType, description, state, url and informative text fields.
        // TODO if the static qr found return 200 with the data above.
        // TODO return 401 if feedback validations such as validation token, website status or something else is valid.
        return null;
    }

    @Operation(
            summary = "Get Feedback by ID",
            description = "Creates a new Feedback Entity or VoiceFeedbackEntity based on the provided request.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
            @ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @GetMapping(path = "/feedback/{feedbackID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<FeedbackDTO>> processFeedback(
            @PathVariable("feedbackID") @Min(0) Long feedbackID

    ) {
        //TODO authorization is performed by security config automatically
        //TODO return 200 with FeedbackDTO or VoiceFeedbackDTO if value is found.
        //TODO 400 bad data is returned automatically on validation checks.
        //TODO return 404 not found if feedback is not found
        //TODO just throw any exception if - no need for catch since global exception handler does - required
        return null;
    }


    @Operation(
            summary = "Fetch All Feedbacks by certain filters & pagination and sorting",
            description = "Fetches all Static QRs based on their state (active or passive), with sorting and pagination."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "204", description = "HTTP Status No Content"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @PostMapping(path = "/static-qr", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<Page<FeedbackDTO>>> getAllStaticQRs(
            @RequestParam(value = "sort-by", defaultValue = "feedbackDate") @Pattern(regexp = "^(feedbackDate|feedbackId)$")String sortBy,
            @RequestParam(value = "ascending", defaultValue = "false") boolean ascending,
            @RequestParam(value = "page-no", defaultValue = "0") @Min(0) int pageNo,
            @RequestBody FeedbackFilterDTO feedbackFilterDTO) {
        //TODO return the value based on the filterDTO and make sure to make sorting ascending and page-no
        //TODO 401 Unauthorized is performed by security config automatically you dont need to implement
        //TODO return 200 NO Content if page is empty
        //TODO throw invalid parameter exception if feedbackFilterDTO's fields aren't valid you dont need to catch the exception.Exceptions caught by global exception handlers
        // TODO if there are any other exceptions: if you can fix the situation catch it and fix it, if you can't just throw it it'll be handled by global exception handler.
        return null;
    }

    //TODO add Spring Webflux, SSE or WebSocket for real-time feedback updates

}
