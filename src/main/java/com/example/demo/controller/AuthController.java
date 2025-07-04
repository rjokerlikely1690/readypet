package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Usuario;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<EntityModel<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        
        EntityModel<LoginResponse> entityModel = EntityModel.of(response);
        entityModel.add(linkTo(methodOn(AuthController.class).login(loginRequest)).withSelfRel());
        entityModel.add(linkTo(methodOn(AuthController.class).register(null)).withRel("register"));
        
        return ResponseEntity.ok(entityModel);
    }
    
    @PostMapping("/register")
    public ResponseEntity<EntityModel<Usuario>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        Usuario usuario = authService.register(registerRequest);
        
        EntityModel<Usuario> entityModel = EntityModel.of(usuario);
        entityModel.add(linkTo(methodOn(AuthController.class).register(registerRequest)).withSelfRel());
        entityModel.add(linkTo(methodOn(AuthController.class).login(null)).withRel("login"));
        
        return ResponseEntity.ok(entityModel);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth service is running");
    }
} 