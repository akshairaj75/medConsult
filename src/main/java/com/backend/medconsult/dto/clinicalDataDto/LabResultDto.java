package com.backend.medconsult.dto.clinicalDataDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.backend.medconsult.dto.doctorDto.DoctorDetailDto;
import com.backend.medconsult.entity.clinicalData.LabResult;
import com.backend.medconsult.enums.LabStatus;

public class LabResultDto {
    private UUID labResultId;
    private UUID patientId;
    private UUID reviewedById;
    private DoctorDetailDto orderedBy;
    private DoctorDetailDto reviewedBy;
    private String panelName;
    private LocalDate testDate;
    private String labSource;
    private LabStatus labStatus;
    private boolean isAbnormal;
    private LocalDateTime reviewedAt;
    private String doctorNotes;
    private List<LabItemDto> labItems;
    private List<FileDto> files;

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

    public UUID getReviewedById() {
        return reviewedById;
    }

    public void setReviewedById(UUID reviewedById) {
        this.reviewedById = reviewedById;
    }

    public DoctorDetailDto getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(DoctorDetailDto orderedBy) {
        this.orderedBy = orderedBy;
    }

    public DoctorDetailDto getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(DoctorDetailDto reviewedBy) {
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

    public List<LabItemDto> getLabItems() {
        return labItems;
    }

    public void setLabItems(List<LabItemDto> labItems) {
        this.labItems = labItems;
    }

    public List<FileDto> getFiles() {
        return files;
    }

    public void setFiles(List<FileDto> files) {
        this.files = files;
    }

    public static LabResultDto fromEntity(LabResult result) {
        LabResultDto dto = new LabResultDto();
        dto.setLabResultId(result.getLabResultId());
        dto.setPatientId(result.getPatient().getPatientId());
        // dto.setOrderedBy(result.getOrderedBy());
        dto.setOrderedBy(DoctorDetailDto.fromEntity(result.getOrderedBy()));
        dto.setReviewedBy(
                result.getReviewedBy() != null
                        ? DoctorDetailDto.fromEntity(result.getReviewedBy())
                        : null);
        // dto.setReviewedBy(result.getReviewedBy().getDoctorId());
        dto.setPanelName(result.getPanelName());
        dto.setTestDate(result.getTestDate());
        dto.setLabSource(result.getLabSource());
        dto.setLabStatus(result.getStatus());
        dto.setAbnormal(result.isAbnormal());
        dto.setReviewedAt(result.getReviewedAt());
        dto.setDoctorNotes(result.getDoctorNotes());
        if (result.getItems() != null) {
            List<LabItemDto> itemDtos = result.getItems()
                    .stream()
                    .map(LabItemDto::fromEntity)
                    .toList();

            dto.setLabItems(itemDtos);
        }
        if (result.getFiles() != null) {
            List<FileDto> fileDto = result.getFiles()
                    .stream()
                    .map(FileDto::fromEntity)
                    .toList();
            dto.setFiles(fileDto);
        }
        return dto;
    }
}
