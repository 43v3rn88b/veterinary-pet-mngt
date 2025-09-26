package com.codecraft.veterinary_pet_system.controller;

import com.codecraft.veterinary_pet_system.dto.AppointmentDTO;
import com.codecraft.veterinary_pet_system.entity.Appointment;
import com.codecraft.veterinary_pet_system.entity.Owner;
import com.codecraft.veterinary_pet_system.entity.Pet;
import com.codecraft.veterinary_pet_system.mapper.AppointmentMapper;
import com.codecraft.veterinary_pet_system.repository.OwnerRepository;
import com.codecraft.veterinary_pet_system.repository.PetRepository;
import com.codecraft.veterinary_pet_system.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
//    private final AppointmentRepository appointmentRepository;

    public AppointmentController(AppointmentService appointmentService, OwnerRepository ownerRepository, PetRepository petRepository) {
        this.appointmentService = appointmentService;
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
    }
//    @GetMapping("/appointments/pending")
//    public List<Appointment> getPendingAppointments() {
//        return appointmentRepository.findByStatus("PENDING");
//    }


    @PostMapping
    public ResponseEntity<AppointmentDTO> addAppointment(@RequestBody AppointmentDTO dto) {
        Owner owner = ownerRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        Appointment appointment = AppointmentMapper.toEntity(dto, owner, pet);
        Appointment saved = appointmentService.addAppointment(appointment);

        return ResponseEntity.ok(AppointmentMapper.toDTO(saved));
    }

    @GetMapping
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.getAllAppointments().stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id)
                .map(AppointmentMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Long id,
                                                            @RequestBody AppointmentDTO dto) {
        Owner owner = ownerRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        Appointment appointment = AppointmentMapper.toEntity(dto, owner, pet);
        appointment.setId(id);

        Appointment updated = appointmentService.updateAppointment(appointment);

        return ResponseEntity.ok(AppointmentMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
