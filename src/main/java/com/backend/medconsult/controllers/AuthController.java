package com.backend.medconsult.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.AuthResponseDto;
import com.backend.medconsult.dto.UserRegisterDto;
import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.service.impl.UserService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterDto> register(@RequestBody UserRegisterDto dto) {
        UserRegisterDto registered = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody User user) {
        return ResponseEntity.ok(userService.verify(user));
    }
    
}
