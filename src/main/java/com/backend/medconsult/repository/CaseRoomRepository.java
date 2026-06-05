package com.backend.medconsult.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.medconsult.dto.caseRoomDto.CaseRoomDto;
import com.backend.medconsult.entity.caseDiscussion.CaseRoom;
import com.backend.medconsult.entity.people.Doctor;

public interface CaseRoomRepository extends JpaRepository<CaseRoom, UUID> {

    @Query("""
                SELECT cr
                FROM CaseRoom cr
                LEFT JOIN FETCH cr.members
                WHERE cr.caseId = :caseId
            """)
    Optional<CaseRoom> findByIdWithMembers(UUID caseId);

    List<CaseRoomDto> findByCreatedBy_DoctorId(UUID doctorId);

    List<CaseRoom> findByCreatedBy(Doctor doc);

     List<CaseRoom> findByMembers_Doctor(Doctor doc);

}
