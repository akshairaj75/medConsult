package com.backend.medconsult.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.medconsult.dto.authDto.AuthResponseDto;
import com.backend.medconsult.dto.authDto.UserDto;
import com.backend.medconsult.dto.authDto.UserLoginDto;
import com.backend.medconsult.dto.authDto.UserRegisterDto;
import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.repository.UserRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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
        return ResponseEntity.ok("Logged out");
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> currentUser(@AuthenticationPrincipal CustomUserPrincipal principal) {

        User user = userRepository.findById(principal.getUserId()).orElseThrow(() -> new RuntimeException("user not found"));

        return ResponseEntity.ok(UserDto.fromEntity(user));

    }
}
