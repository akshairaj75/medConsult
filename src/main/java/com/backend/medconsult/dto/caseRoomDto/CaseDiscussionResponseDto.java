package com.backend.medconsult.dto.caseRoomDto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.backend.medconsult.entity.caseDiscussion.CaseDiscussion;

public class CaseDiscussionResponseDto {

    private UUID discussionId;

    private UUID caseId;

    private UUID authorId;

    private String doctorName;

    private String content;

    private LocalDateTime createdAt;

    public UUID getDiscussionId() {
        return discussionId;
    }

    public UUID getCaseId() {
        return caseId;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static CaseDiscussionResponseDto fromEntity(
            CaseDiscussion discussion) {
        CaseDiscussionResponseDto dto = new CaseDiscussionResponseDto();
        dto.discussionId = discussion.getDiscussionId();
        dto.caseId = discussion.getCaseRoom().getCaseId();
        dto.authorId = discussion.getAuthor().getDoctorId();
        dto.doctorName = discussion.getAuthor().getUser().getFullName();
        dto.content = discussion.getContent();
        dto.createdAt = discussion.getCreatedAt();
        return dto;
    }

}
