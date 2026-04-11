package com.backend.medconsult.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend.medconsult.dto.AuthResponseDto;
import com.backend.medconsult.dto.UserRegisterDto;
import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    public UserRegisterDto register(UserRegisterDto dto) {
        if (userRepository.findByEmail(dto.email).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email already exists");
        }

        User user = new User();
        user.setEmail(dto.email);
        user.setPasswordHash(passwordEncoder.encode(dto.password)); // encode here
        user.setFullName(dto.fullName);
        userRepository.save(user);
        return dto;

    }

    public AuthResponseDto verify(User user) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPasswordHash()));
            return new AuthResponseDto(
                    "dummy-token",
                    user.getEmail(),
                    0);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid username or password");
        }
    }

}
