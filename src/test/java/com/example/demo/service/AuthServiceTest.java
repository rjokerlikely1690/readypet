package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JwtUtil;
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
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private AuthService authService;

    private Usuario testUsuario;
    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        testUsuario = new Usuario();
        testUsuario.setId(1L);
        testUsuario.setNombre("Test User");
        testUsuario.setEmail("test@email.com");
        testUsuario.setPassword("encodedPassword");
        testUsuario.setRol(Usuario.Rol.USER);
        testUsuario.setActivo(true);

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@email.com");
        loginRequest.setPassword("password");

        registerRequest = new RegisterRequest();
        registerRequest.setNombre("New User");
        registerRequest.setEmail("new@email.com");
        registerRequest.setPassword("password");
    }

    @Test
    void testLogin_Success() {
        // Given
        when(usuarioRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(testUsuario));
        when(userDetailsService.loadUserByUsername(loginRequest.getEmail())).thenReturn(null);
        when(jwtUtil.generateToken(any())).thenReturn("testToken");

        // When
        var result = authService.login(loginRequest);

        // Then
        assertNotNull(result);
        assertEquals("testToken", result.getToken());
        assertEquals(testUsuario.getEmail(), result.getEmail());
        assertEquals(testUsuario.getNombre(), result.getNombre());
        assertEquals(testUsuario.getRol(), result.getRol());
        assertEquals(testUsuario.getId(), result.getUsuarioId());

        verify(authenticationManager).authenticate(any());
        verify(jwtUtil).generateToken(any());
    }

    @Test
    void testRegister_Success() {
        // Given
        when(usuarioRepository.existsByEmail(registerRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(testUsuario);

        // When
        Usuario result = authService.register(registerRequest);

        // Then
        assertNotNull(result);
        assertEquals(registerRequest.getNombre(), result.getNombre());
        assertEquals(registerRequest.getEmail(), result.getEmail());
        assertEquals(Usuario.Rol.USER, result.getRol());
        assertTrue(result.isActivo());

        verify(usuarioRepository).existsByEmail(registerRequest.getEmail());
        verify(passwordEncoder).encode(registerRequest.getPassword());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void testRegister_EmailAlreadyExists() {
        // Given
        when(usuarioRepository.existsByEmail(registerRequest.getEmail())).thenReturn(true);

        // When & Then
        assertThrows(RuntimeException.class, () -> authService.register(registerRequest));
        verify(usuarioRepository).existsByEmail(registerRequest.getEmail());
        verify(usuarioRepository, never()).save(any());
    }
} 