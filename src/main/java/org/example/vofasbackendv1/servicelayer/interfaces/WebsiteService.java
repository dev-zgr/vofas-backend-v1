package org.example.vofasbackendv1.servicelayer.interfaces;

import org.example.vofasbackendv1.presentationlayer.dto.WebsiteDTO;
import org.example.vofasbackendv1.data_layer.entities.WebsiteEntity;

public interface WebsiteService {

    boolean createWebsite(WebsiteDTO websiteDTO);

    WebsiteDTO getWebsite();

    boolean updateWebsite(Long websiteID, WebsiteDTO websiteDTO);


}
