package com.backend.medconsult.security;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContext;

import com.backend.medconsult.enums.Role;

public class CustomUserPrincipal {

    private UUID userId;
    private String email;
    private Role role;

    // Getters and setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public CustomUserPrincipal(
            UUID userId,
            String email,
            Role role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
    }
}