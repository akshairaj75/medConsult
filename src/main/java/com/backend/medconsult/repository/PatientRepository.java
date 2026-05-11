package com.backend.medconsult.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.medconsult.entity.people.Patient;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    List<Patient> findByAssignedDoctor_DoctorId(UUID doctorId);


}
