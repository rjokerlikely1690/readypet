package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    public LoginResponse login(LoginRequest loginRequest) {
        // Autenticar usuario
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        
        // Generar token
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        
        // Obtener usuario
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        
        return new LoginResponse(token, usuario);
    }
    
    public Usuario register(RegisterRequest registerRequest) {
        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        
        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(registerRequest.getNombre());
        usuario.setEmail(registerRequest.getEmail());
        usuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        usuario.setRol(Usuario.Rol.USER);
        usuario.setActivo(true);
        
        return usuarioRepository.save(usuario);
    }
} 