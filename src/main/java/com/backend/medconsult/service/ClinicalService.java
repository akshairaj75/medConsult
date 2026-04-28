package com.backend.medconsult.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.clinicalDataDto.FileDto;
import com.backend.medconsult.dto.clinicalDataDto.FileUploadRequestDto;
import com.backend.medconsult.dto.clinicalDataDto.LabItemRegisterDto;
import com.backend.medconsult.dto.clinicalDataDto.LabResultDto;
import com.backend.medconsult.dto.clinicalDataDto.LabResultListDto;
import com.backend.medconsult.dto.clinicalDataDto.LabResultRegisterDto;
import com.backend.medconsult.dto.clinicalDataDto.VitalsDto;

public interface ClinicalService {
    List<LabResultListDto> getAllLabResults();

    LabItemRegisterDto addLabItem(LabItemRegisterDto labItem, UUID labResultId );

    LabResultRegisterDto createLabResult(LabResultRegisterDto dto, List<MultipartFile> file);

    List<LabResultListDto> getPatientResults(UUID patientId);

    LabResultDto getSingleResult(UUID resultId);

    VitalsDto addVitals(UUID patientId, VitalsDto dto);

    List<VitalsDto> getVitals(UUID patientId);

    List<FileDto> uploadFiles(FileUploadRequestDto dto, List<MultipartFile> files, UUID uploadedById);

}
