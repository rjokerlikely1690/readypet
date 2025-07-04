package com.example.demo.repository;

import com.example.demo.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    List<Appointment> findByUsuarioIdAndActivoTrue(Long usuarioId);
    
    List<Appointment> findByMascotaIdAndActivoTrue(Long mascotaId);
    
    List<Appointment> findByEstadoAndActivoTrue(Appointment.Estado estado);
    
    List<Appointment> findByFechaHoraBetweenAndActivoTrue(LocalDateTime inicio, LocalDateTime fin);
    
    List<Appointment> findByActivoTrue();
    
    @Query("SELECT a FROM Appointment a WHERE a.usuario.id = :usuarioId AND a.activo = true ORDER BY a.fechaHora DESC")
    List<Appointment> findCitasByUsuario(@Param("usuarioId") Long usuarioId);
    
    @Query("SELECT a FROM Appointment a WHERE a.mascota.id = :mascotaId AND a.activo = true ORDER BY a.fechaHora DESC")
    List<Appointment> findCitasByMascota(@Param("mascotaId") Long mascotaId);
    
    @Query("SELECT a FROM Appointment a WHERE a.estado = :estado AND a.activo = true ORDER BY a.fechaHora")
    List<Appointment> findCitasByEstado(@Param("estado") Appointment.Estado estado);
    
    @Query("SELECT a FROM Appointment a WHERE a.fechaHora >= :fechaInicio AND a.fechaHora <= :fechaFin AND a.activo = true ORDER BY a.fechaHora")
    List<Appointment> findCitasByRangoFechas(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                            @Param("fechaFin") LocalDateTime fechaFin);
} 