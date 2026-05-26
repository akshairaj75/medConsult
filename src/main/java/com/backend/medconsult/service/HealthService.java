package com.backend.medconsult.service;

import java.util.List;
import java.util.UUID;

import com.backend.medconsult.dto.HealthDto.MedAdherenceRegisterDto;
import com.backend.medconsult.dto.HealthDto.PrescriptionDto;
import com.backend.medconsult.dto.HealthDto.PrescriptionRegisterDto;
import com.backend.medconsult.security.CustomUserPrincipal;

public interface HealthService {

    List<PrescriptionRegisterDto> addPrescription(CustomUserPrincipal authUser, List<PrescriptionRegisterDto> dto, UUID consultationId);

    List<PrescriptionDto> getPrescriptionsByPatientId(CustomUserPrincipal authUser, Boolean activeOnly);

    MedAdherenceRegisterDto addAdherence(MedAdherenceRegisterDto dto, CustomUserPrincipal authUser);

    // List<MedAdherenceDto> getAdherence(CustomUserPrincipal authUser, UUID consultationId);

}
