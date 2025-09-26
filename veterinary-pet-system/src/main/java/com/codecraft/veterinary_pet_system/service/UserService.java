package com.codecraft.veterinary_pet_system.service;

import com.codecraft.veterinary_pet_system.dto.UserDTO;
import com.codecraft.veterinary_pet_system.entity.User;
import com.codecraft.veterinary_pet_system.mapper.UserMapper;
import com.codecraft.veterinary_pet_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDTO addUser(UserDTO dto, String password) {
        User user = UserMapper.toEntity(dto, password);
        return UserMapper.toDTO(userRepository.save(user));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(Long id, UserDTO dto, String password) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existing.setUsername(dto.getUsername());
        if (password != null && !password.isBlank()) {
            existing.setPassword(password);
        }
        existing.setRole(dto.getRole());
        return UserMapper.toDTO(userRepository.save(existing));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

