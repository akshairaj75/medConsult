package com.backend.medconsult.service;

import java.util.UUID;

import com.backend.medconsult.dto.chatDto.ChatConsultationDto;

public interface ConsultationService {

    ChatConsultationDto getConsultDetails(UUID consultationId);

}
