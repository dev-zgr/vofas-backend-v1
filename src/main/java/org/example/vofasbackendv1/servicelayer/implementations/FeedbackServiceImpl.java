package org.example.vofasbackendv1.servicelayer.implementations;

import org.example.vofasbackendv1.components.OpenAIConnector;
import org.example.vofasbackendv1.constants.FeedbackConstants;
import org.example.vofasbackendv1.constants.SourceConstants;
import org.example.vofasbackendv1.data_layer.entities.FeedbackEntity;
import org.example.vofasbackendv1.data_layer.entities.FeedbackSourceEntity;
import org.example.vofasbackendv1.data_layer.entities.VoiceFeedbackEntity;
import org.example.vofasbackendv1.data_layer.enums.*;
import org.example.vofasbackendv1.data_layer.repositories.FeedbackRepository;
import org.example.vofasbackendv1.data_layer.repositories.StaticQRRepository;
import org.example.vofasbackendv1.data_layer.repositories.VoiceFeedbackRepository;
import org.example.vofasbackendv1.data_layer.repositories.WebsiteRepository;
import org.example.vofasbackendv1.exceptions.InvalidSourceException;
import org.example.vofasbackendv1.exceptions.NoContentException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.messaging.config.RabbitMQConfig;
import org.example.vofasbackendv1.messaging.message.FeedbackProcessingMessage;
import org.example.vofasbackendv1.presentationlayer.dto.FeedbackDTO;
import org.example.vofasbackendv1.presentationlayer.dto.FeedbackFilterDTO;
import org.example.vofasbackendv1.presentationlayer.dto.FeedbackRequestDTO;
import org.example.vofasbackendv1.presentationlayer.mappers.FeedbackMapper;
import org.example.vofasbackendv1.servicelayer.interfaces.FeedbackService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final VoiceFeedbackRepository voiceFeedbackRepository;

    @Value("${vofas.storage.filepath}")
    private String filepath;
    private final RabbitTemplate rabbitTemplate;
    private final FeedbackRepository feedbackRepository;
    private final WebsiteRepository websiteRepository;
    private final StaticQRRepository staticQRRepository;
    private final OpenAIConnector openAIConnector;

    @Autowired
    public FeedbackServiceImpl(RabbitTemplate rabbitTemplate, FeedbackRepository feedbackRepository, WebsiteRepository websiteRepository, StaticQRRepository staticQRRepository, VoiceFeedbackRepository voiceFeedbackRepository, OpenAIConnector openAIConnector) {
        this.rabbitTemplate = rabbitTemplate;
        this.feedbackRepository = feedbackRepository;
        this.websiteRepository = websiteRepository;
        this.staticQRRepository = staticQRRepository;
        this.voiceFeedbackRepository = voiceFeedbackRepository;
        this.openAIConnector = openAIConnector;
    }

    @Override
    public Boolean saveFeedback(FeedbackRequestDTO feedbackRequestDTO) throws InvalidSourceException, ResourceNotFoundException {
        FeedbackSourceEntity feedbackSource = getFeedbackSource(feedbackRequestDTO);
        if (feedbackSource.getState() == FeedbackSourceStateEnum.PASSIVE){
            throw new InvalidSourceException(SourceConstants.FEEDBACK, "FeedbackSource state is PASSIVE");
        }
        String artifact;
        try {
            artifact = processFeedbackContent(feedbackRequestDTO);
        } catch (IOException e) {
            return false;
        }

        if (feedbackRequestDTO.getFeedbackType().equals(FeedbackTypeEnum.VOICE.toString())) {
            return handleVoiceFeedback(feedbackRequestDTO, feedbackSource, artifact);
        } else {
            return handleTextFeedback(feedbackRequestDTO, feedbackSource, artifact);
        }
    }

    @Override
    public FeedbackDTO getFeedbackByFeedbackID(Long feedbackID) throws ResourceNotFoundException {
        Optional<FeedbackEntity> feedback = feedbackRepository.findById(feedbackID);

        if (feedback.isEmpty()) {
            throw new ResourceNotFoundException("FeedbackEntity", "feedbackID", String.valueOf(feedbackID));
        }

        return FeedbackMapper.entityToDTO(feedback.get());
    }

    @Override
    public Page<FeedbackDTO> getAllFeedbacks(String sortBy, boolean ascending, int pageNo, FeedbackFilterDTO filterDTO) throws NoContentException {
        validateFeedbackFilters(filterDTO);

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, 10, sort);

        Page<FeedbackEntity> feedbackEntities;

        if (filterDTO.getStartDate() != null && filterDTO.getEndDate() != null) {
            feedbackEntities = feedbackRepository.findByFeedbackDateBetweenAndFilter(
                    filterDTO.getStartDate(),
                    filterDTO.getEndDate(),
                    filterDTO.getFeedbackStatuses(),
                    filterDTO.getFeedbackMethods(),
                    filterDTO.getSentiments(),
                    pageable
            );
        }
        else {
            feedbackEntities = feedbackRepository.findByFilter(
                    filterDTO.getFeedbackStatuses(),
                    filterDTO.getFeedbackMethods(),
                    filterDTO.getSentiments(),
                    pageable
            );
        }

        if (!feedbackEntities.hasContent()) {
            throw new NoContentException("Feedback", String.valueOf(pageNo));
        }

        return feedbackEntities.map(FeedbackMapper::entityToDTO);
    }

    @RabbitListener(queues = RabbitMQConfig.TEXT_QUEUE, concurrency = "10")
    public void processTextFeedback(FeedbackProcessingMessage message) {
        final String SENTIMENT_PROMPT = "Lütfen verilen artifacti sadece SENTIMENT açısından değerlendir. " +
                "Sonuçlardan sadece birini döndür: POSITIVE, NEUTRAL veya NEGATIVE. " +
                "Sonuç yalnızca bir kelime olmalı ve hepsi büyük harflerle yazılmalıdır. " +
                "Boş satır veya ek bilgiye izin yok.";
        SentimentStateEnum sentimentStateEnum = null;
        LocalDateTime sentToSentimentAnalysis;
        LocalDateTime receivedFromSentimentAnalysis;
        int retryCount = 0;
        final int MAX_RETRIES = 5;
        do {
            sentToSentimentAnalysis = LocalDateTime.now();
            String httpResponse = openAIConnector.askFeedbackSentiment(SENTIMENT_PROMPT, message.getArtifact());
            receivedFromSentimentAnalysis = LocalDateTime.now();

            String sentiment = openAIConnector.extractSentiment(httpResponse).trim();
            if (sentiment.equals("POSITIVE") || sentiment.equals("NEUTRAL") || sentiment.equals("NEGATIVE")) {
                sentimentStateEnum = SentimentStateEnum.valueOf(sentiment);
            }
            retryCount++;
            if (retryCount >= MAX_RETRIES) {
                break;
            }
        } while (sentimentStateEnum == null);
        Optional<FeedbackEntity> feedbackEntityOptional = feedbackRepository.findById(message.getFeedbackId());
        if (feedbackEntityOptional.isPresent()) {
            FeedbackEntity feedbackEntity = feedbackEntityOptional.get();
            feedbackEntity.setSentiment(sentimentStateEnum);
            feedbackEntity.setFeedbackStatus(FeedbackStatusEnum.READY);
            feedbackEntity.setSentToSentimentAnalysis(sentToSentimentAnalysis);
            feedbackEntity.setReceivedFromSentimentAnalysis(receivedFromSentimentAnalysis);
            feedbackRepository.save(feedbackEntity);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.VOICE_QUEUE, concurrency = "10")
    public void processVoiceFeedback(FeedbackProcessingMessage message) {
        SentimentStateEnum sentimentStateEnum = null;
        LocalDateTime sentToTranscriptionAt;
        LocalDateTime receivedFromTranscriptionAnalysis;
        LocalDateTime sentToSentimentAnalysis;
        LocalDateTime receivedFromSentimentAnalysis;
        int transcriptionRetryCount = 0;
        String transcriptionResponse;
        int retryCount = 0;
        final int MAX_RETRIES = 5;

        do {
            try {
                String filePath = message.getArtifact();
                File audioFile = new File(filePath);
                sentToTranscriptionAt = LocalDateTime.now();
                transcriptionResponse = openAIConnector.transcribeVoiceFeedback(audioFile);
                receivedFromTranscriptionAnalysis = LocalDateTime.now();
                transcriptionResponse = openAIConnector.extractTranscription(transcriptionResponse);
                transcriptionRetryCount++;
            } catch (IOException e) {
                return;
            }
        } while (transcriptionResponse == null && transcriptionRetryCount < MAX_RETRIES);

        final String SENTIMENT_PROMPT = "Lütfen verilen artifacti sadece SENTIMENT açısından değerlendir. " +
                "Sonuçlardan sadece birini döndür: POSITIVE, NEUTRAL veya NEGATIVE. " +
                "Sonuç yalnızca bir kelime olmalı ve hepsi büyük harflerle yazılmalıdır. " +
                "Boş satır veya ek bilgiye izin yok.";

        do {
            sentToSentimentAnalysis = LocalDateTime.now();
            String httpResponse = openAIConnector.askFeedbackSentiment(SENTIMENT_PROMPT, message.getArtifact());
            receivedFromSentimentAnalysis = LocalDateTime.now();
            String sentiment = openAIConnector.extractSentiment(httpResponse).trim();
            if (sentiment.equals("POSITIVE") || sentiment.equals("NEUTRAL") || sentiment.equals("NEGATIVE")) {
                sentimentStateEnum = SentimentStateEnum.valueOf(sentiment);
            }
            retryCount++;
            if (retryCount >= MAX_RETRIES) {
                break;
            }
        } while (sentimentStateEnum == null);


        Optional<VoiceFeedbackEntity> voiceFeedbackEntityOptional = voiceFeedbackRepository.findById(message.getFeedbackId());
        if (voiceFeedbackEntityOptional.isPresent()) {
            VoiceFeedbackEntity voiceFeedbackEntity = voiceFeedbackEntityOptional.get();
            voiceFeedbackEntity.setFeedbackStatus(FeedbackStatusEnum.READY);
            voiceFeedbackEntity.setContent(transcriptionResponse);
            voiceFeedbackEntity.setSentiment(sentimentStateEnum);
            voiceFeedbackEntity.setSentToSentimentAnalysis(sentToSentimentAnalysis);
            voiceFeedbackEntity.setReceivedFromSentimentAnalysis(receivedFromSentimentAnalysis);
            voiceFeedbackEntity.setSentForTranscription(sentToTranscriptionAt);
            voiceFeedbackEntity.setReceivedFromTranscription(receivedFromTranscriptionAnalysis);
            voiceFeedbackRepository.save(voiceFeedbackEntity);
        }
    }


    private FeedbackSourceEntity getFeedbackSource(FeedbackRequestDTO feedbackRequestDTO) throws ResourceNotFoundException, InvalidSourceException {
        FeedbackSourceEntity feedbackSource = switch (feedbackRequestDTO.getFeedbackMethod()) {
            case "WEBSITE" -> websiteRepository.findById(feedbackRequestDTO.getFeedbackSourceId())
                    .orElseThrow(() -> new ResourceNotFoundException(SourceConstants.WEBSITE, "ID", feedbackRequestDTO.getFeedbackSourceId().toString()));
            case "STATIC_QR" -> staticQRRepository.findById(feedbackRequestDTO.getFeedbackSourceId())
                    .orElseThrow(() -> new ResourceNotFoundException(SourceConstants.WEBSITE, "ID", feedbackRequestDTO.getFeedbackSourceId().toString()));
            default ->
                    throw new InvalidSourceException(SourceConstants.FEEDBACK, FeedbackConstants.INVALID_FEEDBACK_SOURCE);
        };
        if (feedbackRequestDTO.getFeedbackMethod().equals("WEBSITE") && feedbackSource.getSourceType() != SourceTypeEnum.WEBSITE) {
            throw new InvalidSourceException(SourceConstants.FEEDBACK, FeedbackConstants.NON_MATCHING_METHOD_SOURCES);
        } else if (feedbackRequestDTO.getFeedbackMethod().equals("STATIC_QR") && feedbackSource.getSourceType() != SourceTypeEnum.STATIC_QR) {
            throw new InvalidSourceException(SourceConstants.FEEDBACK, FeedbackConstants.NON_MATCHING_METHOD_SOURCES);
        }
        return feedbackSource;
    }

    private void validateFileExtension(MultipartFile file, String validExtension) throws InvalidSourceException {
        if (!Objects.requireNonNull(file.getOriginalFilename()).toLowerCase().endsWith(validExtension)) {
            throw new InvalidSourceException(SourceConstants.FEEDBACK, FeedbackConstants.INVALID_FEEDBACK_CONTENT_TXT);
        }
    }

    private String saveVoiceFeedbackFile(MultipartFile file) throws InvalidSourceException, IOException {
        Long feedbackFileId = Optional.ofNullable(feedbackRepository.findMaxId()).orElse(0L);
        long newId = feedbackFileId + 1;
        String newFileName = "feedback_" + newId + ".wav";
        Path targetLocation = Paths.get(filepath).resolve(newFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return targetLocation.toString();
    }

    private Boolean handleTextFeedback(FeedbackRequestDTO feedbackRequestDTO, FeedbackSourceEntity feedbackSource, String artifact) {
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setFeedbackDate(LocalDateTime.now());
        feedbackEntity.setFeedbackStatus(FeedbackStatusEnum.WAITING_SENTIMENT_ANALYSIS);
        feedbackEntity.setContent(artifact);
        feedbackEntity.setFeedbackSource(feedbackSource);
        feedbackEntity.setMethodEnum(FeedbackMethodEnum.valueOf(feedbackRequestDTO.getFeedbackMethod()));
        feedbackEntity.setTypeEnum(FeedbackTypeEnum.valueOf(feedbackRequestDTO.getFeedbackType()));

        FeedbackEntity savedFeedback = feedbackRepository.save(feedbackEntity);
        sendFeedbackProcessingMessage(savedFeedback.getFeedbackId(), FeedbackTypeEnum.TEXT, artifact);
        return true;
    }

    private Boolean handleVoiceFeedback(FeedbackRequestDTO feedbackRequestDTO, FeedbackSourceEntity feedbackSource, String artifact) {
        VoiceFeedbackEntity voiceFeedbackEntity = new VoiceFeedbackEntity();
        voiceFeedbackEntity.setFeedbackDate(LocalDateTime.now());
        voiceFeedbackEntity.setFeedbackStatus(FeedbackStatusEnum.WAITING_TRANSCRIPTION);
        voiceFeedbackEntity.setFeedbackSource(feedbackSource);
        voiceFeedbackEntity.setMethodEnum(FeedbackMethodEnum.valueOf(feedbackRequestDTO.getFeedbackMethod()));
        voiceFeedbackEntity.setTypeEnum(FeedbackTypeEnum.valueOf(feedbackRequestDTO.getFeedbackType()));
        voiceFeedbackEntity.setFilePath(artifact);
        VoiceFeedbackEntity savedVoiceFeedback = voiceFeedbackRepository.save(voiceFeedbackEntity);
        sendFeedbackProcessingMessage(savedVoiceFeedback.getFeedbackId(), FeedbackTypeEnum.VOICE, artifact);
        return true;
    }

    private void sendFeedbackProcessingMessage(Long feedbackId, FeedbackTypeEnum feedbackType, String artifact) {
        FeedbackProcessingMessage message = new FeedbackProcessingMessage();
        message.setFeedbackId(feedbackId);
        message.setFeedbackType(feedbackType);
        message.setArtifact(artifact);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                feedbackType == FeedbackTypeEnum.TEXT ? RabbitMQConfig.TEXT_ROUTING_KEY : RabbitMQConfig.VOICE_ROUTING_KEY,
                message
        );
    }

    private String processFeedbackContent(FeedbackRequestDTO feedbackRequestDTO) throws InvalidSourceException, IOException {
        MultipartFile file = feedbackRequestDTO.getContent();
        if (file == null) {
            throw new InvalidSourceException(SourceConstants.FEEDBACK, FeedbackConstants.NULL_CONTENT);
        }

        if (feedbackRequestDTO.getFeedbackType().equals(FeedbackTypeEnum.TEXT.toString())) {
            validateFileExtension(file, ".txt");
            return new String(file.getBytes(), StandardCharsets.UTF_8)
                    .replaceAll("[\\r\\n]+", " ")  // replace newlines with space
                    .replaceAll("\"", "\\\\\"")    // escape double quotes
                    .trim();
        } else if (feedbackRequestDTO.getFeedbackType().equals(FeedbackTypeEnum.VOICE.toString())) {
            validateFileExtension(file, ".wav");
            return saveVoiceFeedbackFile(file);
        } else {
            throw new InvalidSourceException(SourceConstants.FEEDBACK, FeedbackConstants.INVALID_FEEDBACK_TYPE);
        }
    }

    private void validateFeedbackFilters(FeedbackFilterDTO filterDTO) throws InvalidParameterException {
        Set<String> validFeedbackStatuses = Set.of("RECEIVED", "WAITING_TRANSCRIPTION", "WAITING_SENTIMENT_ANALYSIS", "READY");
        Set<String> validFeedbackMethods = Set.of("WEBSITE", "KIOSK", "STATIC_QR", "DYNAMIC_QR");
        Set<String> validSentiments = Set.of("POSITIVE", "NEUTRAL", "NEGATIVE");

        if (filterDTO.getFeedbackStatuses() != null) {
            for (String status : filterDTO.getFeedbackStatuses()) {
                if (!validFeedbackStatuses.contains(status)) {
                    throw new InvalidParameterException("Invalid feedback status: " + status);
                }
            }
        }

        if (filterDTO.getFeedbackMethods() != null) {
            for (String method : filterDTO.getFeedbackMethods()) {
                if (!validFeedbackMethods.contains(method)) {
                    throw new InvalidParameterException("Invalid feedback method: " + method);
                }
            }
        }

        if (filterDTO.getSentiments() != null) {
            for (String sentiment : filterDTO.getSentiments()) {
                if (!validSentiments.contains(sentiment)) {
                    throw new InvalidParameterException("Invalid sentiment: " + sentiment);
                }
            }
        }
    }
}
