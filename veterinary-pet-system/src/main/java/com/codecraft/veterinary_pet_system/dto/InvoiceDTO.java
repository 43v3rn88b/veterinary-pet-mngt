package com.codecraft.veterinary_pet_system.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class InvoiceDTO {
    private Long id;
    private String date;
    private BigDecimal amount;
    private String status;

    private Long appointmentId;
    private String appointmentDate;
    private String petName;
    private String ownerName;
}
