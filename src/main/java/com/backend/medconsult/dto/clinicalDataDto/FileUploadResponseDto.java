package com.backend.medconsult.dto.clinicalDataDto;

import java.util.UUID;

import com.backend.medconsult.entity.clinicalData.File;

public class FileUploadResponseDto {

    private UUID fileId;
    private String fileUrl;
    private String fileName;
    private Long fileSizeBytes;
    private String mimeType;

    private UUID caseId;
    private UUID consultationId;



    public UUID getFileId() {
        return fileId;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
        public UUID getCaseId() {
        return caseId;
    }

    public void setCaseId(UUID caseId) {
        this.caseId = caseId;
    }

    public UUID getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(UUID consultationId) {
        this.consultationId = consultationId;
    }


    public static FileUploadResponseDto fromEntity(File file){
        FileUploadResponseDto dto = new FileUploadResponseDto();
        dto.setFileId(file.getFileId());
        dto.setFileName(file.getFileName());
        dto.setFileSizeBytes(file.getFileSizeBytes());
        dto.setFileUrl(file.getFileUrl());
        dto.setMimeType(file.getMimeType());
        if (file.getCaseRoom() != null) {
            dto.setCaseId(file.getCaseRoom().getCaseId());
        }
        if (file.getConsultation() != null) {
            dto.setConsultationId(file.getConsultation().getConsultationId());
        }
        return dto;

    }

}
