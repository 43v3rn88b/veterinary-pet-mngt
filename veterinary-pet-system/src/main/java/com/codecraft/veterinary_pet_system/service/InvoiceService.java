package com.codecraft.veterinary_pet_system.service;

import com.codecraft.veterinary_pet_system.entity.Appointment;
import com.codecraft.veterinary_pet_system.entity.Invoice;
import com.codecraft.veterinary_pet_system.dto.InvoiceDTO;
import com.codecraft.veterinary_pet_system.mapper.InvoiceMapper;
import com.codecraft.veterinary_pet_system.repository.AppointmentRepository;
import com.codecraft.veterinary_pet_system.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    private final AppointmentRepository appointmentRepository;

    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getInvoicesByOwner(Long ownerId) {
        return invoiceRepository.findByOwnerId(ownerId);
    }
    public InvoiceDTO addInvoice(InvoiceDTO dto) {


        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Invoice invoice = InvoiceMapper.toEntity(dto, appointment);
        return InvoiceMapper.toDTO(invoiceRepository.save(invoice));
    }

    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(InvoiceMapper::toDTO)
                .collect(Collectors.toList());
    }
    public InvoiceDTO updateInvoice(Long id, InvoiceDTO dto) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        invoice.setAmount(dto.getAmount());
        invoice.setStatus(dto.getStatus());

        // Update appointment if provided
        if (dto.getAppointmentId() != null) {
            Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));
            invoice.setAppointment(appointment);
        }

        // ✅ Don’t update date, it should stay as creation date
        return InvoiceMapper.toDTO(invoiceRepository.save(invoice));
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}

