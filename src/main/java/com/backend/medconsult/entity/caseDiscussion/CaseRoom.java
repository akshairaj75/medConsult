package com.backend.medconsult.entity.caseDiscussion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.backend.medconsult.entity.people.Doctor;
import com.backend.medconsult.entity.people.Patient;
import com.backend.medconsult.enums.CaseStatus;
import com.backend.medconsult.enums.Priority;

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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "case_rooms", uniqueConstraints = {
                @UniqueConstraint(columnNames = "case_code")
}, indexes = {
                @Index(name = "idx_case_patient", columnList = "patient_id"),
                @Index(name = "idx_case_created_by", columnList = "created_by")
})
public class CaseRoom {
        @Id
        @GeneratedValue
        @UuidGenerator
        @JdbcTypeCode(SqlTypes.CHAR)
        @Column(name = "case_id", nullable = false, updatable = false, length=36)
        private UUID caseId;

        @Column(name = "case_code", nullable = false, length = 20, unique = true)
        private String caseCode;

        // FK → patients(patient_id)
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "patient_id", nullable = false)
        private Patient patient;

        // FK → doctors(doctor_id)
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "created_by", nullable = false)
        private Doctor createdBy;

        @Column(nullable = false, length = 100)
        private String specialty;

        @Column(nullable = false, length = 255)
        private String title;

        @Column(columnDefinition = "TEXT")
        private String description;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private CaseStatus status = CaseStatus.OPEN;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Priority priority = Priority.NORMAL;

        // JSON array
        @Column(name = "pending_actions", columnDefinition = "JSON")
        private List<String> pendingActions;

        @Column(name = "closed_at")
        private LocalDateTime closedAt;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;

        @OneToMany(mappedBy = "caseRoom", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<CaseRoomMember> members;

        @OneToMany(mappedBy = "caseRoom")
        private List<CaseDiscussion> discussions;

        @ManyToMany
        @JoinTable(name = "case_room_members", joinColumns = @JoinColumn(name = "case_id"), inverseJoinColumns = @JoinColumn(name = "doctor_id"))
        private List<Doctor> doctors;

        public UUID getCaseId() {
                return caseId;
        }

        public void setCaseId(UUID caseId) {
                this.caseId = caseId;
        }

        public String getCaseCode() {
                return caseCode;
        }

        public void setCaseCode(String caseCode) {
                this.caseCode = caseCode;
        }

        public Patient getPatient() {
                return patient;
        }

        public void setPatient(Patient patient) {
                this.patient = patient;
        }

        public Doctor getCreatedBy() {
                return createdBy;
        }

        public void setCreatedBy(Doctor createdBy) {
                this.createdBy = createdBy;
        }

        public String getSpecialty() {
                return specialty;
        }

        public void setSpecialty(String specialty) {
                this.specialty = specialty;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public CaseStatus getStatus() {
                return status;
        }

        public void setStatus(CaseStatus status) {
                this.status = status;
        }

        public Priority getPriority() {
                return priority;
        }

        public void setPriority(Priority priority) {
                this.priority = priority;
        }

        public List<String> getPendingActions() {
                return pendingActions;
        }

        public void setPendingActions(List<String> pendingActions) {
                this.pendingActions = pendingActions;
        }

        public LocalDateTime getClosedAt() {
                return closedAt;
        }

        public void setClosedAt(LocalDateTime closedAt) {
                this.closedAt = closedAt;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }

        public LocalDateTime getUpdatedAt() {
                return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
                this.updatedAt = updatedAt;
        }

        public List<CaseRoomMember> getMembers() {
                return members;
        }

        public void setMembers(List<CaseRoomMember> members) {
                this.members = members;
        }

        public List<CaseDiscussion> getDiscussions() {
                return discussions;
        }

        public void setDiscussions(List<CaseDiscussion> discussions) {
                this.discussions = discussions;
        }

        public List<Doctor> getDoctors() {
                return doctors;
        }

        public void setDoctors(List<Doctor> doctors) {
                this.doctors = doctors;
        }

}
