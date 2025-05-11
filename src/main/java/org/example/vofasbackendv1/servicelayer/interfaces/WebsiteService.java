package org.example.vofasbackendv1.servicelayer.interfaces;

import org.example.vofasbackendv1.exceptions.InvalidSourceException;
import org.example.vofasbackendv1.exceptions.ResourceAlreadyExistException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.WebsiteDTO;

public interface WebsiteService {

    WebsiteDTO createWebsite(WebsiteDTO websiteDTO) throws ResourceAlreadyExistException;

    WebsiteDTO getWebsite() throws ResourceNotFoundException;

    WebsiteDTO updateWebsiteByID(Long websiteID, WebsiteDTO websiteDTO) throws InvalidSourceException, ResourceNotFoundException;

}
