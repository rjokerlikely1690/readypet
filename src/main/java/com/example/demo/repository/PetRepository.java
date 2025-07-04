package com.example.demo.repository;

import com.example.demo.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    
    List<Pet> findByUsuarioIdAndActivoTrue(Long usuarioId);
    
    List<Pet> findByActivoTrue();
    
    List<Pet> findByEspecieAndActivoTrue(String especie);
    
    @Query("SELECT p FROM Pet p WHERE p.usuario.id = :usuarioId AND p.activo = true")
    List<Pet> findMascotasByUsuario(@Param("usuarioId") Long usuarioId);
    
    @Query("SELECT p FROM Pet p WHERE p.especie = :especie AND p.activo = true")
    List<Pet> findMascotasByEspecie(@Param("especie") String especie);
} 