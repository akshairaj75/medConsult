package com.backend.medconsult.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.medconsult.entity.caseDiscussion.CaseDiscussion;

public interface CaseDiscussionRepository extends JpaRepository<CaseDiscussion, UUID> {

}
