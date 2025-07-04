package com.example.demo.controller;

import com.example.demo.model.Pet;
import com.example.demo.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "*")
public class PetController {
    
    @Autowired
    private PetService petService;
    
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Pet>>> getAllPets() {
        List<EntityModel<Pet>> pets = petService.getAllPets().stream()
                .map(pet -> EntityModel.of(pet,
                        linkTo(methodOn(PetController.class).getPetById(pet.getId())).withSelfRel(),
                        linkTo(methodOn(PetController.class).getAllPets()).withRel("mascotas")))
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Pet>> collectionModel = CollectionModel.of(pets);
        collectionModel.add(linkTo(methodOn(PetController.class).getAllPets()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pet>> getPetById(@PathVariable Long id) {
        Pet pet = petService.getPetById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        
        EntityModel<Pet> entityModel = EntityModel.of(pet);
        entityModel.add(linkTo(methodOn(PetController.class).getPetById(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(PetController.class).getAllPets()).withRel("mascotas"));
        
        return ResponseEntity.ok(entityModel);
    }
    
    @PostMapping
    public ResponseEntity<EntityModel<Pet>> createPet(@Valid @RequestBody Pet pet) {
        Pet createdPet = petService.createPet(pet);
        
        EntityModel<Pet> entityModel = EntityModel.of(createdPet);
        entityModel.add(linkTo(methodOn(PetController.class).getPetById(createdPet.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(PetController.class).getAllPets()).withRel("mascotas"));
        
        return ResponseEntity.ok(entityModel);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Pet>> updatePet(@PathVariable Long id, @Valid @RequestBody Pet pet) {
        Pet updatedPet = petService.updatePet(id, pet);
        
        EntityModel<Pet> entityModel = EntityModel.of(updatedPet);
        entityModel.add(linkTo(methodOn(PetController.class).getPetById(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(PetController.class).getAllPets()).withRel("mascotas"));
        
        return ResponseEntity.ok(entityModel);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<CollectionModel<EntityModel<Pet>>> getPetsByUsuario(@PathVariable Long usuarioId) {
        List<EntityModel<Pet>> pets = petService.getPetsByUsuario(usuarioId).stream()
                .map(pet -> EntityModel.of(pet,
                        linkTo(methodOn(PetController.class).getPetById(pet.getId())).withSelfRel(),
                        linkTo(methodOn(PetController.class).getAllPets()).withRel("mascotas")))
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Pet>> collectionModel = CollectionModel.of(pets);
        collectionModel.add(linkTo(methodOn(PetController.class).getPetsByUsuario(usuarioId)).withSelfRel());
        collectionModel.add(linkTo(methodOn(PetController.class).getAllPets()).withRel("mascotas"));
        
        return ResponseEntity.ok(collectionModel);
    }
    
    @GetMapping("/especie/{especie}")
    public ResponseEntity<CollectionModel<EntityModel<Pet>>> getPetsByEspecie(@PathVariable String especie) {
        List<EntityModel<Pet>> pets = petService.getPetsByEspecie(especie).stream()
                .map(pet -> EntityModel.of(pet,
                        linkTo(methodOn(PetController.class).getPetById(pet.getId())).withSelfRel(),
                        linkTo(methodOn(PetController.class).getAllPets()).withRel("mascotas")))
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Pet>> collectionModel = CollectionModel.of(pets);
        collectionModel.add(linkTo(methodOn(PetController.class).getPetsByEspecie(especie)).withSelfRel());
        collectionModel.add(linkTo(methodOn(PetController.class).getAllPets()).withRel("mascotas"));
        
        return ResponseEntity.ok(collectionModel);
    }
} 