package com.backend.medconsult.entity.privacy;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.entity.people.Patient;
import com.backend.medconsult.enums.AccessAction;

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
import jakarta.persistence.Table;

@Entity
@Table(name = "access_logs",
       indexes = {
           @Index(name = "idx_access_patient", columnList = "patient_id"),
           @Index(name = "idx_access_user", columnList = "accessed_by"),
           @Index(name = "idx_access_time", columnList = "accessed_at")
       }
)
public class AccessLog {
    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)

    @Column(name = "log_id", nullable = false, updatable = false, length=36)
    private UUID logId;

    // FK → patients(patient_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // FK → users(user_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accessed_by", nullable = false)
    private User accessedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccessAction action;

    @Column(name = "resource_type", nullable = false, length = 60)
    private String resourceType;

    @Column(name = "resource_id", columnDefinition = "CHAR(36)")
    private UUID resourceId;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @CreationTimestamp
    @Column(name = "accessed_at", nullable = false, updatable = false)
    private LocalDateTime accessedAt;

    public UUID getLogId() {
        return logId;
    }

    public void setLogId(UUID logId) {
        this.logId = logId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public User getAccessedBy() {
        return accessedBy;
    }

    public void setAccessedBy(User accessedBy) {
        this.accessedBy = accessedBy;
    }

    public AccessAction getAction() {
        return action;
    }

    public void setAction(AccessAction action) {
        this.action = action;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public void setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public LocalDateTime getAccessedAt() {
        return accessedAt;
    }

    public void setAccessedAt(LocalDateTime accessedAt) {
        this.accessedAt = accessedAt;
    }

}
