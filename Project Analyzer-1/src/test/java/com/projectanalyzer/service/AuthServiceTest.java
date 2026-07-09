package com.projectanalyzer.service;

import com.projectanalyzer.dto.auth.AuthResponse;
import com.projectanalyzer.dto.auth.LoginRequest;
import com.projectanalyzer.dto.auth.RegisterRequest;
import com.projectanalyzer.entity.User;
import com.projectanalyzer.exception.BadRequestException;
import com.projectanalyzer.repository.UserRepository;
import com.projectanalyzer.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = User.builder()
                .id(1L)
                .name("Praveen")
                .email("praveen@example.com")
                .password("encoded_pass")
                .build();
    }

    @Test
    void register_Successful() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Praveen");
        request.setEmail("praveen@example.com");
        request.setPassword("password123");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_pass");
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);
        when(jwtService.generateToken(any())).thenReturn("mock_jwt_token");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("mock_jwt_token", response.getToken());
        assertEquals("praveen@example.com", response.getUser().getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_DuplicateEmail_ThrowsBadRequestException() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Praveen");
        request.setEmail("praveen@example.com");
        request.setPassword("password123");

        when(userRepository.existsByEmail("praveen@example.com")).thenReturn(true);

        assertThrows(BadRequestException.class, () -> authService.register(request));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_Successful() {
        LoginRequest request = new LoginRequest();
        request.setEmail("praveen@example.com");
        request.setPassword("password123");

        when(userRepository.findByEmail("praveen@example.com")).thenReturn(Optional.of(sampleUser));
        when(jwtService.generateToken(any())).thenReturn("mock_jwt_token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("mock_jwt_token", response.getToken());
        assertEquals("praveen@example.com", response.getUser().getEmail());
    }
}
