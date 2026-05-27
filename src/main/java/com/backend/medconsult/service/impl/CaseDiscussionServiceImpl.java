package com.backend.medconsult.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.medconsult.dto.caseRoomDto.CaseDiscussionMessageDto;
import com.backend.medconsult.dto.caseRoomDto.CaseDiscussionResponseDto;
import com.backend.medconsult.dto.caseRoomDto.CaseRoomDto;
import com.backend.medconsult.dto.caseRoomDto.CreateCaseRoomDto;
import com.backend.medconsult.entity.caseDiscussion.CaseDiscussion;
import com.backend.medconsult.entity.caseDiscussion.CaseRoom;
import com.backend.medconsult.entity.caseDiscussion.CaseRoomMember;
import com.backend.medconsult.entity.people.Doctor;
import com.backend.medconsult.entity.people.Patient;
import com.backend.medconsult.enums.CaseMemberRole;
import com.backend.medconsult.repository.CaseDiscussionRepository;
import com.backend.medconsult.repository.CaseRoomMemberRepository;
import com.backend.medconsult.repository.CaseRoomRepository;
import com.backend.medconsult.repository.DoctorRepository;
import com.backend.medconsult.repository.PatientRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.CaseDiscussionService;

@Service
public class CaseDiscussionServiceImpl implements CaseDiscussionService {

    @Autowired
    CaseDiscussionRepository caseDiscussionRepository;

    @Autowired
    CaseRoomRepository caseRoomRepository;

    @Autowired
    CaseRoomMemberRepository caseRoomMemberRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

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

    @Override
    public CaseRoomDto createRoom(
            CreateCaseRoomDto dto,
            CustomUserPrincipal authUser) {

        Doctor creator = authUser.getUser().getDoctor();

        Patient patient = patientRepository
                .findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        CaseRoom room = new CaseRoom();

        room.setPatient(patient);
        room.setCreatedBy(creator);

        room.setTitle(dto.getTitle());
        room.setDescription(dto.getDescription());
        room.setSpecialty(dto.getSpecialty());

        room.setCaseCode(
                "CASE-" + System.currentTimeMillis());

        caseRoomRepository.save(room);

        // creator member
        CaseRoomMember creatorMember = new CaseRoomMember();

        creatorMember.setCaseRoom(room);
        creatorMember.setDoctor(creator);
        creatorMember.setRole(CaseMemberRole.LEAD);

        caseRoomMemberRepository.save(creatorMember);

        // invited doctors
        for (UUID doctorId : dto.getDoctorIds()) {

            Doctor doctor = doctorRepository
                    .findById(doctorId)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            CaseRoomMember member = new CaseRoomMember();

            member.setCaseRoom(room);
            member.setDoctor(doctor);
            member.setRole(CaseMemberRole.COLLABORATOR);

            caseRoomMemberRepository.save(member);
        }

        return CaseRoomDto.fromEntity(room);
    }

}
