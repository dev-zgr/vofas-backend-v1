package org.example.vofasbackendv1.servicelayer.implementations;

import org.example.vofasbackendv1.data_layer.repositories.FeedbackRepository;
import org.example.vofasbackendv1.data_layer.repositories.StaticQRRepository;
import org.example.vofasbackendv1.data_layer.repositories.WebsiteRepository;
import org.example.vofasbackendv1.exceptions.InvalidSourceException;
import org.example.vofasbackendv1.exceptions.NoContentException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.messaging.config.RabbitMQConfig;
import org.example.vofasbackendv1.messaging.message.FeedbackProcessingMessage;
import org.example.vofasbackendv1.presentationlayer.dto.FeedbackDTO;
import org.example.vofasbackendv1.presentationlayer.dto.FeedbackFilterDTO;
import org.example.vofasbackendv1.presentationlayer.dto.FeedbackRequestDTO;
import org.example.vofasbackendv1.servicelayer.interfaces.FeedbackService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private RabbitTemplate rabbitTemplate;
    private FeedbackRepository feedbackRepository;
    private WebsiteRepository websiteRepository;
    private StaticQRRepository staticQRRepository;

    @Autowired
    public FeedbackServiceImpl(RabbitTemplate rabbitTemplate, FeedbackRepository feedbackRepository, WebsiteRepository websiteRepository, StaticQRRepository staticQRRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.feedbackRepository = feedbackRepository;
        this.websiteRepository = websiteRepository;
        this.staticQRRepository = staticQRRepository;
    }


    @Override
    public Boolean saveFeedback(FeedbackRequestDTO feedbackRequestDTO) throws InvalidSourceException {
        //TODO save feedback to the database if request is .txt turn it into string and save.
        //TODO if feedback is voice save to file system.
        //TODO get feedbackSourceID create a new Feedback entity and relate booth entities.
        // YOu need to implement feedbackDate, feedbackStatus, content, sentiment, source, methodEnum, typeEnum, validationTokenId if exist
        //TODO send FeedbackProcessingMessage with appropriate queue
        return null;
    }

    @Override
    public FeedbackDTO getFeedbackByFeedbackID(Long feedbackID) throws ResourceNotFoundException {
        //TODO get feedbacks by feedback id throw ResourceNotFoundException if feedback not found. You don't need to catch exception
        //TODO since its already handled by the global exception handler.
        return null;
    }

    @Override
    public Page<FeedbackDTO> getAllFeedbacks(String sortBy, boolean ascending, int pageNo, FeedbackFilterDTO filterDTO) throws NoContentException, InvalidParameterException {
        // TODO: Implement the logic to retrieve a paginated list of feedbacks with optional sorting and filtering.
        // 1. Validate the input parameters (e.g., sortBy, ascending, pageNo, filterDTO). If any are invalid, throw InvalidParameterException.
        // 2. Apply filtering logic to query feedbacks based on the criteria provided in filterDTO (e.g., feedback type, date range).
        // 3. Sort the feedbacks based on the "sortBy" and "ascending" parameters.
        // 4. Paginate the feedbacks and return a Page<FeedbackDTO> object.
        // 5. If no feedbacks are found (empty result), throw NoContentException.
        return null;
    }

    @RabbitListener(queues = RabbitMQConfig.TEXT_QUEUE)
    public void processTextFeedback(FeedbackProcessingMessage message) {
        //TODO process text based feedback send it to sentiment analysis.
        //TODO update the actual feedback with recent data.
    }

    @RabbitListener(queues = RabbitMQConfig.VOICE_QUEUE)
    public void processVoiceFeedback(FeedbackProcessingMessage message) {
        //TODO process voice feedback send it to STT, send it to sentiment analysis.
        //TODO update the actual feedback with recent data.
    }
}
