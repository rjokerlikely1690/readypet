package com.petspaready.controller;

import com.petspaready.model.Appointment;
import com.petspaready.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class AppointmentController {
    
    @Autowired
    private AppointmentService appointmentService;
    
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Appointment>>> getAllAppointments() {
        List<EntityModel<Appointment>> appointments = appointmentService.getAllAppointments().stream()
                .map(appointment -> EntityModel.of(appointment,
                        linkTo(methodOn(AppointmentController.class).getAppointmentById(appointment.getId())).withSelfRel(),
                        linkTo(methodOn(AppointmentController.class).getAllAppointments()).withRel("citas")))
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Appointment>> collectionModel = CollectionModel.of(appointments);
        collectionModel.add(linkTo(methodOn(AppointmentController.class).getAllAppointments()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Appointment>> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        EntityModel<Appointment> entityModel = EntityModel.of(appointment);
        entityModel.add(linkTo(methodOn(AppointmentController.class).getAppointmentById(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(AppointmentController.class).getAllAppointments()).withRel("citas"));
        
        return ResponseEntity.ok(entityModel);
    }
    
    @PostMapping
    public ResponseEntity<EntityModel<Appointment>> createAppointment(@Valid @RequestBody Appointment appointment) {
        Appointment createdAppointment = appointmentService.createAppointment(appointment);
        
        EntityModel<Appointment> entityModel = EntityModel.of(createdAppointment);
        entityModel.add(linkTo(methodOn(AppointmentController.class).getAppointmentById(createdAppointment.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(AppointmentController.class).getAllAppointments()).withRel("citas"));
        
        return ResponseEntity.ok(entityModel);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Appointment>> updateAppointment(@PathVariable Long id, @Valid @RequestBody Appointment appointment) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
        
        EntityModel<Appointment> entityModel = EntityModel.of(updatedAppointment);
        entityModel.add(linkTo(methodOn(AppointmentController.class).getAppointmentById(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(AppointmentController.class).getAllAppointments()).withRel("citas"));
        
        return ResponseEntity.ok(entityModel);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<CollectionModel<EntityModel<Appointment>>> getAppointmentsByUsuario(@PathVariable Long usuarioId) {
        List<EntityModel<Appointment>> appointments = appointmentService.getAppointmentsByUsuario(usuarioId).stream()
                .map(appointment -> EntityModel.of(appointment,
                        linkTo(methodOn(AppointmentController.class).getAppointmentById(appointment.getId())).withSelfRel(),
                        linkTo(methodOn(AppointmentController.class).getAllAppointments()).withRel("citas")))
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Appointment>> collectionModel = CollectionModel.of(appointments);
        collectionModel.add(linkTo(methodOn(AppointmentController.class).getAllAppointments()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }
    
    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<CollectionModel<EntityModel<Appointment>>> getAppointmentsByMascota(@PathVariable Long mascotaId) {
        List<EntityModel<Appointment>> appointments = appointmentService.getAppointmentsByMascota(mascotaId).stream()
                .map(appointment -> EntityModel.of(appointment,
                        linkTo(methodOn(AppointmentController.class).getAppointmentById(appointment.getId())).withSelfRel(),
                        linkTo(methodOn(AppointmentController.class).getAllAppointments()).withRel("citas")))
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Appointment>> collectionModel = CollectionModel.of(appointments);
        collectionModel.add(linkTo(methodOn(AppointmentController.class).getAllAppointments()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<CollectionModel<EntityModel<Appointment>>> getAppointmentsByEstado(@PathVariable Appointment.Estado estado) {
        List<EntityModel<Appointment>> appointments = appointmentService.getAppointmentsByEstado(estado).stream()
                .map(appointment -> EntityModel.of(appointment,
                        linkTo(methodOn(AppointmentController.class).getAppointmentById(appointment.getId())).withSelfRel(),
                        linkTo(methodOn(AppointmentController.class).getAllAppointments()).withRel("citas")))
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Appointment>> collectionModel = CollectionModel.of(appointments);
        collectionModel.add(linkTo(methodOn(AppointmentController.class).getAllAppointments()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }
    
    @PutMapping("/{id}/estado")
    public ResponseEntity<EntityModel<Appointment>> updateAppointmentStatus(@PathVariable Long id, @RequestParam Appointment.Estado estado) {
        Appointment updatedAppointment = appointmentService.updateAppointmentStatus(id, estado);
        
        EntityModel<Appointment> entityModel = EntityModel.of(updatedAppointment);
        entityModel.add(linkTo(methodOn(AppointmentController.class).getAppointmentById(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(AppointmentController.class).getAllAppointments()).withRel("citas"));
        
        return ResponseEntity.ok(entityModel);
    }
} 