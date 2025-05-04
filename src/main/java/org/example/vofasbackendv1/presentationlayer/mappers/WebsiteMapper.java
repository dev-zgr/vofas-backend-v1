package org.example.vofasbackendv1.presentationlayer.mappers;

import org.example.vofasbackendv1.data_layer.entities.WebsiteEntity;
import org.example.vofasbackendv1.data_layer.enums.FeedbackSourceStateEnum;
import org.example.vofasbackendv1.data_layer.enums.SourceTypeEnum;
import org.example.vofasbackendv1.presentationlayer.dto.WebsiteDTO;

public class WebsiteMapper {
    public static WebsiteDTO entityToDTO(WebsiteEntity websiteEntity) {
        if (websiteEntity == null) {
            return null;
        }

        WebsiteDTO dto = new WebsiteDTO();

        dto.setFeedbackSourceId(websiteEntity.getFeedbackSourceId());
        dto.setSourceName(websiteEntity.getSourceName());
        dto.setSourceType(websiteEntity.getSourceType().toString());
        dto.setDescription(websiteEntity.getDescription());
        dto.setCreatedAt(websiteEntity.getCreatedAt());
        dto.setState(websiteEntity.getState().toString());
        dto.setUrl(websiteEntity.getUrl());
        dto.setInformativeText(websiteEntity.getInformativeText());

        return dto;
    }

    public static WebsiteEntity dtoToEntity(WebsiteDTO websiteDTO) {
        if (websiteDTO == null) {
            return null;
        }

        WebsiteEntity entity = new WebsiteEntity();

        entity.setSourceName(websiteDTO.getSourceName());
        entity.setSourceType(SourceTypeEnum.valueOf(websiteDTO.getSourceType().toUpperCase()));
        entity.setDescription(websiteDTO.getDescription());
        entity.setCreatedAt(websiteDTO.getCreatedAt() != null ? websiteDTO.getCreatedAt() : java.time.LocalDateTime.now());
        entity.setState(FeedbackSourceStateEnum.valueOf(websiteDTO.getState().toUpperCase()));
        entity.setUrl(websiteDTO.getUrl());
        entity.setInformativeText(websiteDTO.getInformativeText());

        return entity;
    }
}
