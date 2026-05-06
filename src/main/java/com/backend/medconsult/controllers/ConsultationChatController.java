package com.backend.medconsult.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.chatDto.ChatMessageDto;
import com.backend.medconsult.dto.chatDto.MessageDto;
import com.backend.medconsult.dto.chatDto.ReadReceiptDto;
import com.backend.medconsult.service.MessageService;

// @Controller
@RestController
public class ConsultationChatController {

   @Autowired
   private SimpMessagingTemplate messagingTemplate;

   @Autowired
   private MessageService messageService;

   @MessageMapping("/consultation/send")
   public void sendMessage(ChatMessageDto dto) {
       MessageDto saved = messageService.saveMessage(dto);

       messagingTemplate.convertAndSend("/topic/consultation/" 
        + dto.getConsultationId(), saved);
     }

   @MessageMapping("/consultation/read")
     public void markAsRead(ReadReceiptDto dto) {
        messageService.markRead(dto);
        
        messagingTemplate.convertAndSend("/topic/consultation/" 
        + dto.getConsultationId(), dto);
     }

}
