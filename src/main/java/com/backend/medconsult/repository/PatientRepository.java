package com.backend.medconsult.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.medconsult.entity.people.Patient;

public interface PatientRepository extends JpaRepository<Patient, UUID> {


}
