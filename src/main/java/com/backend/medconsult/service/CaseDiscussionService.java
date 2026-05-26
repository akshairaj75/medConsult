package com.backend.medconsult.service;

import java.util.List;
import java.util.UUID;

import com.backend.medconsult.dto.caseRoomDto.CaseDiscussionMessageDto;
import com.backend.medconsult.dto.caseRoomDto.CaseDiscussionResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;

public interface CaseDiscussionService {
    List<CaseDiscussionResponseDto> loadMessages(UUID caseId);

    CaseDiscussionResponseDto sendMessage(CaseDiscussionMessageDto dto, CustomUserPrincipal authUser);

}
