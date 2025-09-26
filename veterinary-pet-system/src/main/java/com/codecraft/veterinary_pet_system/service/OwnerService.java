package com.codecraft.veterinary_pet_system.service;

import org.springframework.stereotype.Service;
import com.codecraft.veterinary_pet_system.entity.Owner;
import com.codecraft.veterinary_pet_system.repository.OwnerRepository;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Owner addOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public Owner updateOwner(Long id, Owner updated) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        owner.setName(updated.getName());
        owner.setPhone(updated.getPhone());
        owner.setAddress(updated.getAddress());
        return ownerRepository.save(owner);
    }
    public Optional<Owner> getOwnerById(Long id) {
        return ownerRepository.findById(id);
    }

    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }
}

