package com.backend.medconsult.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.medconsult.entity.clinicalData.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {
    List<Prescription> findByPatient_PatientId(UUID patientId);

    List<Prescription> findActivePrescriptionsByPatient_patientId(UUID patientId, LocalDate now);

}
