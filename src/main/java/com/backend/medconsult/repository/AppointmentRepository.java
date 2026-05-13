package com.backend.medconsult.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.medconsult.entity.appointment.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    // Custom query to find appointments by doctor ID

    // List<Appointment> findByDoctor_DoctorId(UUID doctorId);

    List<Appointment> findByDoctor_DoctorIdAndScheduledAtBetweenOrderByScheduledAtDesc(
        UUID doctorId,
        LocalDateTime start,
        LocalDateTime end);
    List<Appointment> findBypatient_patientIdAndScheduledAtBetweenOrderByScheduledAtDesc(
        UUID doctorId,
        LocalDateTime start,
        LocalDateTime end);

    List<Appointment> findByPatient_PatientIdOrderByScheduledAtDesc(UUID patientId);
    Optional<Appointment> findTopByPatient_PatientIdOrderByScheduledAtDesc(UUID patientId);

}
