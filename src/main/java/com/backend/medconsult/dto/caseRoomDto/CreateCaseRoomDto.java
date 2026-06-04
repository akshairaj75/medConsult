package com.backend.medconsult.dto.caseRoomDto;

import java.util.List;
import java.util.UUID;

public class CreateCaseRoomDto {

    private UUID patientId;
    private String specialty;
    private String title;
    private String description;
    private List<CaseRoomMemberDto> roomMembers;
    private List<UUID> doctorIds;

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
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

    public List<UUID> getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(List<UUID> doctorIds) {
        this.doctorIds = doctorIds;
    }

    public List<CaseRoomMemberDto> getRoomMembers() {
        return roomMembers;
    }

    public void setRoomMembers(List<CaseRoomMemberDto> roomMembers) {
        this.roomMembers = roomMembers;
    }

}
