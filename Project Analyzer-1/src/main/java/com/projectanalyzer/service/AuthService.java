package com.projectanalyzer.service;

import com.projectanalyzer.dto.auth.*;
import com.projectanalyzer.entity.User;
import com.projectanalyzer.exception.BadRequestException;
import com.projectanalyzer.exception.ResourceNotFoundException;
import com.projectanalyzer.repository.UserRepository;
import com.projectanalyzer.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail().toLowerCase().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                savedUser.getEmail(),
                savedUser.getPassword(),
                Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + savedUser.getRole().name()))
        );

        String jwtToken = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(jwtToken)
                .user(mapToUserDto(savedUser))
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail().toLowerCase().trim(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail().toLowerCase().trim())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );

        String jwtToken = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(jwtToken)
                .user(mapToUserDto(user))
                .build();
    }

    public UserDto getCurrentUserDto() {
        User user = getCurrentAuthenticatedUser();
        return mapToUserDto(user);
    }

    public User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new com.projectanalyzer.exception.UnauthorizedException("User is not authenticated");
        }

        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found in database"));
    }

    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
