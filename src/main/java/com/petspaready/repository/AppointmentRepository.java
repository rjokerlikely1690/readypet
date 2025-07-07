package com.petspaready.repository;

import com.petspaready.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    List<Appointment> findByActivoTrue();
    
    List<Appointment> findByUsuarioIdAndActivoTrue(Long usuarioId);
    
    List<Appointment> findByMascotaIdAndActivoTrue(Long mascotaId);
    
    List<Appointment> findByEstadoAndActivoTrue(Appointment.Estado estado);
    
    List<Appointment> findByFechaHoraBetweenAndActivoTrue(LocalDateTime inicio, LocalDateTime fin);
} 