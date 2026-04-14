package com.backend.medconsult.dto;

public class AuthResponseDto {
    private String email;

    public AuthResponseDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
