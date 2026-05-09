package com.backend.medconsult.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.UserDto;
import com.backend.medconsult.dto.patientDto.PatientDto;
import com.backend.medconsult.dto.patientDto.PatientRegisterDto;
import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.entity.people.Doctor;
import com.backend.medconsult.entity.people.Patient;
import com.backend.medconsult.repository.DoctorRepository;
import com.backend.medconsult.repository.PatientRepository;
import com.backend.medconsult.repository.UserRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    FileStorageService fileStorageService;

    @Override
    public PatientRegisterDto registerPatient(PatientRegisterDto dto, CustomUserPrincipal authUser) {
        User userEntity = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Patient patient = new Patient();
        patient.setUser(userEntity);
        patient.setPatientCode(dto.getPatientCode());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        patient.setBloodType(dto.getBloodType());
        patient.setNationalId(dto.getNationalId());
        patient.setInsuranceProvider(dto.getInsuranceProvider());
        patient.setInsuranceExpiry(dto.getinsuranceExpiry());
        patient.setEmergencyContactName(dto.getEmergencyContactName());
        patient.setEmergencyContactPhone(dto.getEmergencyContactPhone());
        patient.setAllergies(dto.getAllergies());
        patient.setChronicConditions(dto.getChronicConditions());
        if (dto.getAssignedDoctor() != null) {
            Doctor doctor = doctorRepository.findById(dto.getAssignedDoctor())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            patient.setAssignedDoctor(doctor);
        }
        patient.setAssignedDoctor(null);

        patient.setNotes(dto.getNotes());
        patientRepository.save(patient);
        return PatientRegisterDto.fromEntity(patient);
    }

    @Override
    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(PatientDto::fromEntity)
                .toList();
    }

    @Override
    public PatientDto getPatientDetails(UUID patientId) {
        PatientDto doctorDetails = patientRepository.findById(patientId)
                .map(PatientDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return doctorDetails;

    }

    @Override
    public UserDto updateUserProfile(
            CustomUserPrincipal authUser,
            UserDto dto,
            MultipartFile profilePhoto) {
        User user = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getFullName() != null) {
            user.setFullName(dto.getFullName());
        }
        if (dto.getLanguage() != null) {
            user.setLanguage(dto.getLanguage());
        }
        if (dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
        }
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            try {

                String fileName = fileStorageService.storeFile(profilePhoto);

                user.setProfilePhotoUrl(fileName);

            } catch (IOException e) {
                throw new RuntimeException(
                        "Failed to upload profile photo");
            }
        }

        return UserDto.fromEntity(user);
    }

}
