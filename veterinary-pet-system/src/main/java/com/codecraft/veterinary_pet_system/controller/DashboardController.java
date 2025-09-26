package com.codecraft.veterinary_pet_system.controller;

import com.codecraft.veterinary_pet_system.dto.AppointmentSummaryDTO;
import com.codecraft.veterinary_pet_system.dto.DashboardDTO;
import com.codecraft.veterinary_pet_system.repository.AppointmentRepository;
import com.codecraft.veterinary_pet_system.repository.InvoiceRepository;
import com.codecraft.veterinary_pet_system.repository.OwnerRepository;
import com.codecraft.veterinary_pet_system.repository.PetRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final AppointmentRepository appointmentRepository;
    private final InvoiceRepository invoiceRepository;

    public DashboardController(
            OwnerRepository ownerRepository,
            PetRepository petRepository,
            AppointmentRepository appointmentRepository,
            InvoiceRepository invoiceRepository
    ) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.appointmentRepository = appointmentRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @GetMapping("/summary")
    public DashboardDTO getSummary() {
        DashboardDTO dto = new DashboardDTO();
        dto.setTotalOwners(ownerRepository.count());
        dto.setTotalPets(petRepository.count());
        dto.setTotalAppointments(appointmentRepository.count());
        dto.setTotalRevenue(invoiceRepository.sumTotalAmount());

        // ✅ Add today’s appointments
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        // Fetch next 5 upcoming appointments
        List<AppointmentSummaryDTO> pending = appointmentRepository.findTop5ByOrderByDateAsc()
                .stream()
                .map(appt -> {
                    AppointmentSummaryDTO a = new AppointmentSummaryDTO();
                    a.setId(appt.getId());
                    a.setDate(appt.getDate());
                    a.setReason(appt.getReason());
                    if (appt.getPet() != null) {
                        a.setPetName(appt.getPet().getName());
                        if (appt.getPet().getOwner() != null) {
                            a.setOwnerName(appt.getPet().getOwner().getName());
                        }
                    }
                    return a;
                })
                .toList();

        List<AppointmentSummaryDTO> todays = appointmentRepository
                .findByDateBetween(startOfDay, endOfDay)
                .stream()
                .map(appt -> {
                    AppointmentSummaryDTO a = new AppointmentSummaryDTO();
                    a.setId(appt.getId());
                    a.setDate(appt.getDate());
                    a.setReason(appt.getReason());
                    if (appt.getPet() != null) {
                        a.setPetName(appt.getPet().getName());
                        if (appt.getPet().getOwner() != null) {
                            a.setOwnerName(appt.getPet().getOwner().getName());
                        }
                    }
                    return a;
                })
                .toList();

        dto.setTodaysAppointments(todays);
        dto.setPendingAppointments(pending);
        return dto;
    }
}
