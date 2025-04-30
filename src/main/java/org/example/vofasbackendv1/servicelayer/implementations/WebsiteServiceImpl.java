package org.example.vofasbackendv1.servicelayer.implementations;

import org.example.vofasbackendv1.data_layer.repositories.WebsiteRepository;
import org.example.vofasbackendv1.presentationlayer.dto.WebsiteDTO;
import org.example.vofasbackendv1.servicelayer.interfaces.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebsiteServiceImpl implements WebsiteService {

    private WebsiteRepository websiteRepository;

    @Autowired
    public WebsiteServiceImpl(WebsiteRepository websiteRepository) {
        this.websiteRepository = websiteRepository;
    }
    @Override
    public boolean createWebsite(WebsiteDTO websiteDTO) {
        return false;
    }

    @Override
    public WebsiteDTO getWebsite() {
        return null;
    }

    @Override
    public boolean updateWebsite(Long websiteID, WebsiteDTO websiteDTO) {
        return false;
    }
}
