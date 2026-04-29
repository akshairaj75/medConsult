package com.backend.medconsult.dto.clinicalDataDto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.backend.medconsult.entity.clinicalData.LabResult;
import com.backend.medconsult.enums.LabStatus;

public class LabResultUpdateDto {
    private UUID labResultId;
    private UUID patientId;
    private LabStatus labStatus;
    private String doctorNotes;
    private UUID reviewedBy;
    private LocalDateTime reviewedAt;

    // For response
    private String patientName;
    private Boolean abnormal;
    private String panelName;
    private LocalDateTime testDate;
    private String reviewerName;

    public UUID getLabResultId() {
        return labResultId;
    }

    public void setLabResultId(UUID labResultId) {
        this.labResultId = labResultId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public LabStatus getLabStatus() {
        return labStatus;
    }

    public void setLabStatus(LabStatus labStatus) {
        this.labStatus = labStatus;
    }

    public String getDoctorNotes() {
        return doctorNotes;
    }

    public void setDoctorNotes(String doctorNotes) {
        this.doctorNotes = doctorNotes;
    }

    public UUID getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(UUID reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Boolean getAbnormal() {
        return abnormal;
    }

    public void setAbnormal(Boolean abnormal) {
        this.abnormal = abnormal;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public LocalDateTime getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDateTime testDate) {
        this.testDate = testDate;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public static LabResultUpdateDto fromEntity(LabResult dto) {
        LabResultUpdateDto updateDto = new LabResultUpdateDto();
        updateDto.setLabResultId(dto.getLabResultId());
        updateDto.setPatientId(dto.getPatient().getPatientId());
        updateDto.setLabStatus(dto.getStatus());
        updateDto.setDoctorNotes(dto.getDoctorNotes());
        updateDto.setReviewedBy(dto.getReviewedBy().getDoctorId());
        updateDto.setReviewedAt(dto.getReviewedAt());
        updateDto.setPatientName(dto.getPatient().getUser().getFullName());
        updateDto.setAbnormal(dto.isAbnormal());
        updateDto.setPanelName(dto.getPanelName());
        updateDto.setTestDate(dto.getTestDate().atStartOfDay());
        updateDto.setReviewerName(dto.getReviewedBy().getUser().getFullName());
        return updateDto;
    }

}
