package com.backend.medconsult.dto.clinicalDataDto;

import java.util.UUID;

import com.backend.medconsult.entity.clinicalData.File;

public class FileUploadResponseDto {

    private UUID fileId;
    private String fileUrl;
    private String fileName;
    private Long fileSizeBytes;
    private String mimeType;

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

    public static FileUploadResponseDto fromEntity(File file){
        FileUploadResponseDto dto = new FileUploadResponseDto();
        dto.setFileId(file.getFileId());
        dto.setFileName(file.getFileName());
        dto.setFileSizeBytes(file.getFileSizeBytes());
        dto.setFileUrl(file.getFileUrl());
        dto.setMimeType(file.getMimeType());
        return dto;

    }

}
