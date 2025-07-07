package com.petspaready.service;

import com.petspaready.model.Appointment;
import com.petspaready.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findByActivoTrue();
    }
    
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }
    
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
    
    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        appointment.setFechaHora(appointmentDetails.getFechaHora());
        appointment.setTipoServicio(appointmentDetails.getTipoServicio());
        appointment.setObservaciones(appointmentDetails.getObservaciones());
        appointment.setEstado(appointmentDetails.getEstado());
        
        return appointmentRepository.save(appointment);
    }
    
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        appointment.setActivo(false);
        appointmentRepository.save(appointment);
    }
    
    public List<Appointment> getAppointmentsByUsuario(Long usuarioId) {
        return appointmentRepository.findByUsuarioIdAndActivoTrue(usuarioId);
    }
    
    public List<Appointment> getAppointmentsByMascota(Long mascotaId) {
        return appointmentRepository.findByMascotaIdAndActivoTrue(mascotaId);
    }
    
    public List<Appointment> getAppointmentsByEstado(Appointment.Estado estado) {
        return appointmentRepository.findByEstadoAndActivoTrue(estado);
    }
    
    public List<Appointment> getAppointmentsByDateRange(LocalDateTime inicio, LocalDateTime fin) {
        return appointmentRepository.findByFechaHoraBetweenAndActivoTrue(inicio, fin);
    }
    
    public Appointment updateAppointmentStatus(Long id, Appointment.Estado estado) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        appointment.setEstado(estado);
        return appointmentRepository.save(appointment);
    }
} 