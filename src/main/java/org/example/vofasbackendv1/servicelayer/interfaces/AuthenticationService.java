package org.example.vofasbackendv1.servicelayer.interfaces;

import org.example.vofasbackendv1.exceptions.InvalidSourceException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.AuthenticationDTO;
import org.example.vofasbackendv1.presentationlayer.dto.AuthenticationRequestDTO;
import org.example.vofasbackendv1.presentationlayer.dto.UserDTO;
import org.springframework.security.authentication.BadCredentialsException;

public interface AuthenticationService {
    AuthenticationDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO) throws BadCredentialsException;
    UserDTO getUserByUserToken(String userToken) throws ResourceNotFoundException;
    UserDTO updateUserByToken(String userToken, UserDTO userDTO) throws InvalidSourceException, ResourceNotFoundException;
}
