package com.backend.medconsult.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.caseRoomDto.CaseDiscussionResponseDto;
import com.backend.medconsult.dto.caseRoomDto.CaseRoomDto;
import com.backend.medconsult.dto.caseRoomDto.CreateCaseRoomDto;
import com.backend.medconsult.dto.chatDto.ChatConsultationDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.CaseDiscussionService;
import com.backend.medconsult.service.ConsultationService;

@RestController
@RequestMapping("/api/consultation")
public class ConsultationChatController {

   @Autowired
   ConsultationService consultationService;

   @Autowired
   CaseDiscussionService caseDiscussionService;

   @GetMapping("/{consultationId}")
   public ResponseEntity<ChatConsultationDto> getConsultDetails(@PathVariable UUID consultationId) {
      ChatConsultationDto dto = consultationService.getConsultDetails(consultationId);
      return ResponseEntity.ok(dto);

   }

   @GetMapping("/case/{caseId}/load-messages")
   public ResponseEntity<List<CaseDiscussionResponseDto>> getMessages(
         @PathVariable UUID caseId) {

      return ResponseEntity.ok(caseDiscussionService.loadCaseRoomMessages(caseId));
   }

   @PostMapping("/create")
   public ResponseEntity<CaseRoomDto> createRoom(@RequestBody CreateCaseRoomDto dto,
         @AuthenticationPrincipal CustomUserPrincipal authUser) {

      return ResponseEntity.ok(caseDiscussionService.createRoom(dto, authUser));
   }

}
