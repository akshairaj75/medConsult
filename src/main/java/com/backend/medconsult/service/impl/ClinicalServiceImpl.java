package com.backend.medconsult.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.clinicalDataDto.FileDto;
import com.backend.medconsult.dto.clinicalDataDto.FileUploadRequestDto;
import com.backend.medconsult.dto.clinicalDataDto.LabItemRegisterDto;
import com.backend.medconsult.dto.clinicalDataDto.LabResultDto;
import com.backend.medconsult.dto.clinicalDataDto.LabResultListDto;
import com.backend.medconsult.dto.clinicalDataDto.LabResultRegisterDto;
import com.backend.medconsult.dto.clinicalDataDto.LabResultUpdateDto;
import com.backend.medconsult.dto.clinicalDataDto.VitalsDto;
import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.entity.clinicalData.File;
import com.backend.medconsult.entity.clinicalData.LabItem;
import com.backend.medconsult.entity.clinicalData.LabResult;
import com.backend.medconsult.entity.clinicalData.Vital;
import com.backend.medconsult.entity.people.Doctor;
import com.backend.medconsult.entity.people.Patient;
import com.backend.medconsult.enums.FileCategory;
import com.backend.medconsult.enums.LabItemStatus;
import com.backend.medconsult.enums.LabStatus;
import com.backend.medconsult.repository.DoctorRepository;
import com.backend.medconsult.repository.FileRepository;
import com.backend.medconsult.repository.LabItemRepository;
import com.backend.medconsult.repository.LabResultRepository;
import com.backend.medconsult.repository.PatientRepository;
import com.backend.medconsult.repository.UserRepository;
import com.backend.medconsult.repository.VitalRepository;
import com.backend.medconsult.service.ClinicalService;

@Service
public class ClinicalServiceImpl implements ClinicalService {

        @Autowired
        LabResultRepository labResultRepository;

        @Autowired
        LabItemRepository labItemRepository;

        @Autowired
        PatientRepository patientRepository;

        @Autowired
        DoctorRepository doctorRepository;

        @Autowired
        FileStorageService fileStorageService;

        @Autowired
        VitalRepository vitalRepository;

        @Autowired
        UserRepository userRepository;

        @Autowired
        FileRepository fileRepository;

        @Override
        public List<LabResultListDto> getAllLabResults() {
                return labResultRepository.findAll()
                                .stream()
                                .map(LabResultListDto::fromEntity)
                                .toList();
        }

        @Override
        public List<LabResultListDto> getPatientResults(UUID patientId) {
                List<LabResultListDto> labResult = labResultRepository.findByPatient_PatientId(patientId)
                                .stream()
                                .map(LabResultListDto::fromEntity)
                                .toList();
                return labResult;
        }

        @Override
        public LabResultDto getSingleResult(UUID resultId) {
                LabResultDto result = labResultRepository.findById(resultId)
                                .map(LabResultDto::fromEntity)
                                .orElseThrow(() -> new RuntimeException("Result not found"));
                return result;
        }

        // @Override
        // public LabItemRegisterDto addLabItem(LabItemRegisterDto dto, UUID
        // labResultId) {
        // LabItem labItem = new LabItem();
        // labItem.setTestName(dto.getTestName());
        // labItem.setValue(dto.getValue());
        // labItem.setUnit(dto.getUnit());
        // labItem.setItemStatus(dto.getItemStatus());
        // labItem.setReferenceMin(dto.getReferenceMin());
        // labItem.setReferenceMax(dto.getReferenceMax());
        // labItem.setSortOrder(dto.getSortOrder());
        // labItemRepository.save(labItem);
        // return LabItemRegisterDto.fromEntity(labItem);
        // }

