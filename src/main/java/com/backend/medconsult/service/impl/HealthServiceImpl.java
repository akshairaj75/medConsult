package com.backend.medconsult.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.medconsult.dto.HealthDto.PrescriptionDto;
import com.backend.medconsult.dto.HealthDto.PrescriptionRegisterDto;
import com.backend.medconsult.entity.clinicalData.Prescription;
import com.backend.medconsult.entity.consultations.Consultation;
import com.backend.medconsult.entity.people.Doctor;
import com.backend.medconsult.entity.people.Patient;
import com.backend.medconsult.repository.ConsultationRepository;
import com.backend.medconsult.repository.DoctorRepository;
import com.backend.medconsult.repository.PatientRepository;
import com.backend.medconsult.repository.PrescriptionRepository;
import com.backend.medconsult.service.HealthService;

@Service
public class HealthServiceImpl implements HealthService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    ConsultationRepository consultationRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Override
    public List<PrescriptionRegisterDto> addPrescription(PrescriptionRegisterDto dto, UUID patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + dto.getDoctorId()));

        LocalDate startDate = dto.getStartedAt() == null
                ? LocalDate.now()
                : dto.getStartedAt();

        // LocalDate expiryDate = startDate.plusDays(dto.getDurationDays());
        LocalDate expiryDate = dto.getDurationDays() != null
                ? startDate.plusDays(dto.getDurationDays())
                : null;

        Consultation consultation = dto.getConsultationId() != null
                ? consultationRepository.findById(dto.getConsultationId())
                        .orElseThrow(() -> new RuntimeException("Consultation not found"))
                : null;

        Prescription prescription = new Prescription();
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setConsultation(consultation);
        prescription.setMedicationName(dto.getMedicationName());
        prescription.setDosage(dto.getDosage());
        prescription.setFrequency(dto.getFrequency());
        prescription.setDurationDays(dto.getDurationDays());
        prescription.setRefillsAllowed(dto.getRefillsAllowed());
        prescription.setInstructions(dto.getInstructions());
        prescription.setStartedAt(startDate);
        prescription.setExpiresAt(expiryDate);

        Prescription savedPrescription = prescriptionRepository.save(prescription);

        return List.of(
                PrescriptionRegisterDto.fromEntity(savedPrescription));
    }

    @Override
    public List<PrescriptionDto> getPrescriptionsByPatientId(UUID patientId, Boolean activeOnly) {
        List<Prescription> prescriptions;
        if (activeOnly != null && activeOnly) {
            prescriptions = prescriptionRepository.findActivePrescriptionsByPatient_patientId(patientId, LocalDate.now());
        } else {
            prescriptions = prescriptionRepository.findByPatient_PatientId(patientId);
        }
        return prescriptions.stream()
                .map(PrescriptionDto::fromEntity)
                .toList();
    }

}
