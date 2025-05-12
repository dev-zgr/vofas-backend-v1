package org.example.vofasbackendv1.presentationlayer.mappers;

import org.example.vofasbackendv1.data_layer.entities.UserEntity;
import org.example.vofasbackendv1.data_layer.enums.RoleEnum;
import org.example.vofasbackendv1.presentationlayer.dto.UserDTO;

public class UserMapper {
    public static UserDTO entityToDTO(UserEntity userEntity, UserDTO userDTO) {
        if (userEntity == null) {
            return null;
        }
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

    public static UserEntity dtoToEntity(UserDTO userDTO, UserEntity userEntity) {
        if (userDTO == null) {
            return null;
        }
        userEntity.setFirstName(userDTO.getFirstName().trim());
        userEntity.setLastName(userDTO.getLastName().trim());
        userEntity.setEmail(userDTO.getEmail().trim());
        userEntity.setPassword(userDTO.getPassword().trim());
        userEntity.setAddressFirstLine(userDTO.getAddressFirstLine().trim());
        userEntity.setAddressLine2(userDTO.getAddressLine2().trim());
        userEntity.setDistrict(userDTO.getDistrict().trim());
        userEntity.setCity(userDTO.getCity().trim());
        userEntity.setPostalCode(userDTO.getPostalCode().trim());
        userEntity.setCountry(userDTO.getCountry().trim());
        userEntity.setRoleEnum(RoleEnum.valueOf(userDTO.getRoleEnum()));
        return userEntity;
    }
}
