package org.example.vofasbackendv1.servicelayer.implementations;

import org.example.vofasbackendv1.data_layer.repositories.UserRepository;
import org.example.vofasbackendv1.presentationlayer.dto.ResponseDTO;
import org.example.vofasbackendv1.presentationlayer.dto.UserDTO;
import org.example.vofasbackendv1.servicelayer.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Page<UserDTO> getAllUser(String sortBy, boolean ascending, int pageNo) {
        // TODO: Validate parameters.
        // TODO: Throw InvalidParametersException if any validation fails.
        // TODO: Query user repository with sorting and pagination.
        // TODO: Throw NoContentException if no users found.
        // TODO: Map result to Page<UserDTO> and return.
        return null;
    }

    @Override
    public UserDTO getUserByUserID(Long userID) {
        // TODO: Validate userID.
        // TODO: Throw InvalidParametersException if invalid.
        // TODO: Retrieve user by ID.
        // TODO: Throw ResourceNotFoundException if not found.
        // TODO: Map to UserDTO and return.
        return null;
    }

    @Override
    public Boolean createUser(UserDTO userDTO) {
        // TODO: Validate userDTO fields.
        // TODO: Throw InvalidParametersException on invalid input. ie in case of trying to persist user with existing email pass this situation as a exception message
        // TODO: Convert to UserEntity and save to DB.
        // TODO: Return ResponseDTO with creation status.
        return null;
    }

    @Override
    public Boolean deleteUserByUserID(Long userID) {
        // TODO: Validate userID.
        // TODO: Throw InvalidParametersException if invalid.
        // TODO: Check if user exists.
        // TODO: Throw ResourceNotFoundException if not found.
        // TODO: Delete user and return ResponseDTO.
        return null;
    }

    @Override
    public UserDTO updateUserByUserID(Long userID, UserDTO userDTO) {
        // TODO: Validate userID and userDTO.
        // TODO: Throw InvalidParametersException on invalid input.
        // TODO: Retrieve and update existing user.
        // TODO: Throw ResourceNotFoundException if not found.
        // TODO: Save and return updated UserDTO.
        return null;
    }
}
