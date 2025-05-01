package org.example.vofasbackendv1.presentationlayer.mappers;

import org.example.vofasbackendv1.data_layer.entities.StaticQREntity;
import org.example.vofasbackendv1.presentationlayer.dto.StaticQRDTO;

public class StaticQRMapper {
    public static StaticQRDTO entityToDTO(StaticQREntity entity) {
        if (entity == null) return null;

        StaticQRDTO dto = new StaticQRDTO();
        dto.setQrID(entity.getQrID());
        dto.setLocation(entity.getLocation());
        dto.setInformativeText(entity.getInformativeText());

        dto.setFeedbackSourceId(entity.getFeedbackSourceId());
        dto.setSourceName(entity.getSourceName());
        dto.setSourceType(entity.getSourceType());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setState(entity.getState());

        return dto;
    }

    public static StaticQREntity dtoToEntity(StaticQRDTO dto) {
        if (dto == null) return null;

        StaticQREntity staticQREntity = new StaticQREntity();
        staticQREntity.setQrID(dto.getQrID());
        staticQREntity.setLocation(dto.getLocation());
        staticQREntity.setInformativeText(dto.getInformativeText());
        staticQREntity.setSourceName(dto.getSourceName());
        staticQREntity.setSourceType(dto.getSourceType());
        staticQREntity.setDescription(dto.getDescription());
        staticQREntity.setCreatedAt(dto.getCreatedAt());
        staticQREntity.setState(dto.getState());

        return staticQREntity;
    }
}