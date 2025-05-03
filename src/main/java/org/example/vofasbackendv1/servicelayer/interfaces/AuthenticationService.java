package org.example.vofasbackendv1.servicelayer.interfaces;

import org.example.vofasbackendv1.presentationlayer.dto.AuthenticationDTO;
import org.example.vofasbackendv1.presentationlayer.dto.AuthenticationRequestDTO;
import org.springframework.security.authentication.BadCredentialsException;

public interface AuthenticationService {
    AuthenticationDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO) throws BadCredentialsException;
}
