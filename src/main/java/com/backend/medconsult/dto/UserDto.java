package com.backend.medconsult.dto;

import java.util.UUID;

import com.backend.medconsult.entity.auth.User;

public class UserDto {
    private UUID id;
    private String fullName;
    private String email;
    private String phone;

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

    public static UserDto fromEntity(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        return dto;
    }
}
