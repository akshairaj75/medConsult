package com.backend.medconsult.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.medconsult.dto.authDto.AuthResponseDto;
import com.backend.medconsult.dto.authDto.UserLoginDto;
import com.backend.medconsult.dto.authDto.UserRegisterDto;
import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.enums.AuthProvider;
import com.backend.medconsult.repository.UserRepository;
import com.backend.medconsult.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public AuthResponseDto register(UserRegisterDto dto) {

        String fullName;
        if (dto.getLastName() != null && !dto.getLastName().trim().isEmpty()) {
            fullName = dto.getFirstName() + " " + dto.getLastName();
        } else {
            fullName = dto.getFirstName();
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(dto.getEmail());
        user.setPasswordHash(
                passwordEncoder.encode(
                        dto.getPassword()));

        user.setAuthProvider(
                AuthProvider.LOCAL);

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponseDto.authResponseDto(user, token);
    }

    @Override
    public AuthResponseDto login(UserLoginDto dto) {
        User user = userRepository.findByEmail(
                dto.getEmail()).orElseThrow();

        if (user.getAuthProvider() != AuthProvider.LOCAL) {
            throw new RuntimeException(
                    "Use Google login");
        }

        boolean matches = passwordEncoder.matches(
                dto.getPassword(),
                user.getPasswordHash());

        if (!matches) {
            throw new RuntimeException(
                    "Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return AuthResponseDto.authResponseDto(user, token);

    }

}