        @Override
        public LabResultRegisterDto createLabResult(LabResultRegisterDto dto, List<MultipartFile> files) {
                UUID patientId = dto.getPatient();
                UUID OrederedById = dto.getOrderedBy();
                boolean isAbnormal = false;
                Patient patient = patientRepository.findById(patientId)
                                .orElseThrow(() -> new RuntimeException("Patient not found"));
                Doctor orderedBy = doctorRepository.findById(OrederedById)
                                .orElseThrow(() -> new RuntimeException("Doctor not found"));

                LabResult labResult = new LabResult();
                labResult.setPatient(patient);
                labResult.setOrderedBy(orderedBy);
                labResult.setPanelName(dto.getPanelName());
                labResult.setTestDate(dto.getTestDate());
                labResult.setLabSource(dto.getLabSource());
                List<LabItem> items = new ArrayList<>();
                if (dto.getLabItems() != null) {
                        for (LabItemRegisterDto labItemDto : dto.getLabItems()) {
                                LabItem item = new LabItem();
                                item.setTestName(labItemDto.getTestName());
                                item.setValue(labItemDto.getValue());
                                item.setUnit(labItemDto.getUnit());
                                item.setReferenceMin(labItemDto.getReferenceMin());
                                item.setReferenceMax(labItemDto.getReferenceMax());
                                item.setItemStatus(calculateStatus(
                                                labItemDto.getValue(),
                                                labItemDto.getReferenceMin(),
                                                labItemDto.getReferenceMax()));
                                item.setSortOrder(labItemDto.getSortOrder());
                                item.setLabResult(labResult);
                                items.add(item);
                        }
                }
                labResult.setItems(items);
                for (LabItem item : items) {
                        if (item.getItemStatus() == LabItemStatus.HIGH ||
                                        item.getItemStatus() == LabItemStatus.LOW) {
                                isAbnormal = true;
                                break;
                        }
                }
                labResult.setAbnormal(isAbnormal);
                // if (isAbnormal) {
                //         labResult.setStatus(LabStatus.FLAGGED);
                // } else {
                //         labResult.setStatus(LabStatus.PENDING);

                // }
                labResultRepository.save(labResult);
                return LabResultRegisterDto.fromEntity(labResult);
        }

        @Override
        public VitalsDto addVitals(UUID patientId, VitalsDto dto) {
                Patient patient = patientRepository.findById(patientId)
                                .orElseThrow(() -> new RuntimeException("Patient not found"));

                User recordeBy = userRepository.findById(dto.getRecordedByUserId())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                Vital vital = new Vital();
                vital.setPatient(patient);
                vital.setRecordedBy(recordeBy);
                vital.setHeartRateBpm(dto.getHeartRateBpm());
                vital.setBpSystolic(dto.getBpSystolic());
                vital.setBpDiastolic(dto.getBpDiastolic());
                vital.setTemperatureC(dto.getTemperatureC());
                vital.setSpo2Percent(dto.getSpo2Percent());
                vital.setWeightKg(dto.getWeightKg());
                vital.setHeightCm(dto.getHeightCm());
                vital.setBmi(dto.getBmi());
                vital.setBloodGlucoseMmol(dto.getBloodGlucoseMmol());

                vitalRepository.save(vital);
                return VitalsDto.fromEntity(vital);
        }

        @Override
        public List<VitalsDto> getVitals(UUID patientId) {
                List<VitalsDto> vitals = vitalRepository.findByPatient_PatientId(patientId)
                                .stream()
                                .map(VitalsDto::fromEntity)
                                .collect(Collectors.toList());
                return vitals;
        }

                @Override
        public VitalsDto getVitalsById(UUID vitalId) {
                Vital vitals = vitalRepository.findById(vitalId)
                                .orElseThrow(() -> new RuntimeException("Vital record not found"));
                return VitalsDto.fromEntity(vitals);
        }

        @Override
        public VitalsDto getLatestVitals(UUID patientId) {

                Vital vitals = vitalRepository
                                .findTopByPatient_PatientIdOrderByRecordedAtDesc(patientId)
                                .orElseThrow(() -> new RuntimeException("No vitals found"));

                return VitalsDto.fromEntity(vitals);
        }

