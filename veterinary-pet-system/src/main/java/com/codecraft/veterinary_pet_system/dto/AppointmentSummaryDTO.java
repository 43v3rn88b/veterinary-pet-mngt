package com.codecraft.veterinary_pet_system.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class AppointmentSummaryDTO {
    private Long id;
    private LocalDateTime date;
    private String reason;
    private String petName;
    private String ownerName;
}
