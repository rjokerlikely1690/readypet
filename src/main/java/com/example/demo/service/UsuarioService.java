package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findByActivoTrue();
    }
    
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public Usuario updateUsuario(Long id, Usuario usuarioDetails) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        usuario.setNombre(usuarioDetails.getNombre());
        usuario.setEmail(usuarioDetails.getEmail());
        usuario.setRol(usuarioDetails.getRol());
        usuario.setActivo(usuarioDetails.isActivo());
        
        return usuarioRepository.save(usuario);
    }
    
    public void deleteUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
    
    public List<Usuario> getUsuariosByRol(Usuario.Rol rol) {
        return usuarioRepository.findActivosByRol(rol);
    }
} 