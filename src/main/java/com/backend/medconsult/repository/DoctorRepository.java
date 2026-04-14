package com.backend.medconsult.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.medconsult.entity.people.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

} 
