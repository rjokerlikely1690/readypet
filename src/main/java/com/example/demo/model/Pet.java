package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "mascotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Pet extends RepresentationModel<Pet> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre de la mascota es obligatorio")
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    @Column(nullable = false)
    private String nombre;
    
    @NotBlank(message = "La especie es obligatoria")
    @Column(nullable = false)
    private String especie;
    
    @NotBlank(message = "La raza es obligatoria")
    @Column(nullable = false)
    private String raza;
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaNacimiento;
    
    @Column
    private String color;
    
    @Column
    private Double peso;
    
    @Column(length = 1000)
    private String observaciones;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> citas = new HashSet<>();
    
    @Column(nullable = false)
    private boolean activo = true;
} 