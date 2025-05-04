package org.example.vofasbackendv1.servicelayer.implementations;

import org.apache.coyote.BadRequestException;
import org.example.vofasbackendv1.constants.WebsiteConstants;
import org.example.vofasbackendv1.data_layer.entities.WebsiteEntity;
import org.example.vofasbackendv1.data_layer.enums.FeedbackSourceStateEnum;
import org.example.vofasbackendv1.data_layer.repositories.WebsiteRepository;
import org.example.vofasbackendv1.exceptions.InvalidParametersException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.WebsiteDTO;
import org.example.vofasbackendv1.presentationlayer.mappers.WebsiteMapper;
import org.example.vofasbackendv1.servicelayer.interfaces.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebsiteServiceImpl implements WebsiteService {

    private WebsiteRepository websiteRepository;

    @Autowired
    public WebsiteServiceImpl(WebsiteRepository websiteRepository) {
        this.websiteRepository = websiteRepository;
    }

    @Override
    public boolean createWebsite(WebsiteDTO websiteDTO) throws InvalidParametersException {
        if (websiteDTO == null) {
            throw new InvalidParametersException("Website data cannot be null.");
        }

        if (websiteDTO.getSourceName() == null || websiteDTO.getSourceName().isBlank()) {
            throw new InvalidParametersException("Source name is required and cannot be blank.");
        }

        if (websiteDTO.getSourceName().length() < 2 || websiteDTO.getSourceName().length() > 32) {
            throw new InvalidParametersException("Source name must be between 2 and 32 characters.");
        }

        if (websiteDTO.getSourceType() == null) {
            throw new InvalidParametersException("Source type is required.");
        }

        if (websiteDTO.getDescription() == null || websiteDTO.getDescription().isBlank()) {
            throw new InvalidParametersException("Description is required and cannot be blank.");
        }

        if (websiteDTO.getDescription().length() < 2 || websiteDTO.getDescription().length() > 255) {
            throw new InvalidParametersException("Description must be between 2 and 255 characters.");
        }

        if (websiteDTO.getState() == null) {
            throw new InvalidParametersException("State is required.");
        }

        if (websiteDTO.getUrl() == null || websiteDTO.getUrl().isBlank()) {
            throw new InvalidParametersException("Website URL is required and cannot be blank.");
        }

        if (websiteDTO.getUrl().length() > 255) {
            throw new InvalidParametersException("Website URL must not exceed 255 characters.");
        }

        if (websiteDTO.getInformativeText() == null || websiteDTO.getInformativeText().isBlank()) {
            throw new InvalidParametersException("Informative text is required and cannot be blank.");
        }

        if (websiteDTO.getInformativeText().length() > 255) {
            throw new InvalidParametersException("Informative text must not exceed 255 characters.");
        }

        List<WebsiteEntity> existingWebsites = websiteRepository.findAll();
        if (!existingWebsites.isEmpty()) {
            throw new InvalidParametersException(WebsiteConstants.WEBSITE_ALREADY_EXISTS);
        }

        WebsiteEntity websiteEntity = WebsiteMapper.dtoToEntity(websiteDTO);
        websiteRepository.save(websiteEntity);

        return true;
    }

    @Override
    public WebsiteDTO getWebsite() throws ResourceNotFoundException, BadRequestException {
        List<WebsiteEntity> websites = websiteRepository.findAll();

        if (websites.isEmpty()) {
            throw new ResourceNotFoundException("Website", "1", "null");
        }

        if (websites.size() > 1) {
            throw new BadRequestException(WebsiteConstants.INVALID_WEBSITE_ID);
        }

        WebsiteEntity website = websites.getFirst();
        return WebsiteMapper.entityToDTO(website);
    }

    @Override
    public WebsiteDTO updateWebsiteByID(Long websiteID, WebsiteDTO websiteDTO) throws InvalidParametersException, ResourceNotFoundException {
        if (websiteID == null || websiteID <= 0) {
            throw new InvalidParametersException(WebsiteConstants.INVALID_WEBSITE_ID);
        }

        WebsiteEntity websiteEntity = websiteRepository.findById(websiteID)
                .orElseThrow(() -> new ResourceNotFoundException("Website", "1", "null"));

        if (websiteDTO.getUrl() != null) {
            websiteEntity.setUrl(websiteDTO.getUrl());
        }
        if (websiteDTO.getInformativeText() != null) {
            websiteEntity.setInformativeText(websiteDTO.getInformativeText());
        }
        if (websiteDTO.getState() != null) {
            websiteEntity.setState(FeedbackSourceStateEnum.valueOf(websiteDTO.getState().toUpperCase()));
        }

        websiteRepository.save(websiteEntity);

        return WebsiteMapper.entityToDTO(websiteEntity);
    }
}
