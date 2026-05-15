package com.backend.medconsult.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.chatDto.ChatMessageDto;
import com.backend.medconsult.entity.consultations.Consultation;
import com.backend.medconsult.repository.ConsultationRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.MessageService;
import com.backend.medconsult.service.impl.FileStorageService;

import lombok.RequiredArgsConstructor;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatWebSocketController {

        private final SimpMessagingTemplate messagingTemplate;
        private final FileStorageService fileStorageService;
        private final ConsultationRepository consultationRepository;
        private final MessageService messageService;

        @MessageMapping("/chat.send")
        public void sendMessage(
                        ChatMessageDto request,
                        Principal principal) {

                ChatMessageDto saved = messageService.process(request, principal);

                Consultation consultation = consultationRepository.findById(request.getConsultationId())
                                .orElseThrow();
                String senderEmail = principal.getName();
                String receiverEmail;

                if (consultation.getDoctor().getUser().getEmail().equals(senderEmail)) {
                        receiverEmail = consultation.getPatient().getUser().getEmail();
                } else {
                        receiverEmail = consultation.getDoctor().getUser().getEmail();
                }

                messagingTemplate.convertAndSendToUser(receiverEmail, "/queue/messages", saved);
        }

        @GetMapping("/{roomId}/messages")
        public ResponseEntity<List<ChatMessageDto>> getMessages(@PathVariable UUID roomId) {

                return ResponseEntity.ok(
                                messageService.loadMessages(roomId));
        }

        @GetMapping("/unread-count")
        public ResponseEntity<Long> unread(
                        Authentication authentication) {

                CustomUserPrincipal user = (CustomUserPrincipal) authentication.getPrincipal();

                Long unreadCount = messageService.unreadCount(user.getUserId());

                return ResponseEntity.ok(unreadCount);
        }

        @PostMapping("/read/{messageId}")
        public ResponseEntity<Void> read(
                        @PathVariable UUID messageId) {

                messageService.markAsRead(messageId);

                return ResponseEntity.ok().build();
        }

        @PostMapping("/upload")
        public ResponseEntity<String> upload(
                        @RequestParam MultipartFile file) throws IOException {

                String url = fileStorageService.storeFile(file);

                return ResponseEntity.ok(url);
        }

}
