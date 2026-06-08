package com.backend.medconsult.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.caseRoomDto.CaseDiscussionMessageDto;
import com.backend.medconsult.dto.caseRoomDto.CaseDiscussionResponseDto;
import com.backend.medconsult.dto.caseRoomDto.CaseRoomDto;
import com.backend.medconsult.dto.caseRoomDto.CreateCaseRoomDto;
import com.backend.medconsult.dto.clinicalDataDto.FileDto;
import com.backend.medconsult.dto.clinicalDataDto.FileUploadResponseDto;
import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.entity.caseDiscussion.CaseDiscussion;
import com.backend.medconsult.entity.caseDiscussion.CaseRoom;
import com.backend.medconsult.entity.caseDiscussion.CaseRoomMember;
import com.backend.medconsult.entity.clinicalData.File;
import com.backend.medconsult.entity.consultations.Consultation;
import com.backend.medconsult.entity.people.Doctor;
import com.backend.medconsult.entity.people.Patient;
import com.backend.medconsult.enums.CaseMemberRole;
import com.backend.medconsult.repository.CaseDiscussionRepository;
import com.backend.medconsult.repository.CaseRoomMemberRepository;
import com.backend.medconsult.repository.CaseRoomRepository;
import com.backend.medconsult.repository.ConsultationRepository;
import com.backend.medconsult.repository.DoctorRepository;
import com.backend.medconsult.repository.FileRepository;
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
    ConsultationRepository consultationRepository;

    @Autowired
    CaseRoomMemberRepository caseRoomMemberRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    FileRepository fileRepository;

    @Override
    public CaseDiscussionResponseDto sendCaseRoomMessage(CaseDiscussionMessageDto dto, CustomUserPrincipal authUser) {

        Doctor doctor = authUser.getUser().getDoctor();

        CaseRoom caseRoom = caseRoomRepository.findById(dto.getCaseId())
                .orElseThrow(() -> new RuntimeException("Case room not found"));

        CaseDiscussion discussion = new CaseDiscussion();

        discussion.setCaseRoom(caseRoom);
        discussion.setAuthor(doctor);
        discussion.setContent(dto.getContent());
        discussion.setTags(dto.getTags());
        discussion.setFileUrl(dto.getFileUrl());
        caseDiscussionRepository.save(discussion);

        return CaseDiscussionResponseDto.fromEntity(discussion);

    }

    @Override
    public List<CaseDiscussionResponseDto> loadCaseRoomMessages(UUID caseId) {

        List<CaseDiscussionResponseDto> dto = caseDiscussionRepository.findByCaseRoom_CaseIdOrderByCreatedAtAsc(caseId)
                .stream()
                .map(CaseDiscussionResponseDto::fromEntity).toList();

        return dto;
    }

    @Override
    public CaseRoomDto createRoom(CreateCaseRoomDto dto, CustomUserPrincipal authUser) {

        Doctor creator = authUser.getUser().getDoctor();

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        CaseRoom room = new CaseRoom();

        room.setPatient(patient);
        room.setCreatedBy(creator);
        room.setTitle(dto.getTitle());
        room.setDescription(dto.getDescription());
        room.setSpecialty(dto.getSpecialty());
        room.setCaseCode("CASE-" + System.currentTimeMillis());
        caseRoomRepository.save(room);

        Consultation consultation = consultationRepository
                .findById(dto.getConsultationId())
                .orElseThrow(() -> new RuntimeException("Consultation not found"));

        consultation.setCaseRoom(room);
        consultationRepository.save(consultation);

        // creator member
        CaseRoomMember creatorMember = new CaseRoomMember();

        creatorMember.setCaseRoom(room);
        creatorMember.setDoctor(creator);
        creatorMember.setRole(CaseMemberRole.LEAD);

        // room.getMembers().add(creatorMember);

        caseRoomMemberRepository.save(creatorMember);

        System.out.println("Creator: " + creator.getDoctorId());

        for (UUID doctorId : dto.getDoctorIds()) {
            System.out.println("Invited: " + doctorId);
        }

        // invited doctors
        for (UUID doctorId : dto.getDoctorIds()) {

            if (doctorId.equals(creator.getDoctorId())) {
                continue;
            }

            Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            CaseRoomMember member = new CaseRoomMember();

            member.setCaseRoom(room);
            member.setDoctor(doctor);
            member.setRole(CaseMemberRole.COLLABORATOR);

            caseRoomMemberRepository.save(member);
        }
        // room = caseRoomRepository.findById(room.getCaseId())
        // .orElseThrow(() -> new RuntimeException("Case room not found"));

        CaseRoomDto res = CaseRoomDto.fromEntity(room);

        return res;
    }

    @Override
    public List<CaseRoomDto> getCases(CustomUserPrincipal authUser) {
        UUID doctorId = authUser.getUser().getDoctor().getDoctorId();

        Doctor doc = doctorRepository.findById(doctorId)
                .orElseThrow();
        List<CaseRoomDto> response = caseRoomRepository.findByMembers_Doctor(doc)
                .stream()
                .map(caseRoom -> {
                    CaseRoomDto dto = CaseRoomDto.fromEntity(caseRoom);

                    consultationRepository
                            .findByCaseRoom(caseRoom)
                            .ifPresent(c -> dto.setConsultationId(c.getConsultationId()));

                    return dto;
                })
                .toList();

        return response;
    }

    @Override
    public FileUploadResponseDto storeCaseFile(MultipartFile file, UUID caseRoomId,
            CustomUserPrincipal authUser) {
        CaseRoom caseRoom = caseRoomRepository
                .findById(caseRoomId)
                .orElseThrow();

        Patient patient = caseRoom.getPatient();

        String url;
        try {
            url = fileStorageService.storeFile(file);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed");
        }
        User user = authUser.getUser();

        File dbFile = new File();

        dbFile.setUploadedBy(user);
        dbFile.setPatient(patient);
        dbFile.setFileName(file.getOriginalFilename());
        dbFile.setFileUrl(url);
        dbFile.setCaseRoom(caseRoom);
        dbFile.setMimeType(file.getContentType());
        dbFile.setFileSizeBytes(file.getSize());

        File saved = fileRepository.save(dbFile);

        return FileUploadResponseDto.fromEntity(saved);
    }

    @Override
    public List<FileDto> getCaseFiles(UUID caseId) {
        List<File> files = fileRepository
                .findByCaseRoom_CaseIdOrderByCreatedAtAsc(caseId);

        return files.stream()
                .map(FileDto::fromEntity)
                .toList();

    }

}
