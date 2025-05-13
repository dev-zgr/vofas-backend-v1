package org.example.vofasbackendv1.servicelayer.implementations;

import jakarta.annotation.PostConstruct;
import org.example.vofasbackendv1.constants.SourceConstants;
import org.example.vofasbackendv1.constants.UserConstants;
import org.example.vofasbackendv1.data_layer.entities.UserEntity;
import org.example.vofasbackendv1.data_layer.enums.RoleEnum;
import org.example.vofasbackendv1.data_layer.repositories.UserRepository;
import org.example.vofasbackendv1.exceptions.InvalidParametersException;
import org.example.vofasbackendv1.exceptions.InvalidSourceException;
import org.example.vofasbackendv1.exceptions.NoContentException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.UserDTO;
import org.example.vofasbackendv1.presentationlayer.mappers.UserMapper;
import org.example.vofasbackendv1.servicelayer.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Value("${vofas.page.size}")
    private int pageSize;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void initializeUserAccount() {
        List<UserEntity> adminUsers = userRepository.findUserEntitiesByRoleEnum(RoleEnum.ADMIN);
        if (!adminUsers.isEmpty()) {
            UserEntity defaultAdminUser = new UserEntity();
            defaultAdminUser.setFirstName("ADMIN");
            defaultAdminUser.setLastName("ADMIN");
            defaultAdminUser.setEmail("admin@admin.com");
            defaultAdminUser.setPassword("admin123");
            defaultAdminUser.setAddressFirstLine("1st admin street.");
            defaultAdminUser.setAddressSecondLine("2nd Lane");
            defaultAdminUser.setDistrict("Sample District");
            defaultAdminUser.setCity("Sample City");
            defaultAdminUser.setPostalCode("00000");
            defaultAdminUser.setCountry("Turkey");
            defaultAdminUser.setRoleEnum(RoleEnum.ADMIN);
            userRepository.save(defaultAdminUser);
        }
    }

    @Override
    public Page<UserDTO> getAllUser(String sortBy, boolean ascending, int pageNo) throws NoContentException, InvalidParametersException {
        Sort sort = Sort.by(ascending ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<UserEntity> userPage = userRepository.findAll(pageable);
        if (userPage.isEmpty()) {
            throw new NoContentException(SourceConstants.User, String.valueOf(pageNo));
        }
        return userPage.map(userEntity -> UserMapper.entityToDTO(userEntity, new UserDTO()));
    }

    @Override
    public UserDTO getUserByUserID(Long userID) throws InvalidParametersException, ResourceNotFoundException {
        Optional<UserEntity> optionalEntity = userRepository.findById(userID);
        if (optionalEntity.isEmpty()) {
            throw new ResourceNotFoundException(SourceConstants.User, "userID", String.valueOf(userID));
        }
        return UserMapper.entityToDTO(optionalEntity.get(), new UserDTO());
    }

    @Override
    public Boolean createUser(UserDTO userDTO) throws InvalidSourceException {

        if (userRepository.findUserEntitiesByEmail(userDTO.getEmail()).isPresent()) {
            throw new InvalidSourceException(SourceConstants.User, UserConstants.USER_ALREADY_EXIST);
        }
        if (userDTO.getRoleEnum().equals("ADMIN")) {
            List<UserEntity> adminList = userRepository.findUserEntitiesByRoleEnum(RoleEnum.ADMIN);
            if (!adminList.isEmpty()) {
                throw new InvalidSourceException(SourceConstants.User, UserConstants.ADMIN_ALREADY_EXIST);
            }
        }

        UserEntity userEntity = UserMapper.dtoToEntity(userDTO, new UserEntity());
        userRepository.save(userEntity);
        return true;
    }


    @Override
    public Boolean deleteUserByUserID(Long userID) throws InvalidParametersException, ResourceNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findById(userID);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException(SourceConstants.User, "userID", String.valueOf(userID));
        }
        userRepository.deleteById(userID);
        return true;
    }

    @Override
    public UserDTO updateUserByUserID(Long userID, UserDTO userDTO) throws InvalidParametersException, ResourceNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findById(userID);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException(SourceConstants.User, "userID", String.valueOf(userID));
        }
        if (!userDTO.getRoleEnum().equals(optionalUser.get().getRoleEnum().toString())) {
            throw new InvalidSourceException(SourceConstants.User, UserConstants.USER_ROLE_CANNOT_BE_CHANGED);
        }

        var existingOtherUser = userRepository.findUserEntitiesByEmail(userDTO.getEmail());
        if (existingOtherUser.isPresent() && !existingOtherUser.get().getUserID().equals(userID)) {
            throw new InvalidSourceException(SourceConstants.User, UserConstants.USER_ALREADY_EXIST);
        }
        UserEntity existingUser = UserMapper.dtoToEntity(userDTO, optionalUser.get());
        UserEntity updatedUser = userRepository.save(existingUser);
        return UserMapper.entityToDTO(updatedUser, new UserDTO());
    }


}
