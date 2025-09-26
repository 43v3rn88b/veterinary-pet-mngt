package com.codecraft.veterinary_pet_system.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OwnerDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private List<PetDTO> pets; // Embed pets, but controlled

}

