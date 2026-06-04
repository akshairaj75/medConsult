package com.backend.medconsult.dto.caseRoomDto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.backend.medconsult.dto.doctorDto.DoctorDto;
import com.backend.medconsult.entity.caseDiscussion.CaseRoomMember;
import com.backend.medconsult.enums.CaseMemberRole;

public class CaseRoomMemberDto {
    private UUID roomMemberId;
    private UUID caseId;
    private DoctorDto doctor;
    private CaseMemberRole role;
    private LocalDateTime joinedAt;

    public UUID getRoomMemberId() {
        return roomMemberId;
    }

    public void setRoomMemberId(UUID roomMemberId) {
        this.roomMemberId = roomMemberId;
    }

    public UUID getCaseId() {
        return caseId;
    }

    public void setCaseId(UUID caseId) {
        this.caseId = caseId;
    }

    public DoctorDto getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDto doctor) {
        this.doctor = doctor;
    }

    public CaseMemberRole getRole() {
        return role;
    }

    public void setRole(CaseMemberRole role) {
        this.role = role;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public static CaseRoomMemberDto fromEntity(CaseRoomMember member) {
        CaseRoomMemberDto dto = new CaseRoomMemberDto();
        dto.setRoomMemberId(member.getMemberId());
        dto.setCaseId(member.getCaseRoom().getCaseId());
        dto.setDoctor(DoctorDto.fromEntity(member.getDoctor()));
        dto.setRole(member.getRole());
        dto.setJoinedAt(member.getJoinedAt());
        return dto;
    }


}
