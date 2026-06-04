package com.backend.medconsult.dto.caseRoomDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.backend.medconsult.dto.doctorDto.DoctorDto;
import com.backend.medconsult.dto.patientDto.PatientDto;
import com.backend.medconsult.entity.caseDiscussion.CaseRoom;
import com.backend.medconsult.enums.CaseStatus;
import com.backend.medconsult.enums.Priority;

public class CaseRoomDto {

    private UUID caseId;
    private String caseCode;
    private UUID patientId;

    private PatientDto patient;



    private DoctorDto createdBy;
    private String specialty;
    private String title;
    private String description;
    private CaseStatus status;
    private Priority priority;
    private List<String> pendingActions;
    private LocalDateTime closedAt;
    private LocalDateTime createdAt;
    private List<CaseRoomMemberDto> roomMembers;

    public UUID getCaseId() {
        return caseId;
    }

    public void setCaseId(UUID caseId) {
        this.caseId = caseId;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }


        public PatientDto getPatient() {
        return patient;
    }

    public void setPatient(PatientDto patient) {
        this.patient = patient;
    }



    public DoctorDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(DoctorDto createdBy) {
        this.createdBy = createdBy;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CaseStatus getStatus() {
        return status;
    }

    public void setStatus(CaseStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public List<String> getPendingActions() {
        return pendingActions;
    }

    public void setPendingActions(List<String> pendingActions) {
        this.pendingActions = pendingActions;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // public List<DoctorDto> getRoomMembers() {
    // return roomMembers;
    // }

    // public void setRoomMembers(List<DoctorDto> roomMembers) {
    // this.roomMembers = roomMembers;
    // }

    public List<CaseRoomMemberDto> getRoomMembers() {
        return roomMembers;
    }

    public void setRoomMembers(List<CaseRoomMemberDto> roomMembers) {
        this.roomMembers = roomMembers;
    }

    public static CaseRoomDto fromEntity(CaseRoom cs) {
        CaseRoomDto dto = new CaseRoomDto();
        dto.setCaseId(cs.getCaseId());
        dto.setCaseCode(cs.getCaseCode());
        dto.setPatientId(cs.getPatient().getPatientId());
        dto.setPatient(PatientDto.fromEntity(cs.getPatient()));
        dto.setCreatedBy(DoctorDto.fromEntity(cs.getCreatedBy()));
        dto.setSpecialty(cs.getSpecialty());
        dto.setTitle(cs.getTitle());
        dto.setDescription(cs.getDescription());
        dto.setStatus(cs.getStatus());
        dto.setPriority(cs.getPriority());
        dto.setPendingActions(cs.getPendingActions());
        dto.setClosedAt(cs.getClosedAt());
        dto.setCreatedAt(cs.getCreatedAt());
        if (cs.getMembers() != null) {
            dto.setRoomMembers(
                    cs.getMembers()
                            .stream()
                            .map(CaseRoomMemberDto::fromEntity)
                            .toList());
        }
        return dto;
    }
}
