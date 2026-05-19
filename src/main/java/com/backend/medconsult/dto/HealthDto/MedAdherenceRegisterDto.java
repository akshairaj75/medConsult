package com.backend.medconsult.dto.HealthDto;

import java.time.LocalDate;
import java.util.UUID;

import com.backend.medconsult.entity.clinicalData.MedicationAdherence;

public class MedAdherenceRegisterDto {
    private UUID prescriptionId;
    private UUID patientId;
    private LocalDate recordedDate;
    private boolean taken;
    private String skippedReason;

    public UUID getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(UUID prescriptionId) {
        this.prescriptionId = prescriptionId;
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

    public static MedAdherenceRegisterDto fromEntity(MedicationAdherence result) {
        MedAdherenceRegisterDto dto = new MedAdherenceRegisterDto();
        dto.setPatientId(result.getPatient().getPatientId());
        dto.setPrescriptionId(result.getPrescription().getPrescriptionId());
        dto.setRecordedDate(result.getRecordedDate());
        dto.setSkippedReason(result.getSkippedReason());
        dto.setTaken(result.isTaken());
        return dto;
    }
}
