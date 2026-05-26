package com.backend.medconsult.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.medconsult.entity.caseDiscussion.CaseRoom;

public interface CaseRoomRepository extends JpaRepository<CaseRoom, UUID>{

}
