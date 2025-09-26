package com.codecraft.veterinary_pet_system.service;

import com.codecraft.veterinary_pet_system.entity.Pet;
import com.codecraft.veterinary_pet_system.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    public Pet addPet(Pet pet) {
        return petRepository.save(pet);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(Long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }
    public Pet updatePet(Long id, Pet updatedPet) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        pet.setName(updatedPet.getName());
        pet.setSpecies(updatedPet.getSpecies());
        pet.setBreed(updatedPet.getBreed());
        pet.setAge(updatedPet.getAge());
        pet.setOwner(updatedPet.getOwner());
        return petRepository.save(pet);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }

}

