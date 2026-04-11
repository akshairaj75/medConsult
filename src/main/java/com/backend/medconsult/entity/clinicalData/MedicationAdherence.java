package com.backend.medconsult.entity.clinicalData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.backend.medconsult.entity.people.Patient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "medication_adherence", indexes = {
        @Index(name = "idx_adherence_prescription", columnList = "prescription_id"),
        @Index(name = "idx_adherence_patient_date", columnList = "patient_id, recorded_date")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = { "prescription_id", "recorded_date" })
})
public class MedicationAdherence {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)

    @Column(name = "adherence_id", nullable = false, updatable = false, length=36
)
    private UUID adherenceId;

    // FK → prescriptions(prescription_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    // FK → patients(patient_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "recorded_date", nullable = false)
    private LocalDate recordedDate;

    @Column(nullable = false)
    private boolean taken; // true = taken, false = skipped

    @Column(name = "skipped_reason", length = 200)
    private String skippedReason;

    @CreationTimestamp
    @Column(name = "recorded_at", nullable = false, updatable = false)
    private LocalDateTime recordedAt;

    public UUID getAdherenceId() {
        return adherenceId;
    }

    public void setAdherenceId(UUID adherenceId) {
        this.adherenceId = adherenceId;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

}
