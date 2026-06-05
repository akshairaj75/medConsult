package com.backend.medconsult.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.medconsult.entity.clinicalData.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {
    List<Prescription> findByPatient_PatientId(UUID patientId);


    @Query("""
    SELECT p
    FROM Prescription p
    WHERE p.patient.patientId = :patientId
    AND p.status = com.backend.medconsult.enums.PrescriptionStatus.ACTIVE
    AND (
        p.expiresAt IS NULL
        OR p.expiresAt >= :today
    )
""")
    List<Prescription> findActivePrescriptionsByPatient_patientId(UUID patientId, LocalDate now);


    Optional <List<Prescription>> findByConsultation_ConsultationId(UUID consultationId);

}
