package org.example.vofasbackendv1.presentationlayer.mappers;

import org.example.vofasbackendv1.data_layer.entities.FeedbackEntity;
import org.example.vofasbackendv1.presentationlayer.dto.FeedbackDTO;

public class FeedbackMapper {
    public static FeedbackDTO entityToDTO(FeedbackEntity feedbackEntity) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setFeedbackId(feedbackEntity.getFeedbackId());
        feedbackDTO.setFeedbackDate(feedbackEntity.getFeedbackDate());
        feedbackDTO.setFeedbackStatus(feedbackEntity.getFeedbackStatus());
        feedbackDTO.setContent(feedbackEntity.getContent());
        feedbackDTO.setSentiment(feedbackEntity.getSentiment());
        feedbackDTO.setMethodEnum(feedbackEntity.getMethodEnum());
        feedbackDTO.setTypeEnum(feedbackEntity.getTypeEnum());
        feedbackDTO.setValidationTokenId(feedbackEntity.getValidationTokenId());
        feedbackDTO.setSentToSentimentAnalysis(feedbackEntity.getSentToSentimentAnalysis());
        feedbackDTO.setReceivedFromSentimentAnalysis(feedbackEntity.getReceivedFromSentimentAnalysis());

        return feedbackDTO;
    }

    public static FeedbackEntity dtoToEntity(FeedbackDTO feedbackDTO) {
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setFeedbackDate(feedbackDTO.getFeedbackDate());
        feedbackEntity.setFeedbackStatus(feedbackDTO.getFeedbackStatus());
        feedbackEntity.setContent(feedbackDTO.getContent());
        feedbackEntity.setSentiment(feedbackDTO.getSentiment());
        feedbackEntity.setMethodEnum(feedbackDTO.getMethodEnum());
        feedbackEntity.setTypeEnum(feedbackDTO.getTypeEnum());
        feedbackEntity.setValidationTokenId(feedbackDTO.getValidationTokenId());
        feedbackEntity.setSentToSentimentAnalysis(feedbackDTO.getSentToSentimentAnalysis());
        feedbackEntity.setReceivedFromSentimentAnalysis(feedbackDTO.getReceivedFromSentimentAnalysis());

        return feedbackEntity;
    }
}
