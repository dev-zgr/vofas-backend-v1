package org.example.vofasbackendv1.servicelayer.implementations;

import org.example.vofasbackendv1.components.JwtTokenProvider;
import org.example.vofasbackendv1.constants.SourceConstants;
import org.example.vofasbackendv1.constants.UserConstants;
import org.example.vofasbackendv1.data_layer.entities.UserEntity;
import org.example.vofasbackendv1.data_layer.repositories.UserRepository;
import org.example.vofasbackendv1.exceptions.InvalidSourceException;
import org.example.vofasbackendv1.exceptions.InvalidTokenException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.AuthenticationDTO;
import org.example.vofasbackendv1.presentationlayer.dto.AuthenticationRequestDTO;
import org.example.vofasbackendv1.presentationlayer.dto.UserDTO;
import org.example.vofasbackendv1.presentationlayer.mappers.UserMapper;
import org.example.vofasbackendv1.servicelayer.interfaces.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, AuthenticationManager authManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.authManager = authManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthenticationDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO) {
        String email = authenticationRequestDTO.getEmail();
        String password = authenticationRequestDTO.getPassword();
        Optional<UserEntity> userEntityOptional = userRepository.findUserEntitiesByEmail(email);
        if (userEntityOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found", "userID", email);
        }
        UserEntity user = userEntityOptional.get();
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserID(), password)
        );
        String token = jwtTokenProvider.generateToken(user.getUserID(), user.getRoleEnum().name());
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(1);
        return new AuthenticationDTO(
                LocalDateTime.now(),
                token,
                expiresAt,
                user.getRoleEnum().name()
        );
    }

    @Override
    public UserDTO getUserByUserToken(String userToken) throws ResourceNotFoundException, InvalidTokenException {
        if (userToken == null || !userToken.startsWith("Bearer ")) {
            throw new InvalidTokenException(userToken);
        }

        String token = userToken.substring(7);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException(userToken);
        }

        String userID = jwtTokenProvider.getUsernameFromToken(token);
        return userRepository.findById(Long.valueOf(userID))
                .map(userEntity -> UserMapper.entityToDTO(userEntity, new UserDTO()))
                .orElseThrow(() -> new ResourceNotFoundException(SourceConstants.User, "user token", token));
    }

    @Override
    public UserDTO updateUserByToken(String userToken, UserDTO userDTO) throws InvalidSourceException, ResourceNotFoundException, InvalidParameterException {
        if (userToken == null || !userToken.startsWith("Bearer ")) {
            throw new InvalidSourceException(SourceConstants.AUTHENTICATION, "Invalid authentication token");
        }
        String token = userToken.substring(7);
        String userID = jwtTokenProvider.getUsernameFromToken(token);
        Optional<UserEntity> userEntityOptional = userRepository.findById(Long.valueOf(userID));
        if (userEntityOptional.isEmpty()) {
            throw new ResourceNotFoundException(SourceConstants.User, "userID", userID);
        }
        UserEntity userEntity = userEntityOptional.get();
        if (userDTO.getUserID() != null && (userEntity.getUserID().longValue() != userDTO.getUserID().longValue())) {
            throw new InvalidSourceException(SourceConstants.User, "userID can't be changed");
        } else if (userDTO.getRoleEnum() != null && !userDTO.getRoleEnum().equals(userEntity.getRoleEnum().name())) {
            throw new InvalidSourceException(SourceConstants.User, "User role can't be changed");
        }

        if (!userDTO.getEmail().equals(userEntity.getEmail())) {
            var existingOtherUser = userRepository.findUserEntitiesByEmail(userDTO.getEmail());
            if (existingOtherUser.isPresent() && !existingOtherUser.get().getUserID().equals(userEntity.getUserID())) {
                throw new InvalidSourceException(SourceConstants.User, UserConstants.USER_ALREADY_EXIST);
            }
        }

        UserEntity updatedUserEntity = UserMapper.dtoToEntity(userDTO, userEntity);
        updatedUserEntity = userRepository.save(updatedUserEntity);
        return UserMapper.entityToDTO(updatedUserEntity, new UserDTO());
    }
}
