package com.backend.medconsult.controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.clinicalDataDto.FileDto;
import com.backend.medconsult.dto.clinicalDataDto.FileUploadRequestDto;
import com.backend.medconsult.dto.clinicalDataDto.LabResultDto;
import com.backend.medconsult.dto.clinicalDataDto.LabResultListDto;
import com.backend.medconsult.dto.clinicalDataDto.LabResultRegisterDto;
import com.backend.medconsult.dto.clinicalDataDto.LabResultUpdateDto;
import com.backend.medconsult.dto.clinicalDataDto.VitalsDto;
import com.backend.medconsult.service.ClinicalService;

@RestController
@RequestMapping("/api/lab-result")
public class LabResultController {

    @Autowired
    private ClinicalService clinicalService;

    @GetMapping("/all")
    public ResponseEntity<List<LabResultListDto>> getAllLabResults() {
        List<LabResultListDto> labResults = clinicalService.getAllLabResults();
        return ResponseEntity.ok(labResults);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<LabResultListDto>> getPatientResults(@PathVariable UUID patientId) {
        List<LabResultListDto> labResults = clinicalService.getPatientResults(patientId);
        return ResponseEntity.ok(labResults);
    }

    @GetMapping("/view/{resultId}")
    public ResponseEntity<LabResultDto> getSingleResult(@PathVariable UUID resultId) {
        LabResultDto result = clinicalService.getSingleResult(resultId);
        return ResponseEntity.ok(result);

    }

    // --- MULTIPART (JSON + FILES) ---
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LabResultRegisterDto> createLabResult(
            @RequestPart("data") LabResultRegisterDto dto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        LabResultRegisterDto registered = clinicalService.createLabResult(dto, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    // --- JSON ONLY ---
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LabResultRegisterDto> createLabResultJson(
            @RequestBody LabResultRegisterDto dto) {
        LabResultRegisterDto registered = clinicalService.createLabResult(dto, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @PostMapping("/{patientId}/vitals")
    public ResponseEntity<VitalsDto> addVitals(@PathVariable UUID patientId, @RequestBody VitalsDto dto) {
        VitalsDto registered = clinicalService.addVitals(patientId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @GetMapping("/{patientId}/vitals")
    public ResponseEntity<VitalsDto> getLatestVitals(@PathVariable UUID patientId) {
        VitalsDto vitals = clinicalService.getLatestVitals(patientId);
        return ResponseEntity.ok(vitals);
    }

    @GetMapping("/{vitalId}/getVital")
    public ResponseEntity<VitalsDto> getVitalsById(@PathVariable UUID vitalId) {
        VitalsDto vitals = clinicalService.getVitalsById(vitalId);
        return ResponseEntity.ok(vitals);
    }

    @PutMapping("/{vitalId}/vitals")
    public ResponseEntity<VitalsDto> updateVitals(@PathVariable UUID vitalId, @RequestBody VitalsDto dto) {
        VitalsDto updatedVitals = clinicalService.updateVitals(vitalId, dto);
        return ResponseEntity.ok(updatedVitals);
    }

    @PostMapping(value = "/{uploadedById}/upload", consumes = "multipart/form-data")
    public ResponseEntity<List<FileDto>> uploadFiles(
            @RequestPart("data") FileUploadRequestDto dto,
            @RequestPart("files") List<MultipartFile> files,
            @PathVariable("uploadedById") UUID uploadedById) throws IOException {
        List<FileDto> savedFiles = clinicalService.uploadFiles(dto, files, uploadedById);
        return ResponseEntity.ok(savedFiles);
    }

    @PutMapping("/{labResultId}/review")
    public ResponseEntity<LabResultUpdateDto> reviewLabResult(@PathVariable UUID labResultId,
            @RequestBody LabResultUpdateDto dto) {
        LabResultUpdateDto reviewedResult = clinicalService.reviewLabResult(labResultId, dto);
        return ResponseEntity.ok(reviewedResult);
    }
}
