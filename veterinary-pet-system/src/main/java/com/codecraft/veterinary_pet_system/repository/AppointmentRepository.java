package com.codecraft.veterinary_pet_system.repository;

import com.codecraft.veterinary_pet_system.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Get all appointments where date is between start and end of today
    List<Appointment> findByDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
    List<Appointment> findTop5ByOrderByDateAsc();
}
