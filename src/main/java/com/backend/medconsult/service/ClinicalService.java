package com.backend.medconsult.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.clinicalDataDto.FileDto;
import com.backend.medconsult.dto.clinicalDataDto.FileUploadRequestDto;
import com.backend.medconsult.dto.clinicalDataDto.LabResultDto;
import com.backend.medconsult.dto.clinicalDataDto.LabResultListDto;
import com.backend.medconsult.dto.clinicalDataDto.LabResultRegisterDto;
import com.backend.medconsult.dto.clinicalDataDto.LabResultUpdateDto;
import com.backend.medconsult.dto.clinicalDataDto.VitalsDto;
import com.backend.medconsult.security.CustomUserPrincipal;

public interface ClinicalService {
    List<LabResultListDto> getAllLabResults(CustomUserPrincipal authUser);


    LabResultRegisterDto createLabResult(LabResultRegisterDto dto, List<MultipartFile> file);

    List<LabResultListDto> getPatientResults(UUID patientId);

    LabResultDto getSingleResult(UUID resultId);

    VitalsDto addVitals(CustomUserPrincipal authUser, VitalsDto dto);

    List<VitalsDto> getVitals(UUID patientId);

    List<FileDto> uploadFiles(FileUploadRequestDto dto, List<MultipartFile> files, UUID uploadedById);


    LabResultUpdateDto reviewLabResult(UUID labResultId, LabResultUpdateDto dto);


    VitalsDto getLatestVitals(CustomUserPrincipal authUser);


    VitalsDto updateVitals(UUID vitalId, VitalsDto dto);


    VitalsDto getVitalsById(UUID vitalId);

}
