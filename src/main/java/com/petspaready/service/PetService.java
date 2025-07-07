package com.petspaready.service;

import com.petspaready.model.Pet;
import com.petspaready.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    
    @Autowired
    private PetRepository petRepository;
    
    public List<Pet> getAllPets() {
        return petRepository.findByActivoTrue();
    }
    
    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);
    }
    
    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }
    
    public Pet updatePet(Long id, Pet petDetails) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        
        pet.setNombre(petDetails.getNombre());
        pet.setEspecie(petDetails.getEspecie());
        pet.setRaza(petDetails.getRaza());
        pet.setFechaNacimiento(petDetails.getFechaNacimiento());
        pet.setColor(petDetails.getColor());
        pet.setPeso(petDetails.getPeso());
        pet.setObservaciones(petDetails.getObservaciones());
        
        return petRepository.save(pet);
    }
    
    public void deletePet(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        pet.setActivo(false);
        petRepository.save(pet);
    }
    
    public List<Pet> getPetsByUsuario(Long usuarioId) {
        return petRepository.findByUsuarioIdAndActivoTrue(usuarioId);
    }
    
    public List<Pet> getPetsByEspecie(String especie) {
        return petRepository.findByEspecieAndActivoTrue(especie);
    }
} 