package com.codecraft.veterinary_pet_system.service;

import com.codecraft.veterinary_pet_system.entity.Appointment;
import com.codecraft.veterinary_pet_system.repository.AppointmentRepository;
import com.codecraft.veterinary_pet_system.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;



//    public Appointment addAppointment(AppointmentRequest request) {
//        Pet pet = petRepository.findById(request.getPetId())
//                .orElseThrow(() -> new RuntimeException("Pet not found"));
//
//        Appointment appt = new Appointment();
//        appt.setDate(request.getDate());
//        appt.setReason(request.getReason());
//        appt.setPet(pet);
//
//        return appointmentRepository.save(appt);
//    }

    public AppointmentService(AppointmentRepository appointmentRepository, PetRepository petRepository) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
    }

    public Appointment addAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment updateAppointment(Appointment appointment) {
        Appointment existing = appointmentRepository.findById(appointment.getId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        existing.setDate(appointment.getDate());
        existing.setReason(appointment.getReason());
        existing.setPet(appointment.getPet());
        existing.setOwner(appointment.getOwner());

        return appointmentRepository.save(existing);
    }


//    public Appointment updateAppointment(Long id, AppointmentRequest request) {
//        Appointment appt = appointmentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Appointment not found"));
//
//        Pet pet = petRepository.findById(request.getPetId())
//                .orElseThrow(() -> new RuntimeException("Pet not found"));
//
//        appt.setDate(request.getDate());
//        appt.setReason(request.getReason());
//        appt.setPet(pet);
//
//        return appointmentRepository.save(appt);
//    }
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }
    @GetMapping("/appointments/today")
    public List<Appointment> getTodaysAppointments() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);

        return appointmentRepository.findByDateBetween(startOfDay, endOfDay);
    }


    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
