package org.example.vofasbackendv1.servicelayer.implementations;

import org.example.vofasbackendv1.constants.StaticQRConstants;
import org.example.vofasbackendv1.data_layer.entities.StaticQREntity;
import org.example.vofasbackendv1.data_layer.enums.FeedbackSourceStateEnum;
import org.example.vofasbackendv1.data_layer.enums.SourceTypeEnum;
import org.example.vofasbackendv1.data_layer.repositories.StaticQRRepository;
import org.example.vofasbackendv1.exceptions.InvalidParametersException;
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

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class StaticQRServiceImpl implements StaticQRService {
    private StaticQRRepository staticQRRepository;

    @Value("${vofas.page.size}")
    private int pageSize;

    @Autowired
    public StaticQRServiceImpl(StaticQRRepository staticQRRepository){
        this.staticQRRepository = staticQRRepository;
    }

    @Override
    public Page<StaticQRDTO> getAllStaticQRs(FeedbackSourceStateEnum  state, String sortBy, boolean ascending, int pageNo) throws NoContentException, InvalidParametersException {
        if (!state.equals(FeedbackSourceStateEnum.ACTIVE) && !state.equals(FeedbackSourceStateEnum.PASSIVE)) {
            throw new InvalidParametersException(StaticQRConstants.INVALID_STATE_PARAMETER);
        }
        List<String> sortByList = List.of("qrID", "location", "createdAt", "sourceName");
        if (!sortByList.contains(sortBy)) {
            throw new InvalidParametersException(StaticQRConstants.INVALID_SORT_PARAMETER);
        }
        if (!"true".equalsIgnoreCase(String.valueOf(ascending)) && !"false".equalsIgnoreCase(String.valueOf(ascending))) {
            throw new InvalidParametersException(StaticQRConstants.INVALID_ASCENDING_PARAMETER);
        }
        if (pageNo < 0) {
            throw new InvalidParametersException(StaticQRConstants.INVALID_PAGINATION_PARAMETER);
        }

        Sort sort = Sort.by(ascending ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<StaticQREntity> staticQRPage;
        staticQRPage = staticQRRepository.findByState(state, pageable);

        if (staticQRPage.isEmpty()) {
            throw new NoContentException(SourceTypeEnum.STATIC_QR.toString(), String.valueOf(pageNo));
        }

        return staticQRPage.map(StaticQRMapper::entityToDTO);
    }

    @Override
    public StaticQRDTO getStaticQRByFeedbackSourceID(Long feedbackSourceID) throws ResourceNotFoundException, InvalidParametersException {
        if (feedbackSourceID == null || feedbackSourceID < 1) {
            throw new InvalidParametersException(StaticQRConstants.INVALID_STATICQR_ID);
        }

        Optional<StaticQREntity> optionalEntity = staticQRRepository.findByFeedbackSourceId(feedbackSourceID);
        if (optionalEntity.isEmpty()) {
            throw new ResourceNotFoundException(SourceTypeEnum.STATIC_QR.toString(), "feedbackSourceID", String.valueOf(feedbackSourceID));
        }

        return StaticQRMapper.entityToDTO(optionalEntity.get());
    }

    @Override
    public StaticQRDTO createStaticQR(StaticQRDTO staticQRDTO) throws InvalidParametersException {
        if (staticQRDTO.getQrID() == null) {
            throw new InvalidParametersException("QR ID cannot be null.");
        }
        if (staticQRDTO.getLocation() == null || staticQRDTO.getLocation().trim().isEmpty()) {
            throw new InvalidParametersException("Location cannot be null or blank.");
        }
        if (staticQRDTO.getInformativeText() == null || staticQRDTO.getInformativeText().trim().isEmpty()) {
            throw new InvalidParametersException("Informative text cannot be null or blank.");
        }
        if (staticQRDTO.getSourceName() == null || staticQRDTO.getSourceName().trim().isEmpty()) {
            throw new InvalidParametersException("Source name cannot be null or blank.");
        }
        if (staticQRDTO.getDescription() == null || staticQRDTO.getDescription().trim().isEmpty()) {
            throw new InvalidParametersException("Description cannot be null or blank.");
        }
        if (staticQRDTO.getState() == null) {
            throw new InvalidParametersException("State cannot be null.");
        }
        if (staticQRDTO.getCreatedAt() == null) {
            throw new InvalidParametersException("Creation date cannot be null.");
        }

        StaticQREntity staticQREntity = StaticQRMapper.dtoToEntity(staticQRDTO);

        try {
            StaticQREntity savedEntity = staticQRRepository.save(staticQREntity);
            return StaticQRMapper.entityToDTO(savedEntity);
        } catch (Exception e) {
            throw new InvalidParameterException("Failed to save StaticQR to database: " + e.getMessage());
        }
    }

    @Override
    public Boolean updateStaticQRByFeedbackSourceID(Long feedbackSourceID, StaticQRDTO staticQRDTO) throws ResourceNotFoundException, InvalidParametersException {
        if (feedbackSourceID == null || feedbackSourceID < 1) {
            throw new InvalidParametersException(StaticQRConstants.INVALID_STATICQR_ID);
        }

        Optional<StaticQREntity> optionalEntity = staticQRRepository.findByFeedbackSourceId(feedbackSourceID);

        if (optionalEntity.isEmpty()) {
            throw new ResourceNotFoundException(SourceTypeEnum.STATIC_QR.toString(), "feedbackSourceID", String.valueOf(feedbackSourceID));
        }

        StaticQREntity existingEntity = optionalEntity.get();

        if (!existingEntity.getQrID().equals(staticQRDTO.getQrID())) {
            throw new InvalidParametersException("Cannot modify qrID field.");
        }
        if (!existingEntity.getLocation().equals(staticQRDTO.getLocation())) {
            throw new InvalidParametersException("Cannot modify location field.");
        }
        if (!existingEntity.getFeedbackSourceId().equals(staticQRDTO.getFeedbackSourceId())) {
            throw new InvalidParametersException("Cannot modify feedbackSourceId field.");
        }
        if (!existingEntity.getSourceType().equals(staticQRDTO.getSourceType())) {
            throw new InvalidParametersException("Cannot modify sourceType field.");
        }
        if (!existingEntity.getCreatedAt().equals(staticQRDTO.getCreatedAt())) {
            throw new InvalidParametersException("Cannot modify createdAt field.");
        }

        existingEntity.setSourceName(staticQRDTO.getSourceName());
        existingEntity.setDescription(staticQRDTO.getDescription());
        existingEntity.setState(staticQRDTO.getState());
        existingEntity.setInformativeText(staticQRDTO.getInformativeText());

        staticQRRepository.save(existingEntity);

        return true;
    }
}
