package com.codecraft.veterinary_pet_system.mapper;


import com.codecraft.veterinary_pet_system.dto.UserDTO;
import com.codecraft.veterinary_pet_system.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }

    public static User toEntity(UserDTO dto, String password) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(password); // pass explicitly, don’t expose in DTO
        user.setRole(dto.getRole());
        return user;
    }
}

