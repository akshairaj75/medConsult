package com.backend.medconsult.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.medconsult.entity.clinicalData.File;

public interface FileRepository extends JpaRepository<File, UUID> {

}
