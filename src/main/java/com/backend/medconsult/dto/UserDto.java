package com.backend.medconsult.dto;

import java.util.UUID;

import com.backend.medconsult.dto.patientDto.PatientDto;
import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.enums.Role;

public class UserDto {
    private UUID id;
    private String fullName;
    private Role role;
    private UUID patientId;
    private String email;
    private String phone;
    private String language;
    private String profilePhoto;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public static UserDto fromEntity(User user) {
        UserDto dto = new UserDto();
        dto.setLanguage(user.getLanguage());
        dto.setProfilePhoto(user.getProfilePhotoUrl() != null
                ? user.getProfilePhotoUrl()
                : null);
        dto.setRole(user.getRole());
        dto.setPatientId(user.getPatient() != null
                ? user.getPatient().getPatientId()
                : null);

        dto.setId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        return dto;
    }
}
