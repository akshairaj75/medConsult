package com.backend.medconsult.dto.clinicalDataDto;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.enums.FileCategory;

public class FileUploadRequestDto {
    private UUID patientId;
    private UUID consultationId;
    private UUID caseId;
    private UUID labResultId;
    private FileCategory fileCategory;
    private String description;
    private List<MultipartFile> files; // multiple files
    
    public UUID getPatientId() {
        return patientId;
    }
    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }
    public UUID getConsultationId() {
        return consultationId;
    }
    public void setConsultationId(UUID consultationId) {
        this.consultationId = consultationId;
    }
    public UUID getCaseId() {
        return caseId;
    }
    public void setCaseId(UUID caseId) {
        this.caseId = caseId;
    }
    public UUID getLabResultId() {
        return labResultId;
    }
    public void setLabResultId(UUID labResultId) {
        this.labResultId = labResultId;
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
    public List<MultipartFile> getFiles() {
        return files;
    }
    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }



}
