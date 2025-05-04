package org.example.vofasbackendv1.servicelayer.implementations;

import org.example.vofasbackendv1.constants.SourceConstants;
import org.example.vofasbackendv1.data_layer.entities.StaticQREntity;
import org.example.vofasbackendv1.data_layer.enums.FeedbackSourceStateEnum;
import org.example.vofasbackendv1.data_layer.enums.SourceTypeEnum;
import org.example.vofasbackendv1.data_layer.repositories.StaticQRRepository;
import org.example.vofasbackendv1.exceptions.InvalidSourceException;
import org.example.vofasbackendv1.exceptions.NoContentException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.StaticQRDTO;
import org.example.vofasbackendv1.presentationlayer.mappers.StaticQRMapper;
import org.example.vofasbackendv1.servicelayer.interfaces.StaticQRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class StaticQRServiceImpl implements StaticQRService {
    private final StaticQRRepository staticQRRepository;

    @Value("${vofas.page.size}")
    private int pageSize;

    @Autowired
    public StaticQRServiceImpl(StaticQRRepository staticQRRepository) {
        this.staticQRRepository = staticQRRepository;
    }

    @Override
    public Page<StaticQRDTO> getAllStaticQRs(String state, String sortBy, boolean ascending, int pageNo) throws NoContentException {
        Sort sort = Sort.by(ascending ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<StaticQREntity> staticQRPage;
        staticQRPage = staticQRRepository.findByState(FeedbackSourceStateEnum.valueOf(state.toUpperCase()), pageable);
        if (staticQRPage.isEmpty()) {
            throw new NoContentException(SourceConstants.STATIC_QR, String.valueOf(pageNo));
        }
        return staticQRPage.map(entity -> StaticQRMapper.entityToDTO(entity, new StaticQRDTO()));
    }

    @Override
    public StaticQRDTO getStaticQRByFeedbackSourceID(Long feedbackSourceID) throws ResourceNotFoundException {
        Optional<StaticQREntity> optionalEntity = staticQRRepository.findByFeedbackSourceId(feedbackSourceID);
        if (optionalEntity.isEmpty()) {
            throw new ResourceNotFoundException(SourceTypeEnum.STATIC_QR.toString(), "feedbackSourceID", String.valueOf(feedbackSourceID));
        }
        return StaticQRMapper.entityToDTO(optionalEntity.get(), new StaticQRDTO());
    }

    @Override
    public Boolean createStaticQR(StaticQRDTO staticQRDTO) throws InvalidSourceException {
        StaticQREntity staticQREntity = StaticQRMapper.dtoToEntity(staticQRDTO, new StaticQREntity());
        staticQREntity.setCreatedAt(LocalDateTime.now());
        staticQREntity.setQrID(UUID.randomUUID());
        if(staticQREntity.getState() == null) {
            staticQREntity.setState(FeedbackSourceStateEnum.ACTIVE);
        }
        staticQRRepository.save(staticQREntity);
        return true;
    }

    @Override
    public StaticQRDTO updateStaticQRByFeedbackSourceID(Long feedbackSourceID, StaticQRDTO staticQRDTO) throws ResourceNotFoundException, InvalidSourceException {
        Optional<StaticQREntity> optionalEntity = staticQRRepository.findByFeedbackSourceId(feedbackSourceID);
        if (!optionalEntity.isPresent()) {
            throw new ResourceNotFoundException(SourceTypeEnum.STATIC_QR.toString(), "feedbackSourceID", String.valueOf(feedbackSourceID));
        }
        StaticQREntity existingEntity = getStaticQREntity(staticQRDTO, optionalEntity);


        existingEntity.setSourceName(staticQRDTO.getSourceName());
        existingEntity.setDescription(staticQRDTO.getDescription());
        existingEntity.setState(FeedbackSourceStateEnum.valueOf(staticQRDTO.getState().toUpperCase()));
        existingEntity.setInformativeText(staticQRDTO.getInformativeText());
        staticQRRepository.save(existingEntity);

        return StaticQRMapper.entityToDTO(existingEntity, new StaticQRDTO());
    }

    private static StaticQREntity getStaticQREntity(StaticQRDTO staticQRDTO, Optional<StaticQREntity> optionalEntity) {
        StaticQREntity existingEntity = optionalEntity.get();

        if (!existingEntity.getQrID().equals(staticQRDTO.getQrID())) {
            throw new InvalidSourceException(SourceConstants.STATIC_QR,"Cannot modify qrID field.");
        }
        if (!existingEntity.getLocation().equals(staticQRDTO.getLocation())) {
            throw new InvalidSourceException(SourceConstants.STATIC_QR,"Cannot modify location field.");
        }
        if (!existingEntity.getFeedbackSourceId().equals(staticQRDTO.getFeedbackSourceId())) {
            throw new InvalidSourceException(SourceConstants.STATIC_QR,"Cannot modify feedbackSourceId field.");
        }
        if (!(existingEntity.getSourceType().equals(SourceTypeEnum.valueOf(staticQRDTO.getSourceType().toUpperCase())))) {
            throw new InvalidSourceException(SourceConstants.STATIC_QR,"Cannot modify sourceType field.");
        }
        if (!existingEntity.getCreatedAt().equals(staticQRDTO.getCreatedAt())) {
            throw new InvalidSourceException(SourceConstants.STATIC_QR,"Cannot modify createdAt field.");
        }
        return existingEntity;
    }
}
