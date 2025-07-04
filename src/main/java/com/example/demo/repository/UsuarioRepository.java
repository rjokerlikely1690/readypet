package com.example.demo.repository;

import com.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<Usuario> findByActivoTrue();
    
    List<Usuario> findByRol(Usuario.Rol rol);
    
    @Query("SELECT u FROM Usuario u WHERE u.activo = true AND u.rol = :rol")
    List<Usuario> findActivosByRol(@Param("rol") Usuario.Rol rol);
    
    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.activo = true")
    Optional<Usuario> findActivoByEmail(@Param("email") String email);
} 