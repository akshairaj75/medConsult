package com.backend.medconsult.dto.clinicalDataDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.backend.medconsult.entity.clinicalData.LabItem;
import com.backend.medconsult.entity.clinicalData.LabResult;
import com.backend.medconsult.enums.LabStatus;

public class LabResultRegisterDto {
    private UUID labResultId;
    private UUID patient;
    private UUID orderedBy;
    private String panelName;
    private LocalDate testDate;
    private String labSource;
    private LabStatus status;
    private List<LabItemRegisterDto> labItems;

    public UUID getLabResultId() {
        return labResultId;
    }

    public void setLabResultId(UUID labResultId) {
        this.labResultId = labResultId;
    }

    public UUID getPatient() {
        return patient;
    }

    public void setPatient(UUID patient) {
        this.patient = patient;
    }

    public UUID getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(UUID orderedBy) {
        this.orderedBy = orderedBy;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public String getLabSource() {
        return labSource;
    }

    public void setLabSource(String labSource) {
        this.labSource = labSource;
    }

    public List<LabItemRegisterDto> getLabItems() {
        return labItems;
    }

    public LabStatus getStatus() {
        return status;
    }

    public void setStatus(LabStatus status) {
        this.status = status;
    }

    public void setLabItems(List<LabItemRegisterDto> labItems) {
        this.labItems = labItems;
    }

    public static LabResultRegisterDto fromEntity(LabResult result) {
        LabResultRegisterDto dto = new LabResultRegisterDto();
        dto.setLabResultId(result.getLabResultId());
        dto.setPatient(result.getPatient().getPatientId());
        dto.setOrderedBy(result.getOrderedBy().getDoctorId());
        dto.setPanelName(result.getPanelName());
        dto.setTestDate(result.getTestDate());
        dto.setLabSource(result.getLabSource());
        dto.setStatus(result.getStatus());

        List<LabItemRegisterDto> items = new ArrayList<>();

        if (result.getItems() != null) {
            for (LabItem labItem : result.getItems()) {
                items.add(LabItemRegisterDto.fromEntity(labItem));
            }
        }

        dto.setLabItems(items);
        return dto;
    }
}
