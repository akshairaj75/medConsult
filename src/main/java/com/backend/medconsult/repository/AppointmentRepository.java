package com.backend.medconsult.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.medconsult.entity.appointment.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    // Custom query to find appointments by doctor ID
    List<Appointment> findByDoctor_DoctorId(UUID doctorId);

}
