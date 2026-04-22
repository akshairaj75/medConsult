package com.backend.medconsult.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend.medconsult.dto.AuthResponseDto;
import com.backend.medconsult.dto.UserLoginDto;
import com.backend.medconsult.dto.UserRegisterDto;
import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.enums.Role;
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
        String fullName;
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword())); // encode here

        if (dto.getLastName() != null && !dto.getLastName().trim().isEmpty()) {
            fullName = dto.getFirstName() + " " + dto.getLastName();
        } else {
            fullName = dto.getFirstName();
        }
        user.setFullName(fullName);
        userRepository.save(user);
        return dto;

    }

    // @Service
    // public class CustomUserDetailsService implements UserDetailsService {
    // @Override
    // public UserDetails loadUserByUsername(String username) {
    // // fetch from DB
    // }
    // }

    public AuthResponseDto verify(UserLoginDto request) {
        try {
            Authentication auth = authManager.authenticate(

                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(auth);

            return new AuthResponseDto(request.getEmail());

        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid username or password");
        }
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<User> getPatients() {
        return userRepository.findByRole(Role.PATIENT);
    }

}
