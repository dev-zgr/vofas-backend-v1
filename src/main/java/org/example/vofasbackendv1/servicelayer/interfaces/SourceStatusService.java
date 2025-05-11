package org.example.vofasbackendv1.servicelayer.interfaces;

import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.StaticQRDTO;
import org.example.vofasbackendv1.presentationlayer.dto.WebsiteDTO;

import java.util.UUID;

public interface SourceStatusService {
    WebsiteDTO getWebsiteStatus() throws ResourceNotFoundException;
    StaticQRDTO getStaticQRStatusByQRID(UUID staticQRID) throws ResourceNotFoundException;
}
