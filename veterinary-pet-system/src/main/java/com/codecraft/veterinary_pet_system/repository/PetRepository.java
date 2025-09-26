package com.codecraft.veterinary_pet_system.repository;

import com.codecraft.veterinary_pet_system.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwnerId(Long ownerId);
}

