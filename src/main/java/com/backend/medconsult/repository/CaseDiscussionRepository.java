package com.backend.medconsult.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.medconsult.entity.caseDiscussion.CaseDiscussion;

public interface CaseDiscussionRepository extends JpaRepository<CaseDiscussion, UUID> {
    List<CaseDiscussion> findByCaseRoom_CaseIdOrderByCreatedAtAsc(
            UUID caseId);

}
