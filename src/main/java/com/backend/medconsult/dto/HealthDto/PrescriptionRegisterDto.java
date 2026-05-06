package com.backend.medconsult.dto.HealthDto;

import java.time.LocalDate;
import java.util.UUID;

import com.backend.medconsult.entity.clinicalData.Prescription;

public class PrescriptionRegisterDto {
    private UUID doctorId;
    private UUID patientId;
    private UUID consultationId;
    private String medicationName;
    private String dosage;
    private String frequency;
    private Integer durationDays;
    private int refillsAllowed;
    private String instructions;
    private LocalDate startedAt;
    private LocalDate expiresAt;

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(UUID consultationId) {
        this.consultationId = consultationId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public int getRefillsAllowed() {
        return refillsAllowed;
    }

    public void setRefillsAllowed(int refillsAllowed) {
        this.refillsAllowed = refillsAllowed;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public LocalDate getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDate startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDate getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDate expiresAt) {
        this.expiresAt = expiresAt;
    }

    public static PrescriptionRegisterDto fromEntity(Prescription prescription) {
        PrescriptionRegisterDto dto = new PrescriptionRegisterDto();
        dto.setDoctorId(prescription.getDoctor().getDoctorId());
        dto.setPatientId(prescription.getPatient().getPatientId());
        dto.setConsultationId(prescription.getConsultation() != null
                ? prescription.getConsultation().getConsultationId()
                : null);
        dto.setMedicationName(prescription.getMedicationName());
        dto.setDosage(prescription.getDosage());
        dto.setFrequency(prescription.getFrequency());
        dto.setDurationDays(prescription.getDurationDays());
        dto.setRefillsAllowed(prescription.getRefillsAllowed());
        dto.setInstructions(prescription.getInstructions());
        dto.setStartedAt(prescription.getStartedAt());
        dto.setExpiresAt(prescription.getExpiresAt());
        return dto;
    }

}
