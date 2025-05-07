package org.example.vofasbackendv1.servicelayer.implementations;

import org.example.vofasbackendv1.data_layer.repositories.StaticQRRepository;
import org.example.vofasbackendv1.data_layer.repositories.WebsiteRepository;
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
    public WebsiteDTO getWebsiteStatus() {
        return null;
    }

    @Override
    public StaticQRDTO getStaticQRStatusByQRID(UUID staticQRID) {
        return null;
    }
}
