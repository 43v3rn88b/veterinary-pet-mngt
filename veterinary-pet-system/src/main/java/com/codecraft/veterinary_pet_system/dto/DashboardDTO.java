package com.codecraft.veterinary_pet_system.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class DashboardDTO {
    private long totalOwners;
    private long totalPets;
    private long totalAppointments;
    private BigDecimal totalRevenue;
    private List<AppointmentSummaryDTO> pendingAppointments;
    private List<AppointmentSummaryDTO> todaysAppointments;
}
