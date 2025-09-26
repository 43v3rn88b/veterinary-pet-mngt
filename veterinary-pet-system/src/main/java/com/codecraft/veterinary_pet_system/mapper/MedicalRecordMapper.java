package com.codecraft.veterinary_pet_system.mapper;


import com.codecraft.veterinary_pet_system.dto.MedicalRecordDTO;
import com.codecraft.veterinary_pet_system.entity.MedicalRecord;

public class MedicalRecordMapper {

    public static MedicalRecordDTO toDTO(MedicalRecord record) {
        MedicalRecordDTO dto = new MedicalRecordDTO();
        dto.setId(record.getId());
        dto.setDiagnosis(record.getDiagnosis());
        dto.setTreatment(record.getTreatment());

        if (record.getAppointment() != null) {
            dto.setAppointmentId(record.getAppointment().getId());
            dto.setAppointmentDate(record.getAppointment().getDate());
            if (record.getPet() != null) {
                dto.setPetName(record.getPet().getName());
                if (record.getPet().getOwner() != null) {
                    dto.setOwnerName(record.getPet().getOwner().getName());
                }
            }
        }
        //dto.setPetId(record.getPet().getId());
        return dto;
    }

    public static MedicalRecord toEntity(MedicalRecordDTO dto) {
        MedicalRecord record = new MedicalRecord();
        record.setId(dto.getId());
        //record.setDate(dto.getDate());
        record.setDiagnosis(dto.getDiagnosis());
        record.setTreatment(dto.getTreatment());
        // ⚠️ Appointment & Pet should be set in Service after fetching from repo
        return record;
    }
}
