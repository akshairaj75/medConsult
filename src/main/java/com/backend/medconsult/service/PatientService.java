package com.backend.medconsult.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.UserDto;
import com.backend.medconsult.dto.appointmentDto.AppointmentDto;
import com.backend.medconsult.dto.patientDto.PatientDto;
import com.backend.medconsult.dto.patientDto.PatientRegisterDto;
import com.backend.medconsult.security.CustomUserPrincipal;

public interface PatientService {

    PatientRegisterDto registerPatient(PatientRegisterDto dto, CustomUserPrincipal authUser);

    List<PatientDto> getAllPatients();

    PatientDto getPatientDetails(UUID patientId);

    UserDto updateUserProfile(CustomUserPrincipal authUser, UserDto dto, MultipartFile profilePhoto);

    AppointmentDto getLatestAppointment(CustomUserPrincipal authUser);

    AppointmentDto getLatestAppointmentToday(CustomUserPrincipal authUser);


}
