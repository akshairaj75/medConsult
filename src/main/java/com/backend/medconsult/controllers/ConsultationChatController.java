package com.backend.medconsult.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.chatDto.ChatConsultationDto;
import com.backend.medconsult.service.ConsultationService;

@RestController
@RequestMapping("/api/consultation")
public class ConsultationChatController {

   @Autowired
   ConsultationService consultationService;

   @GetMapping("/{consultationId}")
   public ResponseEntity<ChatConsultationDto> getConsultDetails(@PathVariable UUID consultationId){
      ChatConsultationDto dto =  consultationService.getConsultDetails(consultationId);
      return ResponseEntity.ok(dto);

   }

}
