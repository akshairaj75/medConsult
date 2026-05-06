package com.backend.medconsult.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.medconsult.entity.consultations.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, UUID> {

}
