package com.example.demo.dto;

import com.example.demo.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    
    private String token;
    private String email;
    private String nombre;
    private Usuario.Rol rol;
    private Long usuarioId;
    
    public LoginResponse(String token, Usuario usuario) {
        this.token = token;
        this.email = usuario.getEmail();
        this.nombre = usuario.getNombre();
        this.rol = usuario.getRol();
        this.usuarioId = usuario.getId();
    }
} 