package com.petspaready.repository;

import com.petspaready.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    
    List<Pet> findByActivoTrue();
    
    List<Pet> findByUsuarioIdAndActivoTrue(Long usuarioId);
    
    List<Pet> findByEspecieAndActivoTrue(String especie);
} 