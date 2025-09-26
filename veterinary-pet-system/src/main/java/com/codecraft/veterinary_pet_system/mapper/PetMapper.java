package com.codecraft.veterinary_pet_system.mapper;

import com.codecraft.veterinary_pet_system.dto.PetDTO;
import com.codecraft.veterinary_pet_system.entity.Owner;
import com.codecraft.veterinary_pet_system.entity.Pet;

public class PetMapper {
    public static PetDTO toDTO(Pet pet) {
        PetDTO dto = new PetDTO();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setSpecies(pet.getSpecies());
        dto.setBreed(pet.getBreed());
        dto.setAge(pet.getAge());
        //dto.setOwnerId(pet.getOwner() != null ? pet.getOwner().getId() : null);
        if(pet.getOwner() != null) {
            dto.setOwnerId(pet.getOwner().getId());
            dto.setOwnerName(pet.getOwner().getName());
        }
        return dto;
    }
    public static Pet toEntity(PetDTO dto, Owner owner) {
        Pet pet = new Pet();
        pet.setId(dto.getId());
        pet.setName(dto.getName());
        pet.setSpecies(dto.getSpecies());
        pet.setAge(dto.getAge());
        pet.setOwner(owner);  // ✅ use the real Owner entity
        return pet;
    }
}

