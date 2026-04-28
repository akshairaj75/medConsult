package com.backend.medconsult.dto.clinicalDataDto;

import java.util.UUID;

import com.backend.medconsult.entity.clinicalData.File;
import com.backend.medconsult.enums.FileCategory;

public class FileDto {
    private UUID fileId;
    private UUID patientId;
    private String fileName;
    private String fileUrl;
    private Long fileSizeBytes;
    private String mimeType;
    private FileCategory fileCategory;
    private String description;

    public UUID getFileId() {
        return fileId;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Long getFileSizeBytes() {
        return fileSizeBytes;
    }

    public void setFileSizeBytes(Long fileSizeBytes) {
        this.fileSizeBytes = fileSizeBytes;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public FileCategory getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(FileCategory fileCategory) {
        this.fileCategory = fileCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static FileDto fromEntity(File file) {
        FileDto dto = new FileDto();
        dto.setFileId(file.getFileId());
        dto.setPatientId(file.getPatient().getPatientId());
        dto.setFileName(file.getFileName());
        dto.setFileCategory(file.getFileCategory());
        dto.setDescription(file.getDescription());
        dto.setFileUrl(file.getFileUrl());
        dto.setFileSizeBytes(file.getFileSizeBytes());
        dto.setMimeType(file.getMimeType());
        return dto;
    }

}
