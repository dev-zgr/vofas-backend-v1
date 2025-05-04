package org.example.vofasbackendv1.presentationlayer.mappers;

import org.example.vofasbackendv1.data_layer.entities.StaticQREntity;
import org.example.vofasbackendv1.data_layer.enums.FeedbackSourceStateEnum;
import org.example.vofasbackendv1.data_layer.enums.SourceTypeEnum;
import org.example.vofasbackendv1.presentationlayer.dto.StaticQRDTO;

public class StaticQRMapper {
    public static StaticQRDTO entityToDTO(StaticQREntity entity, StaticQRDTO dto) {
        if (dto == null || entity == null) return null;
        dto.setQrID(entity.getQrID());
        dto.setLocation(entity.getLocation());
        dto.setInformativeText(entity.getInformativeText());
        dto.setFeedbackSourceId(entity.getFeedbackSourceId());
        dto.setSourceName(entity.getSourceName());
        dto.setSourceType(entity.getSourceType().toString());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setState(entity.getState().toString());
        return dto;
    }

    public static StaticQREntity dtoToEntity(StaticQRDTO dto, StaticQREntity staticQREntity) {
        if (dto == null || staticQREntity == null) return null;
        staticQREntity.setLocation(dto.getLocation());
        staticQREntity.setInformativeText(dto.getInformativeText());
        staticQREntity.setSourceName(dto.getSourceName());
        staticQREntity.setSourceType(SourceTypeEnum.valueOf(dto.getSourceType().toUpperCase()));
        staticQREntity.setDescription(dto.getDescription());
        staticQREntity.setCreatedAt(dto.getCreatedAt());
        staticQREntity.setState(FeedbackSourceStateEnum.valueOf(dto.getState().toUpperCase()));
        return staticQREntity;
    }
}