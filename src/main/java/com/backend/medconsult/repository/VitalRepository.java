package com.backend.medconsult.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.medconsult.entity.clinicalData.Vital;

public interface VitalRepository extends JpaRepository<Vital, Long> {

    List<Vital> findByPatient_PatientId(UUID patientId);

}
