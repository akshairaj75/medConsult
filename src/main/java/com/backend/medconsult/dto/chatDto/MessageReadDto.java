package com.backend.medconsult.dto.chatDto;

import java.util.UUID;

public class MessageReadDto {
    private UUID messageId;

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

}
