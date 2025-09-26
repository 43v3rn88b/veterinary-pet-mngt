package com.codecraft.veterinary_pet_system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int quantity;
}
