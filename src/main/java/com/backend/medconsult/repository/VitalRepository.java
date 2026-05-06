package com.backend.medconsult.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.medconsult.entity.clinicalData.Vital;

public interface VitalRepository extends JpaRepository<Vital, UUID> {

    List<Vital> findByPatient_PatientId(UUID patientId);
    Optional<Vital> findTopByPatient_PatientIdOrderByRecordedAtDesc(UUID patientId);

}
