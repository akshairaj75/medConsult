package com.backend.medconsult.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.patientDto.PatientDto;
import com.backend.medconsult.dto.patientDto.PatientRegisterDto;
import com.backend.medconsult.service.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    PatientService patientService;

    @PostMapping("/register") // add userId as path variable after implementing auth
    public ResponseEntity<PatientRegisterDto> registerPatient(@RequestBody PatientRegisterDto dto) {

        PatientRegisterDto registered = patientService.registerPatient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        List<PatientDto> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDto> getPatientDetails(@PathVariable UUID patientId){
        PatientDto patient = patientService.getPatientDetails(patientId);
        return ResponseEntity.ok(patient);
    }

}
