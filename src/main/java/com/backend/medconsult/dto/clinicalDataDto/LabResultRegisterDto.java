package com.backend.medconsult.dto.clinicalDataDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private UUID reviewedBy;
    private String panelName;
    private LocalDate testDate;
    private String labSource;
    private LabStatus labStatus;
    private boolean isAbnormal;
    private LocalDateTime reviewedAt;
    private String doctorNotes;
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

    public UUID getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(UUID reviewedBy) {
        this.reviewedBy = reviewedBy;
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

    public LabStatus getLabStatus() {
        return labStatus;
    }

    public void setLabStatus(LabStatus labStatus) {
        this.labStatus = labStatus;
    }

    public boolean isAbnormal() {
        return isAbnormal;
    }

    public void setAbnormal(boolean isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public String getDoctorNotes() {
        return doctorNotes;
    }

    public void setDoctorNotes(String doctorNotes) {
        this.doctorNotes = doctorNotes;
    }

    public List<LabItemRegisterDto> getLabItems() {
        return labItems;
    }

    public void setLabItems(List<LabItemRegisterDto> labItems) {
        this.labItems = labItems;
    }

    public static LabResultRegisterDto fromEntity(LabResult result) {
        LabResultRegisterDto dto = new LabResultRegisterDto();
        dto.setLabResultId(result.getLabResultId());
        dto.setPatient(result.getPatient().getPatientId());
        dto.setOrderedBy(result.getOrderedBy().getDoctorId());
        dto.setReviewedBy(result.getReviewedBy().getDoctorId());
        dto.setPanelName(result.getPanelName());
        dto.setTestDate(result.getTestDate());
        dto.setLabSource(result.getLabSource());
        dto.setLabStatus(result.getStatus());
        dto.setAbnormal(result.isAbnormal());
        dto.setReviewedAt(result.getReviewedAt());
        dto.setDoctorNotes(result.getDoctorNotes());

        List<LabItemDto> items = new ArrayList<>();

        if (result.getItems() != null) {
            for (LabItem labItemDto : result.getItems()) {

                LabItemDto itemDto = new LabItemDto();

                itemDto.setTestName(labItemDto.getTestName());

                itemDto.setValue(labItemDto.getValue());
                itemDto.setUnit(labItemDto.getUnit());
                itemDto.setReferenceMin(labItemDto.getReferenceMin());
                itemDto.setReferenceMax(labItemDto.getReferenceMax());
                itemDto.setItemStatus(labItemDto.getItemStatus());
                itemDto.setSortOrder(labItemDto.getSortOrder());

                items.add(itemDto);
            }
        }
        
        return dto;
    }
}
