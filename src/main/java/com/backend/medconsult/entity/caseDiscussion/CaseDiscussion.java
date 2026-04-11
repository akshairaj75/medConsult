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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "case_discussions", indexes = {
        @Index(name = "idx_case_discussions_case", columnList = "case_id, created_at")
})
public class CaseDiscussion {
    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "discussion_id", nullable = false, updatable = false, length= 36)
    private UUID discussionId;

    // FK → case_rooms(case_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    private CaseRoom caseRoom;

    // FK → doctors(doctor_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Doctor author;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // JSON field
    @Column(columnDefinition = "JSON")
    private List<String> tags;

    @Column(name = "file_url", length = 512)
    private String fileUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public UUID getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(UUID discussionId) {
        this.discussionId = discussionId;
    }

    public CaseRoom getCaseRoom() {
        return caseRoom;
    }

    public void setCaseRoom(CaseRoom caseRoom) {
        this.caseRoom = caseRoom;
    }

    public Doctor getAuthor() {
        return author;
    }

    public void setAuthor(Doctor author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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

}
