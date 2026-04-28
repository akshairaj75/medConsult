package com.backend.medconsult.dto.clinicalDataDto;

import java.time.LocalDate;
import java.util.UUID;

import com.backend.medconsult.entity.clinicalData.LabResult;
import com.backend.medconsult.enums.LabStatus;

public class LabResultListDto {
    private String panelName;
    private UUID labResultId;
    private LabStatus labStatus;
    private UUID patientId;
    private LocalDate testDate;
    public String getPanelName() {
        return panelName;
    }
    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }
    public UUID getLabResultId() {
        return labResultId;
    }
    public void setLabResultId(UUID labResultId) {
        this.labResultId = labResultId;
    }
    public LabStatus getLabStatus() {
        return labStatus;
    }
    public void setLabStatus(LabStatus labStatus) {
        this.labStatus = labStatus;
    }
    public UUID getPatientId() {
        return patientId;
    }
    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }
    public LocalDate getTestDate() {
        return testDate;
    }
    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public static LabResultListDto fromEntity(LabResult result){
        LabResultListDto dto = new LabResultListDto();
        dto.setLabResultId(result.getLabResultId());
        dto.setLabStatus(result.getStatus());
        dto.setPanelName(result.getPanelName());
        dto.setPatientId(result.getPatient().getPatientId());
        dto.setTestDate(result.getTestDate());
        return dto;
    }

}
