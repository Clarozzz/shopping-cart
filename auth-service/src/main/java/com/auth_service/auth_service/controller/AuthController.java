package com.auth_service.auth_service.controller;

import com.auth_service.auth_service.dto.AuthResponseDto;
import com.auth_service.auth_service.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public record AuthRequest(String username, String password) {}

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponseDto register(@RequestBody AuthRequest req) {
        return authService.register(req.username(), req.password());
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequest req) {
        return authService.login(req.username(), req.password());
    }
}
