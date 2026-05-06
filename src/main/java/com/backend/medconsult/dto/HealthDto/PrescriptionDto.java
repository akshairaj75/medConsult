package com.backend.medconsult.dto.HealthDto;

import java.time.LocalDate;
import java.util.UUID;

import com.backend.medconsult.entity.clinicalData.Prescription;
import com.backend.medconsult.enums.PrescriptionStatus;

public class PrescriptionDto {
    private UUID prescriptionId;
    private UUID PatientId;
    private UUID doctorId;
    private UUID consultationId;
    private String medicationName;
    private String dosage;
    private String frequency;
    private Integer durationDays;
    private int refillsAllowed;
    private int refillsUsed;
    private PrescriptionStatus status;
    private String instructions;
    private String stopReason;
    private LocalDate startedAt;
    private LocalDate expiresAt;
    private boolean active;

    public UUID getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(UUID prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public UUID getPatientId() {
        return PatientId;
    }

    public void setPatientId(UUID patientId) {
        PatientId = patientId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
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

    public int getRefillsUsed() {
        return refillsUsed;
    }

    public void setRefillsUsed(int refillsUsed) {
        this.refillsUsed = refillsUsed;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getStopReason() {
        return stopReason;
    }

    public void setStopReason(String stopReason) {
        this.stopReason = stopReason;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static PrescriptionDto fromEntity(Prescription prescription) {
        PrescriptionDto dto = new PrescriptionDto();
        dto.setPrescriptionId(prescription.getPrescriptionId());
        dto.setPatientId(prescription.getPatient().getPatientId());
        dto.setDoctorId(prescription.getDoctor().getDoctorId());
        if (prescription.getConsultation() != null) {
            dto.setConsultationId(prescription.getConsultation().getConsultationId());
        }
        dto.setMedicationName(prescription.getMedicationName());
        dto.setDosage(prescription.getDosage());
        dto.setFrequency(prescription.getFrequency());
        dto.setDurationDays(prescription.getDurationDays());
        dto.setRefillsAllowed(prescription.getRefillsAllowed());
        dto.setRefillsUsed(prescription.getRefillsUsed());
        dto.setStatus(prescription.getStatus());
        dto.setInstructions(prescription.getInstructions());
        dto.setStopReason(prescription.getStopReason());
        dto.setStartedAt(prescription.getStartedAt());
        dto.setExpiresAt(prescription.getExpiresAt());

        // Determine if the prescription is active
        boolean active = prescription.getStatus() == PrescriptionStatus.ACTIVE
                && (prescription.getExpiresAt() == null || prescription.getExpiresAt().isAfter(LocalDate.now()));
        dto.setActive(active);

        return dto;
    }

}
