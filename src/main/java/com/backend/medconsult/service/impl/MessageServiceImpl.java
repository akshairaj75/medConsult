package com.backend.medconsult.service.impl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.medconsult.dto.chatDto.ChatMessageDto;
import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.entity.consultations.Consultation;
import com.backend.medconsult.entity.consultations.Message;
import com.backend.medconsult.enums.MessageType;
import com.backend.medconsult.repository.ConsultationRepository;
import com.backend.medconsult.repository.MessageRepository;
import com.backend.medconsult.repository.UserRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.MessageService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    ConsultationRepository consultationRepository;

    // @Override
    // public MessageDto saveMessage(ChatMessageDto dto) {

    // Consultation consultation =
    // consultationRepository.findById(dto.getConsultationId())
    // .orElseThrow(() -> new RuntimeException("Consultation not found"));

    // User sender = userRepository.findById(dto.getSenderId())
    // .orElseThrow(() -> new RuntimeException("Sender not found"));

    // Message message = new Message();
    // message.setConsultation(consultation);
    // message.setSender(sender);
    // message.setContent(dto.getContent());
    // message.setMessageType(dto.getMessageType());

    // repository.save(message);

    // return MessageDto.fromMessageDto(message);
    // // return null;
    // }

    @Override
    public ChatMessageDto process(
            ChatMessageDto request,
            Principal principal) {
        User sender = userRepository
                .findByEmail(principal.getName())
                .orElseThrow(
                        () -> new RuntimeException(
                                "Sender not found"));

        Consultation consultation = consultationRepository
                .findById(request.getConsultationId())
                .orElseThrow(() -> new RuntimeException(
                        "Consultation not found"));

        Message message = new Message();
        message.setConsultation(consultation);
        message.setSender(sender);
        message.setContent(request.getContent());
        message.setFileUrl(request.getFileUrl());
        message.setMessageType(
                request.getMessageType() != null
                        ? request.getMessageType()
                        : MessageType.TEXT);

        Message saved = messageRepository.save(message);

        return ChatMessageDto.fromEntity(saved);
    }

    @Override
    public List<ChatMessageDto> loadConsultMessages(UUID consultationId) {

        return messageRepository
                .findMessagesByConsultation_ConsultationIdOrderByCreatedAtAsc(consultationId)
                .stream()
                .map(ChatMessageDto::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public void markConsultationRead(UUID consultationId, CustomUserPrincipal authUser) {

        messageRepository.markConsultationMessagesAsRead(
                consultationId,
                authUser.getUserId());
        // messageRepository.save(message);
    }

    public void markAsRead(UUID messageId) {

        Message message = messageRepository.findById(messageId).orElseThrow();
        message.setRead(true);
        message.setReadAt(LocalDateTime.now());
        messageRepository.save(message);
    }

    @Override
    public Long unreadCount(UUID userId) {

        return messageRepository.unreadCount(userId);
    }

    @Override
    public ChatMessageDto saveConsultMessage(ChatMessageDto dto, CustomUserPrincipal authUser) {
        Consultation consultation = consultationRepository.findById(dto.getConsultationId())
                .orElseThrow();

        User sender = userRepository.findById(authUser.getUserId())
                .orElseThrow();

        Message message = new Message();
        message.setConsultation(consultation);
        message.setSender(sender);
        message.setContent(dto.getContent());
        message.setRead(false);
        message.setMessageType(MessageType.TEXT);
        Message saved = messageRepository.save(message);

        return ChatMessageDto.fromEntity(saved);

    }

}
