package org.example.vofasbackendv1.presentationlayer.mappers;

import org.example.vofasbackendv1.data_layer.entities.UserEntity;
import org.example.vofasbackendv1.data_layer.enums.RoleEnum;
import org.example.vofasbackendv1.exceptions.InvalidParametersException;
import org.example.vofasbackendv1.presentationlayer.dto.UserDTO;

public class UserMapper {
    public static UserDTO EntitytoUserDTO(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUserID(userEntity.getUserID());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setAddressFirstLine(userEntity.getAddressFirstLine());
        userDTO.setAddressLine2(userEntity.getAddressLine2());
        userDTO.setDistrict(userEntity.getDistrict());
        userDTO.setCity(userEntity.getCity());
        userDTO.setPostalCode(userEntity.getPostalCode());
        userDTO.setCountry(userEntity.getCountry());
        userDTO.setRoleEnum(userEntity.getRoleEnum() != null ? userEntity.getRoleEnum().name() : null);

        return userDTO;
    }

    public static UserEntity UserDTOtoEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        RoleEnum roleEnum;
        try {
            roleEnum = RoleEnum.valueOf(userDTO.getRoleEnum().toUpperCase());
        } catch (Exception e) {
            throw new InvalidParametersException("Invalid role: " + userDTO.getRoleEnum());
        }

        return new UserEntity(
                userDTO.getFirstName().trim(),
                userDTO.getLastName().trim(),
                userDTO.getEmail().trim(),
                userDTO.getPassword().trim(),
                userDTO.getAddressFirstLine().trim(),
                userDTO.getAddressLine2().trim(),
                userDTO.getDistrict().trim(),
                userDTO.getCity().trim(),
                userDTO.getPostalCode().trim(),
                userDTO.getCountry().trim(),
                roleEnum
        );
    }
}
