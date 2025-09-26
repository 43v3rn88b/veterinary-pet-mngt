package com.codecraft.veterinary_pet_system.repository;


import com.codecraft.veterinary_pet_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

