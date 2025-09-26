package com.codecraft.veterinary_pet_system.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Setter
@Getter
public class MedicalRecordDTO {
    private Long id;
    //private LocalDate date;
    private Long appointmentId;
    private LocalDateTime appointmentDate;
    //private Long petId;
    private String diagnosis;
    private String treatment;
    private String petName;
    private String ownerName;
}
