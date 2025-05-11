package org.example.vofasbackendv1.servicelayer.implementations;

import org.example.vofasbackendv1.constants.SourceConstants;
import org.example.vofasbackendv1.data_layer.entities.StaticQREntity;
import org.example.vofasbackendv1.data_layer.entities.WebsiteEntity;
import org.example.vofasbackendv1.data_layer.repositories.StaticQRRepository;
import org.example.vofasbackendv1.data_layer.repositories.WebsiteRepository;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.StaticQRDTO;
import org.example.vofasbackendv1.presentationlayer.dto.WebsiteDTO;
import org.example.vofasbackendv1.servicelayer.interfaces.SourceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SourceStatusServiceImpl implements SourceStatusService {

    private final WebsiteRepository websiteRepository;
    private final StaticQRRepository staticQRRepository;

    @Autowired
    public SourceStatusServiceImpl(WebsiteRepository websiteRepository, StaticQRRepository staticQRRepository) {
        this.websiteRepository = websiteRepository;
        this.staticQRRepository = staticQRRepository;
    }

    @Override
    public WebsiteDTO getWebsiteStatus() throws ResourceNotFoundException{
        WebsiteEntity website = websiteRepository.findAll().isEmpty() ? null : websiteRepository.findAll().getFirst();
        if (website == null) {
            throw new ResourceNotFoundException(SourceConstants.WEBSITE, "", "");
        }
        WebsiteDTO websiteDTO = new WebsiteDTO();
        websiteDTO.setFeedbackSourceId(website.getFeedbackSourceId());
        websiteDTO.setSourceName(website.getSourceName());
        websiteDTO.setSourceType(website.getSourceType().toString());
        websiteDTO.setDescription(website.getDescription());
        websiteDTO.setState(website.getState().toString());
        websiteDTO.setUrl(website.getUrl());
        websiteDTO.setInformativeText(website.getInformativeText());
        return websiteDTO;
    }

    @Override
    public StaticQRDTO getStaticQRStatusByQRID(UUID staticQRID) {
        StaticQREntity staticQR = staticQRRepository.findByQrID(staticQRID)
                .orElseThrow(() -> new ResourceNotFoundException("Static QR", "QR ID", staticQRID.toString()));

        StaticQRDTO staticQRDTO = new StaticQRDTO();
        staticQRDTO.setFeedbackSourceId(staticQR.getFeedbackSourceId());
        staticQRDTO.setSourceName(staticQR.getSourceName());
        staticQRDTO.setSourceType(staticQR.getSourceType().toString());
        staticQRDTO.setDescription(staticQR.getDescription());
        staticQRDTO.setState(staticQR.getState().toString());
        staticQRDTO.setQrID(staticQR.getQrID());
        staticQRDTO.setLocation(staticQR.getLocation());
        staticQRDTO.setInformativeText(staticQR.getInformativeText());

        return staticQRDTO;
    }
}
