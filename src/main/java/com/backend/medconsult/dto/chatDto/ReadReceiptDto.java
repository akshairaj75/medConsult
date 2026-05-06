package com.backend.medconsult.dto.chatDto;

import java.util.UUID;

public class ReadReceiptDto {
    private UUID consultationId;
    private UUID messageId;
    private UUID readerId;
    
    public UUID getConsultationId() {
        return consultationId;
    }
    public void setConsultationId(UUID consultationId) {
        this.consultationId = consultationId;
    }
    public UUID getMessageId() {
        return messageId;
    }
    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }
    public UUID getReaderId() {
        return readerId;
    }
    public void setReaderId(UUID readerId) {
        this.readerId = readerId;
    }
}
