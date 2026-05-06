package com.backend.medconsult.service;

import java.util.List;
import java.util.UUID;

import com.backend.medconsult.dto.HealthDto.PrescriptionDto;
import com.backend.medconsult.dto.HealthDto.PrescriptionRegisterDto;

public interface HealthService {

    List<PrescriptionRegisterDto> addPrescription(PrescriptionRegisterDto dto, UUID patientId);

    List<PrescriptionDto> getPrescriptionsByPatientId(UUID patientId, Boolean activeOnly);

}
