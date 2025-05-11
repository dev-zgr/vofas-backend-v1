package org.example.vofasbackendv1.presentationlayer.mappers;

import org.example.vofasbackendv1.data_layer.entities.WebsiteEntity;
import org.example.vofasbackendv1.data_layer.enums.FeedbackSourceStateEnum;
import org.example.vofasbackendv1.data_layer.enums.SourceTypeEnum;
import org.example.vofasbackendv1.presentationlayer.dto.WebsiteDTO;

import java.time.LocalDateTime;

public class WebsiteMapper {
    public static WebsiteDTO entityToDTO(WebsiteEntity websiteEntity, WebsiteDTO websiteDTO) {
        if (websiteEntity == null || websiteDTO == null) {
            return null;
        }
        websiteDTO.setFeedbackSourceId(websiteEntity.getFeedbackSourceId());
        websiteDTO.setSourceName(websiteEntity.getSourceName());
        websiteDTO.setSourceType(websiteEntity.getSourceType().toString());
        websiteDTO.setDescription(websiteEntity.getDescription());
        websiteDTO.setCreatedAt(websiteEntity.getCreatedAt());
        websiteDTO.setState(websiteEntity.getState().toString());
        websiteDTO.setUrl(websiteEntity.getUrl());
        websiteDTO.setInformativeText(websiteEntity.getInformativeText());

        return websiteDTO;
    }

    public static WebsiteEntity dtoToEntity(WebsiteDTO websiteDTO, WebsiteEntity websiteEntity) {
        if (websiteDTO == null || websiteEntity == null) {
            return null;
        }

        websiteEntity.setSourceName(websiteDTO.getSourceName());
        websiteEntity.setSourceType(SourceTypeEnum.valueOf(websiteDTO.getSourceType().toUpperCase()));
        websiteEntity.setDescription(websiteDTO.getDescription());
        websiteEntity.setCreatedAt(websiteDTO.getCreatedAt() != null ? websiteDTO.getCreatedAt() : LocalDateTime.now());
        websiteEntity.setState(FeedbackSourceStateEnum.valueOf(websiteDTO.getState().toUpperCase()));
        websiteEntity.setUrl(websiteDTO.getUrl());
        websiteEntity.setInformativeText(websiteDTO.getInformativeText());

        return websiteEntity;
    }
}
