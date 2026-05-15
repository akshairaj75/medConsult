package com.backend.medconsult.controllers;



import org.springframework.stereotype.Controller;


@Controller
public class ConsultationChatController {


   // @MessageMapping("/consultation.send")
   // public void sendMessage(ChatMessageDto dto) {
   //    MessageDto saved = messageService.saveMessage(dto);
   //    messagingTemplate.convertAndSend("/topic/consultation/"
   //          + dto.getConsultationId(), saved);
   // }

   // @MessageMapping("/consultation/read")
   // public void markAsRead(ReadReceiptDto dto) {
   //    messageService.markRead(dto);

   //    messagingTemplate.convertAndSend("/topic/consultation/"
   //          + dto.getConsultationId(), dto);
   // }

}
