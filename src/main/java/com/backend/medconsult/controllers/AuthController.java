package com.backend.medconsult.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.medconsult.dto.AuthResponseDto;
import com.backend.medconsult.dto.UserLoginDto;
import com.backend.medconsult.dto.UserRegisterDto;
import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.repository.UserRepository;
import com.backend.medconsult.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

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

    // @GetMapping("/users")
    // public ResponseEntity<List<UserDto>> getUsers() {
    // List<UserDto> users = userService.getUsers()
    // .stream().map(UserDto::fromEntity)
    // .toList();
    // return ResponseEntity.ok(users);
    // }

    // @GetMapping("/me")
    // public String getCurrentUser(Authentication authentication) {
    //     return authentication.getName(); // returns email
    // }

    // @GetMapping("/patients")
    // public ResponseEntity<List<UserDto>> getPatients() {
    // List<UserDto> patients = userService.getPatients()
    // .stream().map(UserDto::fromEntity)
    // .toList();
    // return ResponseEntity.ok(patients);
    // }


    @GetMapping("/me")
    public ResponseEntity<?> currentUser(
            Authentication authentication) {

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            return ResponseEntity.status(401).body("Unauthorized");
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return ResponseEntity.ok(
                Map.of(
                        "userId", user.getUserId(),
                        "fullName", user.getFullName(),
                        "email", user.getEmail(),
                        "role", user.getRole(),
                        "provider", user.getAuthProvider(),
                        "authenticated", true
                )
        );
    }

}
