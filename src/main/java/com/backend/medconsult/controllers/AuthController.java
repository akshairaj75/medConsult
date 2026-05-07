package com.backend.medconsult.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.medconsult.dto.AuthResponseDto;
import com.backend.medconsult.dto.UserLoginDto;
import com.backend.medconsult.dto.UserRegisterDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody UserRegisterDto dto) {
        AuthResponseDto registered = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserLoginDto request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(
                "Logged out");
    }

    @GetMapping("/me")
    public ResponseEntity<?> currentUser(
            @AuthenticationPrincipal CustomUserPrincipal principal) {

        return ResponseEntity.ok(Map.of(
                "userId", principal.getUserId(),
                "email", principal.getUsername(),
                "role", principal.getUser().getRole()));
    }
}
