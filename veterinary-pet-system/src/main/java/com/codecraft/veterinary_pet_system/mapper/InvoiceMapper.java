package com.codecraft.veterinary_pet_system.mapper;

import com.codecraft.veterinary_pet_system.dto.InvoiceDTO;
import com.codecraft.veterinary_pet_system.entity.Appointment;
import com.codecraft.veterinary_pet_system.entity.Invoice;

import java.time.LocalDate;

public class InvoiceMapper {

    public static InvoiceDTO toDTO(Invoice invoice) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(invoice.getId());
        dto.setDate(invoice.getDate().toString());
        dto.setAmount(invoice.getAmount());
        dto.setStatus(invoice.getStatus());

        if (invoice.getAppointment() != null) {
            dto.setAppointmentId(invoice.getAppointment().getId());
            dto.setAppointmentDate(invoice.getAppointment().getDate().toString());

            if (invoice.getAppointment().getPet() != null) {
                dto.setPetName(invoice.getAppointment().getPet().getName());
                if (invoice.getAppointment().getPet().getOwner() != null) {
                    dto.setOwnerName(invoice.getAppointment().getPet().getOwner().getName());
                }
            }
        }
        return dto;
    }

    public static Invoice toEntity(InvoiceDTO dto, Appointment appointment) {
        Invoice invoice = new Invoice();
        invoice.setId(dto.getId());
        invoice.setDate(LocalDate.now()); // assuming format yyyy-MM-dd
        invoice.setAmount(dto.getAmount());
        invoice.setStatus(dto.getStatus());
        invoice.setAppointment(appointment);
        return invoice;
    }
}

