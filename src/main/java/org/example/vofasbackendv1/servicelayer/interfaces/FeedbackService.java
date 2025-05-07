package org.example.vofasbackendv1.servicelayer.interfaces;

import org.example.vofasbackendv1.exceptions.InvalidSourceException;
import org.example.vofasbackendv1.exceptions.NoContentException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.messaging.message.FeedbackProcessingMessage;
import org.example.vofasbackendv1.presentationlayer.dto.FeedbackDTO;
import org.example.vofasbackendv1.presentationlayer.dto.FeedbackFilterDTO;
import org.example.vofasbackendv1.presentationlayer.dto.FeedbackRequestDTO;
import org.example.vofasbackendv1.presentationlayer.dto.StaticQRDTO;
import org.springframework.data.domain.Page;

import java.security.InvalidParameterException;

public interface FeedbackService {

    Boolean saveFeedback(FeedbackRequestDTO feedbackRequestDTO) throws InvalidSourceException;
    FeedbackDTO getFeedbackByFeedbackID(Long feedbackID) throws ResourceNotFoundException;
    Page<FeedbackDTO> getAllFeedbacks(String sortBy, boolean ascending, int pageNo, FeedbackFilterDTO filterDTO) throws NoContentException, InvalidParameterException;
    void processTextFeedback(FeedbackProcessingMessage message);
    void processVoiceFeedback(FeedbackProcessingMessage message);


    }
