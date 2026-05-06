package com.backend.medconsult.service;

import com.backend.medconsult.dto.chatDto.ChatMessageDto;
import com.backend.medconsult.dto.chatDto.MessageDto;
import com.backend.medconsult.dto.chatDto.ReadReceiptDto;

public interface MessageService {

    MessageDto saveMessage(ChatMessageDto dto);
    void markRead(ReadReceiptDto dto);

}
