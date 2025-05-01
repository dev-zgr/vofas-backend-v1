package org.example.vofasbackendv1.servicelayer.implementations;

import org.example.vofasbackendv1.constants.UserConstants;
import org.example.vofasbackendv1.data_layer.entities.UserEntity;
import org.example.vofasbackendv1.data_layer.enums.RoleEnum;
import org.example.vofasbackendv1.data_layer.repositories.UserRepository;
import org.example.vofasbackendv1.exceptions.InvalidParametersException;
import org.example.vofasbackendv1.exceptions.NoContentException;
import org.example.vofasbackendv1.exceptions.ResourceNotFoundException;
import org.example.vofasbackendv1.presentationlayer.dto.UserDTO;
import org.example.vofasbackendv1.presentationlayer.mappers.UserMapper;
import org.example.vofasbackendv1.servicelayer.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Value("${vofas.page.size}")
    private int pageSize;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Page<UserDTO> getAllUser(String sortBy, boolean ascending, int pageNo) throws NoContentException, InvalidParametersException {
        List<String> sortByList = List.of("userID", "firstName", "lastName", "email");
        if (!sortByList.contains(sortBy)) {
            throw new InvalidParametersException(UserConstants.INVALID_SORT_PARAMETER);
        }
        if (!"true".equalsIgnoreCase(String.valueOf(ascending)) && !"false".equalsIgnoreCase(String.valueOf(ascending))) {
            throw new InvalidParametersException(UserConstants.INVALID_ASCENDING_PARAMETER);
        }
        if (pageNo < 0) {
            throw new InvalidParametersException(UserConstants.INVALID_PAGINATION_PARAMETER);
        }

        Sort sort = Sort.by(ascending ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<UserEntity> userPage = userRepository.findAll(pageable);

        if (userPage.isEmpty()) {
            throw new NoContentException("User", String.valueOf(pageNo));
        }

        return userPage.map(UserMapper::EntitytoDTO);
    }

    @Override
    public UserDTO getUserByUserID(Long userID) throws InvalidParametersException, ResourceNotFoundException {
        validateUserID(userID);

        Optional<UserEntity> optionalEntity = userRepository.findById(userID);

        if (optionalEntity.isEmpty()) {
            throw new ResourceNotFoundException("User", "userID", String.valueOf(userID));
        }

        return UserMapper.EntitytoDTO(optionalEntity.get());
    }

    @Override
    public Boolean createUser(UserDTO userDTO) throws InvalidParametersException {
        validateUserDTO(userDTO);

        UserEntity userEntity = UserMapper.DTOtoEntity(userDTO);

        try {
            userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidParametersException("A user with this email already exists.");
        } catch (Exception e) {
            throw new InvalidParametersException("Unexpected error: " + e.getMessage());
        }

        return true;
    }


    @Override
    public Boolean deleteUserByUserID(Long userID) throws InvalidParametersException, ResourceNotFoundException {
        validateUserID(userID);

        Optional<UserEntity> optionalUser = userRepository.findById(userID);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User", "userID", String.valueOf(userID));
        }

        userRepository.deleteById(userID);

        return true;
    }

    @Override
    public UserDTO updateUserByUserID(Long userID, UserDTO userDTO) throws InvalidParametersException, ResourceNotFoundException {
        validateUserID(userID);
        validateUserDTO(userDTO);

        Optional<UserEntity> optionalUser = userRepository.findById(userID);

        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User", "userID", String.valueOf(userID));
        }

        UserEntity existingUser = optionalUser.get();

        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setAddressFirstLine(userDTO.getAddressFirstLine());
        existingUser.setAddressLine2(userDTO.getAddressLine2());
        existingUser.setDistrict(userDTO.getDistrict());
        existingUser.setCity(userDTO.getCity());
        existingUser.setPostalCode(userDTO.getPostalCode());
        existingUser.setCountry(userDTO.getCountry());
        existingUser.setRoleEnum(RoleEnum.valueOf(userDTO.getRoleEnum()));

        UserEntity updatedUser = userRepository.save(existingUser);

        return UserMapper.EntitytoDTO(updatedUser);
    }

    private void validateUserID(Long userID) throws InvalidParametersException {
        if (userID == null || userID < 1) {
            throw new InvalidParametersException(UserConstants.INVALID_USER_ID);
        }
    }

    private void validateUserDTO(UserDTO userDTO) throws InvalidParametersException {
        if (userDTO.getFirstName() == null || userDTO.getFirstName().trim().length() < 2 || userDTO.getFirstName().length() > 32) {
            throw new InvalidParametersException("First name must be between 2 and 32 characters.");
        }
        if (userDTO.getLastName() == null || userDTO.getLastName().trim().length() < 2 || userDTO.getLastName().length() > 32) {
            throw new InvalidParametersException("Last name must be between 2 and 32 characters.");
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().length() < 8 || userDTO.getEmail().length() > 64) {
            throw new InvalidParametersException("Email must be between 8 and 64 characters.");
        }
        if (!userDTO.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,}$")) {
            throw new InvalidParametersException("Invalid email format.");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().length() < 8 || userDTO.getPassword().length() > 64) {
            throw new InvalidParametersException("Password must be between 8 and 64 characters.");
        }
        if (userDTO.getAddressFirstLine() == null || userDTO.getAddressFirstLine().trim().isEmpty() || userDTO.getAddressFirstLine().length() > 64) {
            throw new InvalidParametersException("Address first line must not be blank and max 64 characters.");
        }
        if (userDTO.getAddressLine2() == null || userDTO.getAddressLine2().trim().isEmpty() || userDTO.getAddressLine2().length() > 64) {
            throw new InvalidParametersException("Address line 2 must not be blank and max 64 characters.");
        }
        if (userDTO.getDistrict() == null || userDTO.getDistrict().trim().isEmpty()) {
            throw new InvalidParametersException("District must not be blank.");
        }
        if (userDTO.getCity() == null || userDTO.getCity().trim().isEmpty()) {
            throw new InvalidParametersException("City must not be blank.");
        }
        if (userDTO.getPostalCode() == null || userDTO.getPostalCode().trim().isEmpty()) {
            throw new InvalidParametersException("Postal code must not be blank.");
        }
        if (userDTO.getCountry() == null || userDTO.getCountry().trim().isEmpty()) {
            throw new InvalidParametersException("Country must not be blank.");
        }
    }
}
