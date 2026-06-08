package com.backend.medconsult.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.caseRoomDto.CaseDiscussionMessageDto;
import com.backend.medconsult.dto.caseRoomDto.CaseDiscussionResponseDto;
import com.backend.medconsult.dto.caseRoomDto.CaseRoomDto;
import com.backend.medconsult.dto.caseRoomDto.CreateCaseRoomDto;
import com.backend.medconsult.dto.clinicalDataDto.FileDto;
import com.backend.medconsult.dto.clinicalDataDto.FileUploadResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;

public interface CaseDiscussionService {
    List<CaseDiscussionResponseDto> loadCaseRoomMessages(UUID caseId);

    CaseDiscussionResponseDto sendCaseRoomMessage(CaseDiscussionMessageDto dto, CustomUserPrincipal authUser);

    CaseRoomDto createRoom(CreateCaseRoomDto dto, CustomUserPrincipal authUser);

    
    List<CaseRoomDto> getCases(CustomUserPrincipal authUser);

    FileUploadResponseDto storeCaseFile(MultipartFile file, UUID caseRoomId, CustomUserPrincipal authUser);

    List<FileDto> getCaseFiles(UUID caseId);

}
