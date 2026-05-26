package com.backend.medconsult.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.HealthDto.MedAdherenceDto;
import com.backend.medconsult.dto.HealthDto.MedAdherenceRegisterDto;
import com.backend.medconsult.dto.HealthDto.PrescriptionDto;
import com.backend.medconsult.dto.HealthDto.PrescriptionRegisterDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.HealthService;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @Autowired
    HealthService healthService;

    @PostMapping("/prescription/{consultationId}/add")
    public ResponseEntity<List<PrescriptionRegisterDto>> addPrescription(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @RequestBody List<PrescriptionRegisterDto> dto,
            @PathVariable UUID consultationId) {
        List<PrescriptionRegisterDto> prescriptions = healthService.addPrescription(authUser, dto, consultationId);
        return ResponseEntity.ok(prescriptions);
    }

    @GetMapping("/prescription/{patientId}")
    public ResponseEntity<List<PrescriptionDto>> getPrescriptionsByPatientId(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @RequestParam(required = false) Boolean activeOnly) {
        List<PrescriptionDto> prescriptions = healthService.getPrescriptionsByPatientId(authUser, activeOnly);
        return ResponseEntity.ok(prescriptions);
    }

    @PostMapping("/med-adherence")
    public ResponseEntity<MedAdherenceRegisterDto> addAdherence(
            @RequestBody MedAdherenceRegisterDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser) {
        MedAdherenceRegisterDto adherence = healthService.addAdherence(dto, authUser);
        return ResponseEntity.ok(adherence);
    }


}
