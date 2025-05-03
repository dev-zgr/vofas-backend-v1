package org.example.vofasbackendv1.servicelayer.interfaces;

import org.apache.coyote.BadRequestException;
import org.example.vofasbackendv1.presentationlayer.dto.WebsiteDTO;
import org.example.vofasbackendv1.data_layer.entities.WebsiteEntity;

public interface WebsiteService {

    boolean createWebsite(WebsiteDTO websiteDTO);

    WebsiteDTO getWebsite() throws BadRequestException;

    WebsiteDTO updateWebsiteByID(Long websiteID, WebsiteDTO websiteDTO);

}
