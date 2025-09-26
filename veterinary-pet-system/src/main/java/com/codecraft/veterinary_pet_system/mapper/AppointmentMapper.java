package com.codecraft.veterinary_pet_system.mapper;

import com.codecraft.veterinary_pet_system.dto.AppointmentDTO;
import com.codecraft.veterinary_pet_system.entity.Appointment;
import com.codecraft.veterinary_pet_system.entity.Owner;
import com.codecraft.veterinary_pet_system.entity.Pet;

public class AppointmentMapper {

    // Entity -> DTO
    public static AppointmentDTO toDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setDate(appointment.getDate());
        dto.setReason(appointment.getReason());

        if (appointment.getPet() != null) {
            dto.setPetId(appointment.getPet().getId());
            dto.setPetName(appointment.getPet().getName());
        }
        if (appointment.getOwner() != null) {
            dto.setOwnerId(appointment.getOwner().getId());
            dto.setOwnerName(appointment.getOwner().getName());
        }

        return dto;
    }

    // DTO -> Entity
    public static Appointment toEntity(AppointmentDTO dto, Owner owner, Pet pet) {
        Appointment appointment = new Appointment();
        appointment.setId(dto.getId());
        appointment.setDate(dto.getDate());
        appointment.setReason(dto.getReason());
        appointment.setOwner(owner);
        appointment.setPet(pet);
        return appointment;
    }
}

