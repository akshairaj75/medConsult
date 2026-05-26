package com.backend.medconsult.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.medconsult.entity.appointment.Appointment;
import com.backend.medconsult.enums.AppointmentStatus;

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

    @Query("""
                SELECT a
                FROM Appointment a
                WHERE a.doctor.doctorId = :doctorId
                  AND a.status NOT IN :excludedStatuses
                ORDER BY a.scheduledAt DESC
            """)
    List<Appointment> findActiveDoctorAppointments(
            @Param("doctorId") UUID doctorId,
            @Param("excludedStatuses") List<AppointmentStatus> excludedStatuses);

    @Query("""
                SELECT a
                FROM Appointment a
                WHERE a.doctor.doctorId = :doctorId
                  AND a.scheduledAt BETWEEN :startOfDay AND :endOfDay
                  AND a.status IN :excludedStatuses
                ORDER BY a.scheduledAt ASC
            """)
    List<Appointment> findTodayDoctorAppointments(
            @Param("doctorId") UUID doctorId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay,
            @Param("excludedStatuses") List<AppointmentStatus> excludedStatuses);

    List<Appointment> findByPatient_PatientIdOrderByScheduledAtDesc(UUID patientId);

    Optional<Appointment> findTopByPatient_PatientIdOrderByScheduledAtDesc(UUID patientId);

    // boolean existsByPatient_PatientIdAndDoctor_DoctorIdAndScheduledAt(
    // UUID patientId,
    // UUID doctorId,
    // LocalDateTime scheduledAt);

    // boolean existsByDoctor_DoctorIdAndScheduledAt(
    // UUID doctorId,
    // LocalDateTime scheduledAt);

    Optional<Appointment> findByDoctor_DoctorIdAndScheduledAt(
            UUID doctorId,
            LocalDateTime scheduledAt);

    Optional<Appointment> findTopByPatient_PatientIdAndScheduledAtBetweenOrderByScheduledAtDesc(
            UUID patientId,
            LocalDateTime start,
            LocalDateTime end);

    List<Appointment> findByDoctor_DoctorIdAndScheduledAtBetween(
            UUID doctorId,
            LocalDateTime start,
            LocalDateTime end);

    Optional<Appointment> findByConsultation_ConsultationId(UUID consultationId);
}
