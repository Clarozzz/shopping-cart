package com.auth_service.auth_service.service;

import com.auth_service.auth_service.config.JwtService;
import com.auth_service.auth_service.dto.AuthResponseDto;
import com.auth_service.auth_service.entity.User;
import com.auth_service.auth_service.exception.UserAlreadyExistsException;
import com.auth_service.auth_service.exception.UserNotFoundException;
import com.auth_service.auth_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder encoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDto register(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = User.builder()
                .username(username)
                .password(encoder.encode(password))
                .build();

        userRepository.save(user);

        return new AuthResponseDto(jwtService.generateToken(username));
    }

    public AuthResponseDto login(String username, String password) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Incorrect credentials");
        }

        return new AuthResponseDto(jwtService.generateToken(username));
    }
}
