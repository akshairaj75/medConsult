package com.backend.medconsult.service;

import java.util.List;
import java.util.UUID;

import com.backend.medconsult.dto.patientDto.PatientDto;
import com.backend.medconsult.dto.patientDto.PatientRegisterDto;

public interface PatientService {

    PatientRegisterDto registerPatient(PatientRegisterDto dto);

    List<PatientDto> getAllPatients();

    PatientDto getPatientDetails(UUID patientId);


}
