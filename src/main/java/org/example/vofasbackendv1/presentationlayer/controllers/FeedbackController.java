package org.example.vofasbackendv1.presentationlayer.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.example.vofasbackendv1.constants.FeedbackConstants;
import org.example.vofasbackendv1.constants.SourceConstants;
import org.example.vofasbackendv1.presentationlayer.dto.*;
import org.example.vofasbackendv1.servicelayer.interfaces.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Feedback API endpoints", description = "Feedback QR API endpoints")
@RestController
@RequestMapping(value = "/vofas/api/v1", produces = "application/json")
@CrossOrigin(origins = "*")
public class FeedbackController {

    private final FeedbackService feedbackService;

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
            @ApiResponse(responseCode = "417", description = "Expectation Failed"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @PostMapping(path = "/feedback", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<ResponseDTO>> processFeedback(@Valid @ModelAttribute FeedbackRequestDTO feedbackRequestDTO){
        boolean feedbackSaved = feedbackService.saveFeedback(feedbackRequestDTO);
        ResponseDTO responseDTO;
        BaseDTO<ResponseDTO> baseDTO;
        HttpStatus status;

        if (feedbackSaved) {
            responseDTO = new ResponseDTO(
                    FeedbackConstants.HTTP_CREATED,
                    FeedbackConstants.FEEDBACK_SAVE_SUCCESS
            );
            status = HttpStatus.CREATED;
        } else {
            responseDTO = new ResponseDTO(
                    FeedbackConstants.HTTP_EXPECTATION_FAILED,
                    FeedbackConstants.EXPECTATION_FAILED_MESSAGE
            );
            status = HttpStatus.EXPECTATION_FAILED;
        }

        baseDTO = new BaseDTO<>(
                SourceConstants.FEEDBACK,
                feedbackSaved ? FeedbackConstants.FEEDBACK_SAVE_SUCCESS : FeedbackConstants.EXPECTATION_FAILED_MESSAGE,
                LocalDateTime.now(),
                responseDTO
        );
        return new ResponseEntity<>(baseDTO, status);
    }

    @Operation(
            summary = "Get Feedback by ID",
            description = "Returns the feedback entity with the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
            @ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @GetMapping(path = "/feedback/{feedbackID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<FeedbackDTO>> getFeedbackByID(
            @PathVariable("feedbackID") @Min(0) Long feedbackID

    ) {
        FeedbackDTO feedbackDTO = feedbackService.getFeedbackByFeedbackID(feedbackID);

        if (feedbackDTO == null) {
            return ResponseEntity.status(404).body(new BaseDTO<>("SYSTEM", "Feedback not found", LocalDateTime.now(), null));
        }

        BaseDTO<FeedbackDTO> response = new BaseDTO<>();
        response.setSourceName("SYSTEM");
        response.setMessage("Feedback fetched successfully");
        response.setRequestedAt(LocalDateTime.now());
        response.setContent(feedbackDTO);

        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Fetch All Feedbacks by certain filters & pagination and sorting",
            description = "Filters the feedbacks based on the given filters and returns the fetched feedbacks."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "204", description = "HTTP Status No Content"),
            @ApiResponse(responseCode = "400", description = "HTTP Status Bad Request"),
            @ApiResponse(responseCode = "401", description = "HTTP Status Unauthorized"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @GetMapping(path = "/feedback", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO<Page<FeedbackDTO>>> getAllFeedbacks(
            @RequestParam(value = "sort-by", defaultValue = "feedbackDate") @Pattern(regexp = "^(feedbackDate|feedbackId)$")String sortBy,
            @RequestParam(value = "ascending", defaultValue = "false") boolean ascending,
            @RequestParam(value = "page-no", defaultValue = "0") @Min(0) int pageNo,
            @RequestBody FeedbackFilterDTO feedbackFilterDTO) {
        Page<FeedbackDTO> feedbackDTOPage = feedbackService.getAllFeedbacks(sortBy, ascending, pageNo, feedbackFilterDTO);

        if (feedbackDTOPage == null || feedbackDTOPage.isEmpty()) {
            BaseDTO<Page<FeedbackDTO>> baseDTO = new BaseDTO<>(
                    SourceConstants.FEEDBACK,
                    "No feedbacks found for the given criteria.",
                    LocalDateTime.now(),
                    Page.empty()
            );
            return new ResponseEntity<>(baseDTO, HttpStatus.NO_CONTENT);
        }

        BaseDTO<Page<FeedbackDTO>> baseDTO = new BaseDTO<>(
                SourceConstants.FEEDBACK,
                "Feedbacks fetched successfully",
                LocalDateTime.now(),
                feedbackDTOPage
        );
        return new ResponseEntity<>(baseDTO, HttpStatus.OK);
    }

}
