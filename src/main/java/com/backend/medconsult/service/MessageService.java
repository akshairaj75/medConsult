package com.backend.medconsult.service;
import java.util.List;
import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.chatDto.ChatMessageDto;
import com.backend.medconsult.dto.clinicalDataDto.FileUploadResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;

public interface MessageService {

    // MessageDto saveMessage(ChatMessageDto dto);
    void markConsultationRead(UUID consultationId, CustomUserPrincipal authUser);

    // ChatMessageDto process(ChatMessageDto request, Principal principal);

    List<ChatMessageDto> loadConsultMessages(UUID consultationId);

    ChatMessageDto saveConsultMessage(ChatMessageDto dto, CustomUserPrincipal authUser);

    @Nullable
    Long unreadCount(UUID userId);

    void markAsRead(UUID messageId);

    FileUploadResponseDto storeFile(MultipartFile file,UUID consultationId, CustomUserPrincipal authUser);

    FileUploadResponseDto storeCaseFile(MultipartFile file, UUID caseRoomId, CustomUserPrincipal authUser);

}
