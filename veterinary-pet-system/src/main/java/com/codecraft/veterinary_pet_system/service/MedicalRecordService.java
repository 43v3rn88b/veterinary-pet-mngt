package com.codecraft.veterinary_pet_system.service;

import com.codecraft.veterinary_pet_system.dto.MedicalRecordDTO;
import com.codecraft.veterinary_pet_system.entity.*;
import com.codecraft.veterinary_pet_system.mapper.MedicalRecordMapper;
import com.codecraft.veterinary_pet_system.repository.AppointmentRepository;
import com.codecraft.veterinary_pet_system.repository.MedicalRecordRepository;
import com.codecraft.veterinary_pet_system.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;

    public MedicalRecordDTO addMedicalRecord(MedicalRecordDTO dto) {
        if (dto.getAppointmentId() == null) {
            throw new IllegalArgumentException("Appointment ID is required");
        }
        boolean exists = medicalRecordRepository.existsByAppointmentId(dto.getAppointmentId());
        if (exists) {
            throw new IllegalStateException("A medical record already exists for this appointment.");
        }
        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        //Pet pet = petRepository.findById(dto.getPetId())
        //        .orElseThrow(() -> new RuntimeException("Pet not found"));

        MedicalRecord record = new MedicalRecord();
        record.setDiagnosis(dto.getDiagnosis());
        record.setTreatment(dto.getTreatment());
        record.setAppointment(appointment);

        return MedicalRecordMapper.toDTO(medicalRecordRepository.save(record));
    }

    public List<MedicalRecordDTO> getAllRecords() {
        return medicalRecordRepository.findAll()
                .stream()
                .map(MedicalRecordMapper::toDTO)
                .collect(Collectors.toList());
    }
    public void deleteRecords(Long id) {
        medicalRecordRepository.deleteById(id);
    }

    public MedicalRecordDTO updateRecord(Long id, MedicalRecordDTO dto) {
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found"));

        if (dto.getAppointmentId() != null &&
                !dto.getAppointmentId().equals(record.getAppointment().getId())) {

            // check if another record already exists for this appointment
            boolean exists = medicalRecordRepository.existsByAppointmentId(dto.getAppointmentId());
            if (exists) {
                throw new IllegalStateException("A medical record already exists for this appointment.");
            }

            Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));
            record.setAppointment(appointment);
        }

        record.setDiagnosis(dto.getDiagnosis());
        record.setTreatment(dto.getTreatment());

        // Update appointment if provided
//        if (dto.getAppointmentId() != null) {
//            Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
//                    .orElseThrow(() -> new RuntimeException("Appointment not found"));
//            record.setAppointment(appointment);
//        }

        // Update pet if provided
//        if (dto.getPetId() != null) {
//            Pet pet = petRepository.findById(dto.getPetId())
//                    .orElseThrow(() -> new RuntimeException("Pet not found"));
//            record.setPet(pet);
//        }

        return MedicalRecordMapper.toDTO(medicalRecordRepository.save(record));
    }

    public List<MedicalRecord> getRecordsByPet(Long petId) {
        return medicalRecordRepository.findByPetId(petId);
    }
}

