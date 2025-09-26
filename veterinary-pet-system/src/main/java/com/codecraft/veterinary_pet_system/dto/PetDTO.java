package com.codecraft.veterinary_pet_system.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PetDTO {
    private Long id;
    private String name;
    private String species;
    private String breed;
    private int age;
    private Long ownerId;
    private String ownerName;
}

