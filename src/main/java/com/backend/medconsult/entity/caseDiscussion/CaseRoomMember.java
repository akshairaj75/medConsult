package com.backend.medconsult.entity.caseDiscussion;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.backend.medconsult.entity.people.Doctor;
import com.backend.medconsult.enums.CaseMemberRole;

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
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "case_room_members", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "case_id", "doctor_id" })
}, indexes = {
        @Index(name = "idx_case_member_case", columnList = "case_id"),
        @Index(name = "idx_case_member_doctor", columnList = "doctor_id")
})
public class CaseRoomMember {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)

    @Column(name = "member_id", nullable = false, updatable = false, length=36
)
    private UUID memberId;

    // FK → case_rooms(case_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    private CaseRoom caseRoom;

    // FK → doctors(doctor_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CaseMemberRole role = CaseMemberRole.COLLABORATOR;

    @CreationTimestamp
    @Column(name = "joined_at", nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    public UUID getMemberId() {
        return memberId;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
    }

    public CaseRoom getCaseRoom() {
        return caseRoom;
    }

    public void setCaseRoom(CaseRoom caseRoom) {
        this.caseRoom = caseRoom;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public CaseMemberRole getRole() {
        return role;
    }

    public void setRole(CaseMemberRole role) {
        this.role = role;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

}
