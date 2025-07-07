package com.petspaready.controller;

import com.petspaready.dto.LoginRequest;
import com.petspaready.dto.LoginResponse;
import com.petspaready.dto.RegisterRequest;
import com.petspaready.model.Usuario;
import com.petspaready.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@Valid @RequestBody RegisterRequest registerRequest) {
        Usuario usuario = authService.register(registerRequest);
        return ResponseEntity.ok(usuario);
    }
} 