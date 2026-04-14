package com.backend.medconsult.controllers;

import java.util.List;

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
import com.backend.medconsult.dto.UserDto;
import com.backend.medconsult.dto.UserLoginDto;
import com.backend.medconsult.dto.UserRegisterDto;
import com.backend.medconsult.service.impl.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterDto> register(@RequestBody UserRegisterDto dto) {
        UserRegisterDto registered = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserLoginDto request) {
        return ResponseEntity.ok(userService.verify(request));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers()
                .stream().map(UserDto::fromEntity)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/me")
    public String getCurrentUser(Authentication authentication) {
        return authentication.getName(); // returns email
    }

}
