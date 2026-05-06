package com.backend.medconsult.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.medconsult.dto.chatDto.ChatMessageDto;
import com.backend.medconsult.dto.chatDto.MessageDto;
import com.backend.medconsult.dto.chatDto.ReadReceiptDto;
import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.entity.consultations.Consultation;
import com.backend.medconsult.entity.consultations.Message;
import com.backend.medconsult.repository.ConsultationRepository;
import com.backend.medconsult.repository.MessageRepository;
import com.backend.medconsult.repository.UserRepository;
import com.backend.medconsult.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository repository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public MessageDto saveMessage(ChatMessageDto dto) {

        Consultation consultation = consultationRepository.findById(dto.getConsultationId())
                .orElseThrow(() -> new RuntimeException("Consultation not found"));

        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Message message = new Message();
        message.setConsultation(consultation);
        message.setSender(sender);
        message.setContent(dto.getContent());
        message.setMessageType(dto.getMessageType());

        repository.save(message);

        return MessageDto.fromMessageDto(message);
        // return null;
    }

    @Override
    public void markRead(ReadReceiptDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'markRead'");
    }

}
