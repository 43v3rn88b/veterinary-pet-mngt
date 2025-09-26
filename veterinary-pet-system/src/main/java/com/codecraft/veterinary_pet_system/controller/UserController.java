package com.codecraft.veterinary_pet_system.controller;


import com.codecraft.veterinary_pet_system.dto.UserDTO;
import com.codecraft.veterinary_pet_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO dto,
                                           @RequestParam String password) {
        return ResponseEntity.ok(userService.addUser(dto, password));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
                                              @RequestBody UserDTO dto,
                                              @RequestParam(required = false) String password) {
        return ResponseEntity.ok(userService.updateUser(id, dto, password));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

