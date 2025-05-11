package org.example.vofasbackendv1.servicelayer.interfaces;

import org.example.vofasbackendv1.exceptions.NoContentException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.StaticQRDTO;
import org.springframework.data.domain.Page;

import java.security.InvalidParameterException;

public interface StaticQRService {

    Page<StaticQRDTO> getAllStaticQRs(String state, String sortBy, boolean ascending, int pageNo) throws NoContentException;

    StaticQRDTO getStaticQRByFeedbackSourceID(Long feedbackSourceID) throws ResourceNotFoundException;

    Boolean createStaticQR(StaticQRDTO staticQRDTO) throws InvalidParameterException;

    StaticQRDTO updateStaticQRByFeedbackSourceID(Long feedbackSourceID, StaticQRDTO staticQRDTO) throws ResourceNotFoundException;

}