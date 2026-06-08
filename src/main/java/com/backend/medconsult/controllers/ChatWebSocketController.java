package com.backend.medconsult.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.caseRoomDto.CaseDiscussionMessageDto;
import com.backend.medconsult.dto.caseRoomDto.CaseDiscussionResponseDto;
import com.backend.medconsult.dto.chatDto.ChatMessageDto;
import com.backend.medconsult.dto.clinicalDataDto.FileUploadResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.CaseDiscussionService;
import com.backend.medconsult.service.MessageService;

import lombok.RequiredArgsConstructor;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final CaseDiscussionService caseDiscussionService;

    @MessageMapping("/chat.send")
    public void sendConsultMessage(ChatMessageDto dto, Principal principal) {

        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) principal;

        CustomUserPrincipal authUser = (CustomUserPrincipal) auth.getPrincipal();

        ChatMessageDto saved = messageService.saveConsultMessage(dto, authUser);

        messagingTemplate.convertAndSend("/topic/chat/" + dto.getConsultationId(), saved);
    }

    @GetMapping("/{consultationId}/messages")
    public ResponseEntity<List<ChatMessageDto>> getConsultMessages(
        @PathVariable UUID consultationId,
        @AuthenticationPrincipal CustomUserPrincipal authUser) {

        return ResponseEntity.ok(messageService.loadConsultMessages(consultationId, authUser));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Long> unread(Authentication authentication) {

        CustomUserPrincipal user = (CustomUserPrincipal) authentication.getPrincipal();

        Long unreadCount = messageService.unreadCount(user.getUserId());

        return ResponseEntity.ok(unreadCount);
    }

    @PostMapping("/{consultationId}/read")
    public ResponseEntity<Void> markRead(@PathVariable UUID consultationId,
            @AuthenticationPrincipal CustomUserPrincipal authUser) {

        messageService.markConsultationRead(consultationId, authUser);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/message/{messageId}/read")
    public ResponseEntity<Void> markMessageRead(@PathVariable UUID messageId) {

        messageService.markAsRead(messageId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponseDto> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam UUID consultationId,
            @AuthenticationPrincipal CustomUserPrincipal authUser)
            throws IOException {

        FileUploadResponseDto dto = messageService.storeFile(file, consultationId, authUser);
        // String url = fileStorageService.storeFile(file);

        return ResponseEntity.ok(dto);
    }

    // CASE ROOM DISCUSSIONS
    // =========================
    @MessageMapping("/case-chat.send")
    public void sendCaseRoomMessage(CaseDiscussionMessageDto dto, Principal principal) {

        CustomUserPrincipal authUser = (CustomUserPrincipal) ((Authentication) principal).getPrincipal();

        CaseDiscussionResponseDto response = caseDiscussionService.sendCaseRoomMessage(dto, authUser);

        messagingTemplate.convertAndSend("/topic/case-room/" + dto.getCaseId(), response);
    }

    @PostMapping("/case/upload")
    public ResponseEntity<FileUploadResponseDto> caseFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam UUID caseRoomId,
            @AuthenticationPrincipal CustomUserPrincipal authUser)
            throws IOException {

        FileUploadResponseDto dto = messageService.storeCaseFile(file, caseRoomId, authUser);
        // String url = fileStorageService.storeFile(file);

        return ResponseEntity.ok(dto);
    }

}
