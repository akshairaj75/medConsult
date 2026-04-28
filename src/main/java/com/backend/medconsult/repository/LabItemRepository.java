package com.backend.medconsult.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.medconsult.entity.clinicalData.LabItem;

public interface LabItemRepository extends JpaRepository<LabItem, UUID>{

}
