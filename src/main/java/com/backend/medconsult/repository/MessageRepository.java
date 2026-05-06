package com.backend.medconsult.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.medconsult.entity.consultations.Message;

public interface MessageRepository extends JpaRepository<Message, UUID> {

}
