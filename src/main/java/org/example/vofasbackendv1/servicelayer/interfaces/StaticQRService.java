package org.example.vofasbackendv1.servicelayer.interfaces;

import org.example.vofasbackendv1.exceptions.NoContentException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.StaticQRDTO;
import org.springframework.data.domain.Page;

import java.security.InvalidParameterException;

public interface StaticQRService {

    Page<StaticQRDTO> getAllStaticQRs(String status, String sortBy, boolean ascending, int pageNo) throws NoContentException, InvalidParameterException;

    StaticQRDTO getStaticQRByFeedbackSourceID(Long feedbackSourceID) throws ResourceNotFoundException, InvalidParameterException;

    Boolean createStaticQR(StaticQRDTO staticQRDTO) throws InvalidParameterException;

    Boolean updateStaticQRByFeedbackSourceID(Long feedbackSourceID, StaticQRDTO staticQRDTO) throws ResourceNotFoundException, InvalidParameterException;

}