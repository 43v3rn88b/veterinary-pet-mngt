package com.codecraft.veterinary_pet_system.mapper;


import com.codecraft.veterinary_pet_system.dto.SupplyDTO;
import com.codecraft.veterinary_pet_system.entity.Supply;

public class SupplyMapper {

    public static SupplyDTO toDTO(Supply supply) {
        SupplyDTO dto = new SupplyDTO();
        dto.setId(supply.getId());
        dto.setName(supply.getName());
        dto.setQuantity(supply.getQuantity());
        return dto;
    }

    public static Supply toEntity(SupplyDTO dto) {
        Supply supply = new Supply();
        supply.setId(dto.getId());
        supply.setName(dto.getName());
        supply.setQuantity(dto.getQuantity());
        return supply;
    }
}
