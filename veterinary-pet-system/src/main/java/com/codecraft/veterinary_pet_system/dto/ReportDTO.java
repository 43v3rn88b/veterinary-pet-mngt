package com.codecraft.veterinary_pet_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private Long totalAppointments;
    private BigDecimal totalRevenue;
    private Long totalPets;
}

