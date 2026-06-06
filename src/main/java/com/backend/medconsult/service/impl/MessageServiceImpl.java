package com.backend.medconsult.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.medconsult.dto.chatDto.ChatMessageDto;
import com.backend.medconsult.dto.clinicalDataDto.FileUploadResponseDto;
import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.entity.caseDiscussion.CaseRoom;
import com.backend.medconsult.entity.clinicalData.File;
import com.backend.medconsult.entity.consultations.Consultation;
import com.backend.medconsult.entity.consultations.Message;
import com.backend.medconsult.entity.people.Patient;
import com.backend.medconsult.enums.MessageType;
import com.backend.medconsult.repository.CaseRoomRepository;
import com.backend.medconsult.repository.ConsultationRepository;
import com.backend.medconsult.repository.FileRepository;
import com.backend.medconsult.repository.MessageRepository;
import com.backend.medconsult.repository.UserRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.MessageService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MessageServiceImpl implements MessageService {

        @Autowired
        UserRepository userRepository;

        @Autowired
        MessageRepository messageRepository;

        @Autowired
        FileStorageService fileStorageService;

        @Autowired
        FileRepository fileRepository;

        @Autowired
        ConsultationRepository consultationRepository;

        @Autowired
        CaseRoomRepository caseRoomRepository;


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
                message.setMessageType(
                                dto.getMessageType() != null
                                                ? dto.getMessageType()
                                                : MessageType.TEXT);

                message.setFileUrl(dto.getFileUrl());
                message.setFileName(dto.getFileName());
                message.setFileSizeBytes(dto.getFileSizeBytes());
                message.setFileMimeType(dto.getFileMimeType());

                Message saved = messageRepository.save(message);

                return ChatMessageDto.fromEntity(saved);

        }

        @Override
        public FileUploadResponseDto storeFile(
                        MultipartFile file,
                        UUID consultationId,
                        CustomUserPrincipal authUser) {
                Consultation consultation = consultationRepository
                                .findById(consultationId)
                                .orElseThrow();

                Patient patient = consultation.getPatient();

                String url;
                try {
                        url = fileStorageService.storeFile(file);
                } catch (IOException e) {
                        url = null;
                }
                User user = authUser.getUser();

                File dbFile = new File();

                dbFile.setUploadedBy(user);
                dbFile.setPatient(patient);
                dbFile.setFileName(file.getOriginalFilename());
                dbFile.setFileUrl(url);
                dbFile.setConsultation(consultation);
                dbFile.setMimeType(file.getContentType());
                dbFile.setFileSizeBytes(file.getSize());

                File saved = fileRepository.save(dbFile);

                return FileUploadResponseDto.fromEntity(saved);

        }

        @Override
        public FileUploadResponseDto storeCaseFile(MultipartFile file, UUID caseRoomId,
                        CustomUserPrincipal authUser) {
                CaseRoom caseRoom = caseRoomRepository
                                .findById(caseRoomId)
                                .orElseThrow();

                Patient patient = caseRoom.getPatient();

                String url;
                try {
                        url = fileStorageService.storeFile(file);
                } catch (IOException e) {
                        url = null;
                }
                User user = authUser.getUser();

                File dbFile = new File();

                dbFile.setUploadedBy(user);
                dbFile.setPatient(patient);
                dbFile.setFileName(file.getOriginalFilename());
                dbFile.setFileUrl(url);
                dbFile.setCaseRoom(caseRoom);
                ;
                dbFile.setMimeType(file.getContentType());
                dbFile.setFileSizeBytes(file.getSize());

                File saved = fileRepository.save(dbFile);

                return FileUploadResponseDto.fromEntity(saved);
        }

}
