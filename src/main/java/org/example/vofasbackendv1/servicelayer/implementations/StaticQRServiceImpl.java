package org.example.vofasbackendv1.servicelayer.implementations;

import org.example.vofasbackendv1.data_layer.repositories.StaticQRRepository;
import org.example.vofasbackendv1.exceptions.NoContentException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.StaticQRDTO;
import org.example.vofasbackendv1.servicelayer.interfaces.StaticQRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
public class StaticQRServiceImpl implements StaticQRService {
    private StaticQRRepository staticQRRepository;

    @Autowired
    public StaticQRServiceImpl(StaticQRRepository staticQRRepository){
        this.staticQRRepository = staticQRRepository;
    }

    @Override
    public Page<StaticQRDTO> getAllStaticQRs(String status, String sortBy, boolean ascending, int pageNo) throws NoContentException, InvalidParameterException {
        // TODO: Validate 'status'. Allowed values are 'active', 'passive'.
        // TODO: Throw InvalidParametersException if validation fails.
        // TODO: Implement pagination and sorting based on 'sortBy' and 'ascending'.Only 'createdAt', 'feedbackSourceID', 'sourceName'  and 'location' are allowed as sortBy fields.
        // TODO: Fetch StaticQR entities based on status and pagination.
        // TODO: If no StaticQRs are found, throw NoContentException with a descriptive message.
        // TODO: Map StaticQREntities to StaticQRDTOs and return a Page<StaticQRDTO>.
        return null;
    }

    @Override
    public StaticQRDTO getStaticQRByFeedbackSourceID(Long feedbackSourceID) throws ResourceNotFoundException, InvalidParameterException {
        // TODO: Validate 'feedbackSourceID'. Ensure it's not null or less than 1.
        // TODO: Fetch StaticQR by feedbackSourceID from the repository.
        // TODO: If not found, throw ResourceNotFoundException with a descriptive message.
        // TODO: Map StaticQREntity to StaticQRDTO and return it.
        return null;
    }

    @Override
    public Boolean createStaticQR(StaticQRDTO staticQRDTO) throws InvalidParameterException {
        // TODO: Validate StaticQRDTO fields, especially for required fields.
        // TODO: Throw InvalidParametersException if any required field is invalid (e.g., null or empty).
        // TODO: Map StaticQRDTO to StaticQREntity.
        // TODO: Save StaticQREntity to the database.
        // TODO: Return Boolean indicating success (true if created successfully, false otherwise).
        return null;
    }

    @Override
    public Boolean updateStaticQRByFeedbackSourceID(Long feedbackSourceID, StaticQRDTO staticQRDTO) throws ResourceNotFoundException, InvalidParameterException {
        // TODO: Validate 'feedbackSourceID'. Ensure it's valid.
        // TODO: Fetch the existing StaticQR entity by 'feedbackSourceID'.
        // TODO: If not found, throw ResourceNotFoundException with a descriptive message.
        // TODO: Ensure only 'sourceName', 'description', 'state', and 'informativeText' are updated.
        // TODO: Throw InvalidParametersException if any other fields are modified.
        // TODO: Update the StaticQR entity and save to the database.
        // TODO: Return Boolean indicating success (true if updated successfully, false otherwise).
        return null;
    }
}
