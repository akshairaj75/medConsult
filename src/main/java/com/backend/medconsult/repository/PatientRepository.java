package com.backend.medconsult.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.medconsult.entity.people.Patient;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    List<Patient> findByAssignedDoctor_DoctorId(UUID doctorId);

    @Query("""
                SELECT p
                FROM Patient p
                WHERE p.assignedDoctor.doctorId = :doctorId
                   OR EXISTS (
                        SELECT lr
                        FROM LabResult lr
                        WHERE lr.patient = p
                          AND lr.orderedBy.doctorId = :doctorId
                   )
            """)
    List<Patient> findPatientsAccessibleToDoctor(@Param("doctorId") UUID doctorId);

}