        @Override
        public VitalsDto updateVitals(UUID vitalId, VitalsDto dto) {
                Vital vital = vitalRepository.findById(vitalId)
                                .orElseThrow(() -> new RuntimeException("No vitals found"));

                if (dto.getHeartRateBpm() != null) {
                        vital.setHeartRateBpm(dto.getHeartRateBpm());
                }
                if (dto.getBpSystolic() != null) {
                        vital.setBpSystolic(dto.getBpSystolic());
                }
                if (dto.getBpDiastolic() != null) {
                        vital.setBpDiastolic(dto.getBpDiastolic());
                }
                if (dto.getTemperatureC() != null) {
                        vital.setTemperatureC(dto.getTemperatureC());
                }
                if (dto.getSpo2Percent() != null) {
                        vital.setSpo2Percent(dto.getSpo2Percent());
                }
                if (dto.getWeightKg() != null) {
                        vital.setWeightKg(dto.getWeightKg());
                }
                if (dto.getHeightCm() != null) {
                        vital.setHeightCm(dto.getHeightCm());
                }
                if (dto.getBmi() != null) {
                        vital.setBmi(dto.getBmi());
                }
                if (dto.getBloodGlucoseMmol() != null) {
                        vital.setBloodGlucoseMmol(dto.getBloodGlucoseMmol());
                }

                vitalRepository.save(vital);
                return VitalsDto.fromEntity(vital);

        }

        @Override
        public List<FileDto> uploadFiles(FileUploadRequestDto dto, List<MultipartFile> files, UUID uploadedById) {

                User uploadedBy = userRepository.findById(uploadedById)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                Patient patient = patientRepository.findById(dto.getPatientId())
                                .orElseThrow(() -> new RuntimeException("Patient not found"));

                List<FileDto> responseList = new ArrayList<>();

                for (MultipartFile file : files) {
                        try {
                                String storedFileName = fileStorageService.storeFile(file);

                                File entity = new File();
                                entity.setPatient(patient);
                                entity.setUploadedBy(uploadedBy);
                                entity.setFileName(storedFileName);
                                entity.setFileUrl("uploads/" + storedFileName);
                                entity.setFileSizeBytes(file.getSize());
                                entity.setMimeType(file.getContentType());
                                entity.setDescription(dto.getDescription());
                                entity.setFileCategory(
                                                dto.getFileCategory() != null
                                                                ? dto.getFileCategory()
                                                                : FileCategory.OTHER);

                                File saved = fileRepository.save(entity);
                                responseList.add(FileDto.fromEntity(saved));

                        } catch (Exception e) {
                                throw new RuntimeException("File upload failed", e);
                        }
                }
                return responseList;
        }

        @Override
        public LabResultUpdateDto reviewLabResult(UUID labResultId, LabResultUpdateDto dto) {
                LabResult labResult = labResultRepository.findById(labResultId)
                                .orElseThrow(() -> new RuntimeException("Lab result not found"));
                if (dto.getDoctorNotes() != null) {
                        labResult.setDoctorNotes(dto.getDoctorNotes());
                }
                // if (dto.getReviewedAt() != null) {
                labResult.setReviewedAt(LocalDateTime.now());
                // }
                if (dto.getLabStatus() != null) {
                        LabStatus status = dto.getLabStatus() == LabStatus.REVIEWED
                                        ? LabStatus.REVIEWED
                                        : dto.getLabStatus() == LabStatus.FLAGGED
                                                        ? LabStatus.FLAGGED
                                                        : LabStatus.PENDING;

                        labResult.setStatus(status);

                        labResult.setAbnormal(status == LabStatus.FLAGGED);
                }

                if (dto.getReviewedBy() != null) {
                        Doctor reviewer = doctorRepository.findById(dto.getReviewedBy())
                                        .orElseThrow(() -> new RuntimeException("Reviewer doctor not found"));
                        labResult.setReviewedBy(reviewer);
                }
                labResultRepository.save(labResult);
                // Implementation for reviewing lab result
                return LabResultUpdateDto.fromEntity(labResult);
        }


              //Helper method to calculate lab item status
     //============================================================================
        private LabItemStatus calculateStatus(String value, String min, String max) {
                try {
                        if (value == null || value.isBlank() ||
                                        min == null || min.isBlank() ||
                                        max == null || max.isBlank()) {
                                return LabItemStatus.NORMAL;
                        }

                        double val = Double.parseDouble(value.trim());
                        double minVal = Double.parseDouble(min.trim());
                        double maxVal = Double.parseDouble(max.trim());

                        return val < minVal ? LabItemStatus.LOW
                                        : val > maxVal ? LabItemStatus.HIGH
                                                        : LabItemStatus.NORMAL;

                } catch (NumberFormatException e) {
                        return LabItemStatus.NORMAL;
                }
        }
     //============================================================================




}