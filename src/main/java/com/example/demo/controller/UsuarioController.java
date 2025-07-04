package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
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
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> getAllUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.getAllUsuarios().stream()
                .map(usuario -> EntityModel.of(usuario,
                        linkTo(methodOn(UsuarioController.class).getUsuarioById(usuario.getId())).withSelfRel(),
                        linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("usuarios")))
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Usuario>> collectionModel = CollectionModel.of(usuarios);
        collectionModel.add(linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        EntityModel<Usuario> entityModel = EntityModel.of(usuario);
        entityModel.add(linkTo(methodOn(UsuarioController.class).getUsuarioById(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("usuarios"));
        
        return ResponseEntity.ok(entityModel);
    }
    
    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> createUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario createdUsuario = usuarioService.createUsuario(usuario);
        
        EntityModel<Usuario> entityModel = EntityModel.of(createdUsuario);
        entityModel.add(linkTo(methodOn(UsuarioController.class).getUsuarioById(createdUsuario.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("usuarios"));
        
        return ResponseEntity.ok(entityModel);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        Usuario updatedUsuario = usuarioService.updateUsuario(id, usuario);
        
        EntityModel<Usuario> entityModel = EntityModel.of(updatedUsuario);
        entityModel.add(linkTo(methodOn(UsuarioController.class).getUsuarioById(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("usuarios"));
        
        return ResponseEntity.ok(entityModel);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/rol/{rol}")
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> getUsuariosByRol(@PathVariable Usuario.Rol rol) {
        List<EntityModel<Usuario>> usuarios = usuarioService.getUsuariosByRol(rol).stream()
                .map(usuario -> EntityModel.of(usuario,
                        linkTo(methodOn(UsuarioController.class).getUsuarioById(usuario.getId())).withSelfRel(),
                        linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("usuarios")))
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Usuario>> collectionModel = CollectionModel.of(usuarios);
        collectionModel.add(linkTo(methodOn(UsuarioController.class).getUsuariosByRol(rol)).withSelfRel());
        collectionModel.add(linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("usuarios"));
        
        return ResponseEntity.ok(collectionModel);
    }
} 