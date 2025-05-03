package org.example.vofasbackendv1.servicelayer.interfaces;

import org.example.vofasbackendv1.exceptions.InvalidSourceException;
import org.example.vofasbackendv1.exceptions.NoContentException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.UserDTO;
import org.springframework.data.domain.Page;

import java.security.InvalidParameterException;

public interface UserService {

    Page<UserDTO> getAllUser(String sortBy, boolean ascending, int pageNo) throws NoContentException;

    UserDTO getUserByUserID(Long userID) throws ResourceNotFoundException;

    Boolean createUser(UserDTO userDTO) throws InvalidSourceException;

    Boolean deleteUserByUserID(Long userID) throws  ResourceNotFoundException;

    UserDTO updateUserByUserID(Long userID, UserDTO userDTO) throws InvalidSourceException, ResourceNotFoundException;
}
