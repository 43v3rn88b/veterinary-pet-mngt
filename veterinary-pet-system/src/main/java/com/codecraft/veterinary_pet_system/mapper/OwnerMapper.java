package com.codecraft.veterinary_pet_system.mapper;

import com.codecraft.veterinary_pet_system.dto.OwnerDTO;
import com.codecraft.veterinary_pet_system.entity.Owner;

import java.util.stream.Collectors;

public class OwnerMapper {
    public static OwnerDTO toDTO(Owner owner) {
        OwnerDTO dto = new OwnerDTO();
        dto.setId(owner.getId());
        dto.setName(owner.getName());
        dto.setPhone(owner.getPhone());
        dto.setEmail(owner.getEmail());
        dto.setAddress(owner.getAddress());

        if (owner.getPets() != null) {
            dto.setPets(owner.getPets().stream()
                    .map(PetMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
    // DTO -> Entity
    public static Owner toEntity(OwnerDTO dto) {
        Owner owner = new Owner();
        owner.setId(dto.getId());
        owner.setName(dto.getName());
        owner.setPhone(dto.getPhone());
        owner.setEmail(dto.getEmail());
        owner.setAddress(dto.getAddress());
        // ⚠ Pets are usually managed separately — skip mapping back here
        return owner;
    }
}
