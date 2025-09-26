package com.codecraft.veterinary_pet_system.service;

import com.codecraft.veterinary_pet_system.entity.Invoice;
import com.codecraft.veterinary_pet_system.dto.ReportDTO;
import com.codecraft.veterinary_pet_system.repository.AppointmentRepository;
import com.codecraft.veterinary_pet_system.repository.InvoiceRepository;
import com.codecraft.veterinary_pet_system.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final AppointmentRepository appointmentRepository;
    private final InvoiceRepository invoiceRepository;
    private final PetRepository petRepository;

    public ReportDTO generateReport() {
        Long totalAppointments = appointmentRepository.count();
        BigDecimal totalRevenue = invoiceRepository.findAll().stream()
                .map(Invoice::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Long totalPets = petRepository.count();

        return new ReportDTO(totalAppointments, totalRevenue, totalPets);
    }
}

