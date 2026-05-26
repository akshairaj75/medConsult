package com.backend.medconsult.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.medconsult.dto.caseRoomDto.CaseDiscussionMessageDto;
import com.backend.medconsult.dto.caseRoomDto.CaseDiscussionResponseDto;
import com.backend.medconsult.entity.caseDiscussion.CaseDiscussion;
import com.backend.medconsult.entity.caseDiscussion.CaseRoom;
import com.backend.medconsult.entity.people.Doctor;
import com.backend.medconsult.repository.CaseDiscussionRepository;
import com.backend.medconsult.repository.CaseRoomRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.CaseDiscussionService;

@Service
public class CaseDiscussionServiceImpl implements CaseDiscussionService {

    @Autowired
    CaseDiscussionRepository caseDiscussionRepository;

    @Autowired
    CaseRoomRepository caseRoomRepository;

    @Override
    public CaseDiscussionResponseDto sendMessage(
            CaseDiscussionMessageDto dto,
            CustomUserPrincipal authUser) {

        Doctor doctor = authUser.getUser().getDoctor();

        CaseRoom caseRoom = caseRoomRepository
                .findById(dto.getCaseId())
                .orElseThrow(
                        () -> new RuntimeException("Case room not found"));

        CaseDiscussion discussion = new CaseDiscussion();

        discussion.setCaseRoom(caseRoom);
        discussion.setAuthor(doctor);
        discussion.setContent(dto.getContent());
        discussion.setTags(dto.getTags());
        discussion.setFileUrl(dto.getFileUrl());
        caseDiscussionRepository.save(discussion);

        return CaseDiscussionResponseDto
                .fromEntity(discussion);
    }

    @Override
    public List<CaseDiscussionResponseDto> loadMessages(UUID caseId) {

        return caseDiscussionRepository
                .findByCaseRoom_CaseIdOrderByCreatedAtAsc(caseId)
                .stream()
                .map(CaseDiscussionResponseDto::fromEntity)
                .toList();
    }

}
