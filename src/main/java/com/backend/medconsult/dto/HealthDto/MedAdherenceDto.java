package com.backend.medconsult.dto.HealthDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.backend.medconsult.entity.clinicalData.MedicationAdherence;

public class MedAdherenceDto {
    private UUID prescriptionId;
    private UUID adherenceId;
    private UUID patientId;
    private LocalDate recordedDate;
    private LocalDateTime recordedAt;
    private boolean taken;
    private String skippedReason;

    public UUID getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(UUID prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public UUID getAdherenceId() {
        return adherenceId;
    }

    public void setAdherenceId(UUID adherenceId) {
        this.adherenceId = adherenceId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public LocalDate getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(LocalDate recordedDate) {
        this.recordedDate = recordedDate;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public String getSkippedReason() {
        return skippedReason;
    }

    public void setSkippedReason(String skippedReason) {
        this.skippedReason = skippedReason;
    }

    public static MedAdherenceDto fromEntity(MedicationAdherence result) {
        MedAdherenceDto dto = new MedAdherenceDto();
        dto.setAdherenceId(result.getAdherenceId());
        dto.setPatientId(result.getPatient().getPatientId());
        dto.setPrescriptionId(result.getPrescription().getPrescriptionId());
        dto.setRecordedDate(result.getRecordedDate());
        dto.setSkippedReason(result.getSkippedReason());
        dto.setTaken(result.isTaken());
        dto.setRecordedAt(result.getRecordedAt());
        return dto;
    }

}
