package com.backend.medconsult.entity.clinicalData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.backend.medconsult.entity.people.Doctor;
import com.backend.medconsult.entity.people.Patient;
import com.backend.medconsult.enums.LabStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "lab_results", indexes = {
        @Index(name = "idx_lab_patient", columnList = "patient_id"),
        @Index(name = "idx_lab_ordered_by", columnList = "ordered_by"),
        @Index(name = "idx_lab_reviewed_by", columnList = "reviewed_by")
})
public class LabResult {
    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)

    @Column(name = "lab_result_id", nullable = false, updatable = false, length=36
)
    private UUID labResultId;

    // FK → patients(patient_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // FK → doctors(doctor_id) (optional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordered_by")
    private Doctor orderedBy;

    // FK → doctors(doctor_id) (optional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    private Doctor reviewedBy;

    @Column(name = "panel_name", nullable = false, length = 150)
    private String panelName;

    @Column(name = "test_date", nullable = false)
    private LocalDate testDate;

    @Column(name = "lab_source", length = 150)
    private String labSource;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LabStatus status = LabStatus.PENDING;

    @Column(name = "is_abnormal", nullable = false)
    private boolean isAbnormal = false;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "doctor_notes", columnDefinition = "TEXT")
    private String doctorNotes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "labResult", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<LabItem> items;

    @OneToMany(mappedBy = "labResult")
    private List<File> files;

    public UUID getLabResultId() {
        return labResultId;
    }

    public void setLabResultId(UUID labResultId) {
        this.labResultId = labResultId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(Doctor orderedBy) {
        this.orderedBy = orderedBy;
    }

    public Doctor getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(Doctor reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public String getLabSource() {
        return labSource;
    }

    public void setLabSource(String labSource) {
        this.labSource = labSource;
    }

    public LabStatus getStatus() {
        return status;
    }

    public void setStatus(LabStatus status) {
        this.status = status;
    }

    public boolean isAbnormal() {
        return isAbnormal;
    }

    public void setAbnormal(boolean isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public String getDoctorNotes() {
        return doctorNotes;
    }

    public void setDoctorNotes(String doctorNotes) {
        this.doctorNotes = doctorNotes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<LabItem> getItems() {
        return items;
    }

    public void setItems(List<LabItem> items) {
        this.items = items;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
