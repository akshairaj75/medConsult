package com.backend.medconsult.dto.chatDto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.backend.medconsult.entity.consultations.Message;
import com.backend.medconsult.enums.MessageType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {

    private UUID messageId;
    private UUID consultationId;
    private UUID senderId;
    private String senderName;
    private String content;
    private String fileUrl;
    private Boolean isRead;
    private MessageType messageType;
    private LocalDateTime createdAt;

    public static ChatMessageDto fromEntity(Message message) {

        return ChatMessageDto.builder()

                .messageId(
                        message.getMessageId())

                .consultationId(
                        message.getConsultation()
                                .getConsultationId())

                .senderId(
                        message.getSender().getUserId())

                .senderName(
                        message.getSender().getFullName())

                .content(
                        message.getContent())

                .fileUrl(
                        message.getFileUrl())

                .isRead(
                        message.isRead())

                .messageType(
                        message.getMessageType())

                .createdAt(
                        message.getCreatedAt())

                .build();
    }

}
