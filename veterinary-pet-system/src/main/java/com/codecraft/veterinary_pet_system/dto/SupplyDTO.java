package com.codecraft.veterinary_pet_system.dto;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SupplyDTO {
    private Long id;
    private String name;
    private int quantity;
}
