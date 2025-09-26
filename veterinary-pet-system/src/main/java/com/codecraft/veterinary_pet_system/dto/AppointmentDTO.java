package com.codecraft.veterinary_pet_system.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private Long id;
    private LocalDateTime date;
    private String reason;
    private Long petId;
    private Long ownerId;
    private String petName;
    private String ownerName;

}
