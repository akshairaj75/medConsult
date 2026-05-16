package com.backend.medconsult.service;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.jspecify.annotations.Nullable;

import com.backend.medconsult.dto.chatDto.ChatMessageDto;
import com.backend.medconsult.security.CustomUserPrincipal;

public interface MessageService {

    // MessageDto saveMessage(ChatMessageDto dto);
    void markAsRead(UUID messageId);

    ChatMessageDto process(ChatMessageDto request, Principal principal);

    List<ChatMessageDto> loadMessages(UUID consultationId);

    ChatMessageDto saveMessage(ChatMessageDto dto, CustomUserPrincipal authUser);


    @Nullable
    Long unreadCount(UUID userId);

}
