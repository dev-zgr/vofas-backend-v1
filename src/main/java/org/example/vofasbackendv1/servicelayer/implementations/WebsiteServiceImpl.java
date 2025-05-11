package org.example.vofasbackendv1.servicelayer.implementations;

import org.example.vofasbackendv1.constants.FeedbackConstants;
import org.example.vofasbackendv1.constants.SourceConstants;
import org.example.vofasbackendv1.data_layer.entities.WebsiteEntity;
import org.example.vofasbackendv1.data_layer.enums.FeedbackSourceStateEnum;
import org.example.vofasbackendv1.data_layer.enums.SourceTypeEnum;
import org.example.vofasbackendv1.data_layer.repositories.WebsiteRepository;
import org.example.vofasbackendv1.exceptions.InvalidSourceException;
import org.example.vofasbackendv1.exceptions.ResourceAlreadyExistException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.WebsiteDTO;
import org.example.vofasbackendv1.presentationlayer.mappers.WebsiteMapper;
import org.example.vofasbackendv1.servicelayer.interfaces.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebsiteServiceImpl implements WebsiteService {

    private final WebsiteRepository websiteRepository;

    @Autowired
    public WebsiteServiceImpl(WebsiteRepository websiteRepository) {
        this.websiteRepository = websiteRepository;
    }

    @Override
    public WebsiteDTO createWebsite(WebsiteDTO websiteDTO) throws ResourceAlreadyExistException {
        List<WebsiteEntity> websites = websiteRepository.findAll();
        if (!websites.isEmpty()) {
            throw new ResourceAlreadyExistException("Website", "sourceID", websites.getFirst().getFeedbackSourceId().toString());
        }
        WebsiteEntity websiteEntity = new WebsiteEntity();
        websiteEntity = WebsiteMapper.dtoToEntity(websiteDTO,websiteEntity);
        WebsiteEntity savedWebsite = websiteRepository.save(websiteEntity);
        return WebsiteMapper.entityToDTO(savedWebsite,new WebsiteDTO());
    }

    @Override
    public WebsiteDTO getWebsite() throws ResourceNotFoundException {
        List<WebsiteEntity> websites = websiteRepository.findAll();
        if (websites.isEmpty()) {
            throw new ResourceNotFoundException(SourceConstants.WEBSITE, "sourceID", "No website found");
        }
        WebsiteEntity website = websites.getFirst();
        return WebsiteMapper.entityToDTO(website, new WebsiteDTO());
    }

    @Override
    public WebsiteDTO updateWebsiteByID(Long websiteID, WebsiteDTO websiteDTO) throws ResourceNotFoundException, InvalidSourceException {
        WebsiteEntity websiteEntity = websiteRepository.findById(websiteID)
                .orElseThrow(() -> new ResourceNotFoundException("Website", "feedbackSourceID", websiteID.toString()));

        if (SourceTypeEnum.valueOf(websiteDTO.getSourceType()) != websiteEntity.getSourceType() ||
                (websiteDTO.getCreatedAt() != null && !websiteEntity.getCreatedAt().isEqual(websiteDTO.getCreatedAt()))||
                !websiteEntity.getUrl().equals(websiteDTO.getUrl())
        ){
            throw new InvalidSourceException(SourceConstants.WEBSITE, FeedbackConstants.ATTEMPTED_TO_UPDATE_STATIC_FIELDS);
        }

        websiteEntity.setSourceName(websiteDTO.getSourceName());
        websiteEntity.setDescription(websiteDTO.getDescription());
        websiteEntity.setState(FeedbackSourceStateEnum.valueOf(websiteDTO.getState()));
        websiteEntity.setInformativeText(websiteDTO.getInformativeText());
        websiteEntity = websiteRepository.save(websiteEntity);

        return WebsiteMapper.entityToDTO(websiteEntity, new WebsiteDTO());
    }
}
