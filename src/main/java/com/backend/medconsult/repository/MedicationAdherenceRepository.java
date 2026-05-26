package com.backend.medconsult.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.medconsult.entity.clinicalData.MedicationAdherence;
import com.backend.medconsult.entity.clinicalData.Prescription;

public interface MedicationAdherenceRepository extends JpaRepository<MedicationAdherence, UUID>{

    Optional<MedicationAdherence> findByPrescriptionAndRecordedDate(Prescription prescription, LocalDate recordedDate);

    Optional<List<MedicationAdherence>> findByPatient_PatientId(UUID patientId);

    Optional<List<MedicationAdherence>> findByPrescription_Consultation_ConsultationId(UUID consultationId);

}
