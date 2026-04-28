package com.backend.medconsult.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.medconsult.entity.clinicalData.LabResult;

public interface LabResultRepository extends JpaRepository<LabResult, UUID>{

    List<LabResult> findByPatient_PatientId(UUID patientId);

}
