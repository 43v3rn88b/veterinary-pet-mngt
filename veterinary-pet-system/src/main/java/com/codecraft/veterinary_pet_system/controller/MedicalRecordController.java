package com.codecraft.veterinary_pet_system.controller;

import com.codecraft.veterinary_pet_system.entity.Appointment;
import com.codecraft.veterinary_pet_system.entity.MedicalRecord;
import com.codecraft.veterinary_pet_system.dto.MedicalRecordDTO;
import com.codecraft.veterinary_pet_system.mapper.MedicalRecordMapper;
import com.codecraft.veterinary_pet_system.repository.AppointmentRepository;
import com.codecraft.veterinary_pet_system.repository.MedicalRecordRepository;
import com.codecraft.veterinary_pet_system.repository.PetRepository;
import com.codecraft.veterinary_pet_system.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalRecords")
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    @PostMapping
    public MedicalRecordDTO addMedicalRecord(@RequestBody MedicalRecordDTO dto) {
        return medicalRecordService.addMedicalRecord(dto);
        //return ResponseEntity.ok(medicalRecordService.addMedicalRecord(dto));
    }

//    @PostMapping
//    public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecordDTO dto) {
//
//        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
//                .orElseThrow(() -> new RuntimeException("Appointment not found"));
//
////        Pet pet = petRepository.findById(dto.getPetId())
////                .orElseThrow(() -> new RuntimeException("Pet not found"));
//
//        Pet pet = appointment.getPet();
//        MedicalRecord record = new MedicalRecord();
//        record.setId(dto.getId());
//        //record.setDate(dto.getDate());
//        record.setAppointment(appointment);
//        record.setPet(pet);
//        record.setDiagnosis(dto.getDiagnosis());
//        record.setTreatment(dto.getTreatment());
//
//
//        return ResponseEntity.ok(medicalRecordRepository.save(record));
//    }
    @GetMapping
    public List<MedicalRecordDTO> getAllRecords() {
        return medicalRecordService.getAllRecords();
    }

    @PutMapping("/{id}")
    public MedicalRecordDTO updateRecord(
            @PathVariable Long id,
            @RequestBody MedicalRecordDTO dto) {
//        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
//                .orElseThrow(() -> new RuntimeException("Appointment not found"));
//        MedicalRecord updatedRecord = MedicalRecordMapper.toEntity(dto, appointment);
//        updatedRecord.setId(id);
//        MedicalRecord savedRecord = medicalRecordService.saveRecord(updatedRecord);
//        return ResponseEntity.ok(MedicalRecordMapper.toDTO(savedRecord));
        //return ResponseEntity.ok(medicalRecordService.updateRecord(id, dto));

        return medicalRecordService.updateRecord(id, dto);
    }



        @GetMapping("/pet/{petId}")
    public ResponseEntity<List<MedicalRecord>> getRecordsByPet(@PathVariable Long petId) {
        return ResponseEntity.ok(medicalRecordService.getRecordsByPet(petId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecords(@PathVariable Long id) {
        medicalRecordService.deleteRecords(id);
        return ResponseEntity.noContent().build();
    }
}

