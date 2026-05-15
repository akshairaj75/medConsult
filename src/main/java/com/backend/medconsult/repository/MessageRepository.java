package com.backend.medconsult.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.medconsult.entity.consultations.Message;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    @Query("""
            SELECT COUNT(m)
            FROM Message m
            WHERE m.isRead = false
            AND m.sender.id <> :userId
            """)
    long unreadCount(UUID userId);

    @Query("""
                SELECT m
                FROM Message m
                WHERE m.consultation.consultationId =
                        :consultationId
                ORDER BY m.createdAt ASC
            """)
    List<Message> findMessagesByConsultation(
            UUID consultationId);
}
