package com.backend.medconsult.dto.chatDto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.backend.medconsult.entity.consultations.Message;
import com.backend.medconsult.enums.MessageType;

public class MessageDto {
    private UUID messageId;
    private UUID consultationId;
    private UUID senderId;
    private MessageType messageType;
    private String content;
    private String fileUrl;
    private String fileName;
    private Boolean isRead;
    private LocalDateTime readAt;
    private LocalDateTime createdAt;

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public UUID getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(UUID consultationId) {
        this.consultationId = consultationId;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

        public static MessageDto fromMessageDto(Message message) {
        MessageDto dto = new MessageDto();
        dto.setMessageId(message.getMessageId());
        dto.setConsultationId(message.getConsultation().getConsultationId());
        dto.setSenderId(message.getSender().getUserId());
        dto.setMessageType(message.getMessageType());
        dto.setContent(message.getContent());
        dto.setFileUrl(message.getFileUrl());
        dto.setFileName(message.getFileName());
        // dto.setFileSizeBytes(messageDto.getFileSizeBytes());
        // dto.setFileMimeType(messageDto.getFileMimeType());
        return dto;
    }


}
