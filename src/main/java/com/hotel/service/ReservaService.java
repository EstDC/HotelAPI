package com.hotel.service;

import com.hotel.model.Reserva;
import com.hotel.model.Usuario;
import com.hotel.model.Habitacion;
import com.hotel.model.EstadoReserva;
import com.hotel.repository.ReservaRepository;
import com.hotel.repository.UsuarioRepository;
import com.hotel.repository.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar las reservas de habitaciones.
 * Este servicio maneja todas las operaciones relacionadas con las reservas,
 * incluyendo su creación, actualización, cancelación y consulta.
 */
@Service
@Transactional
public class ReservaService {
    
    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private HabitacionRepository habitacionRepository;

    /**
     * Crea una nueva reserva.
     * Verifica la disponibilidad de la habitación y la validez de las fechas.
     *
     * @param usuarioId ID del usuario que realiza la reserva
     * @param habitacionId ID de la habitación a reservar
     * @param reserva Datos de la reserva
     * @return La reserva creada
     * @throws RuntimeException si la habitación no está disponible o las fechas son inválidas
     */
    public Reserva crearReserva(Long usuarioId, Long habitacionId, Reserva reserva) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Habitacion habitacion = habitacionRepository.findById(habitacionId)
            .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
        
        // Convertir LocalDate a LocalDateTime
        LocalDateTime fechaEntrada = LocalDateTime.of(reserva.getFechaEntrada().toLocalDate(), LocalTime.of(14, 0));
        LocalDateTime fechaSalida = LocalDateTime.of(reserva.getFechaSalida().toLocalDate(), LocalTime.of(12, 0));
        
        // Verificar disponibilidad
        if (!verificarDisponibilidad(habitacion, fechaEntrada, fechaSalida)) {
            throw new RuntimeException("La habitación no está disponible para las fechas seleccionadas");
        }
        
        reserva.setUsuario(usuario);
        reserva.setHabitacion(habitacion);
        reserva.setEstado(EstadoReserva.PENDIENTE);
        reserva.setFechaCreacion(LocalDateTime.now());
        reserva.setFechaEntrada(fechaEntrada);
        reserva.setFechaSalida(fechaSalida);
        
        return reservaRepository.save(reserva);
    }

    /**
     * Actualiza una reserva existente.
     * Solo permite actualizar ciertos campos y verifica la disponibilidad si se cambian las fechas.
     *
     * @param id ID de la reserva a actualizar
     * @param reservaActualizada Datos actualizados de la reserva
     * @return La reserva actualizada
     * @throws RuntimeException si la reserva no existe o no se puede actualizar
     */
    public Reserva actualizarReserva(Long id, Reserva reservaActualizada) {
        return reservaRepository.findById(id)
            .map(reserva -> {
                if (reservaActualizada.getFechaEntrada() != null && 
                    reservaActualizada.getFechaSalida() != null) {
                    
                    LocalDateTime fechaEntrada = LocalDateTime.of(reservaActualizada.getFechaEntrada().toLocalDate(), LocalTime.of(14, 0));
                    LocalDateTime fechaSalida = LocalDateTime.of(reservaActualizada.getFechaSalida().toLocalDate(), LocalTime.of(12, 0));
                    
                    if (!verificarDisponibilidad(reserva.getHabitacion(), fechaEntrada, fechaSalida)) {
                        throw new RuntimeException("La habitación no está disponible para las nuevas fechas");
                    }
                    reserva.setFechaEntrada(fechaEntrada);
                    reserva.setFechaSalida(fechaSalida);
                }
                
                if (reservaActualizada.getNumeroHuespedes() != null) {
                    reserva.setNumeroHuespedes(reservaActualizada.getNumeroHuespedes());
                }
                
                if (reservaActualizada.getExtras() != null) {
                    reserva.setExtras(reservaActualizada.getExtras());
                }
                
                if (reservaActualizada.getPrecioTotal() != null) {
                    reserva.setPrecioTotal(reservaActualizada.getPrecioTotal());
                }
                
                reserva.setFechaActualizacion(LocalDateTime.now());
                return reservaRepository.save(reserva);
            })
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }

    /**
     * Cancela una reserva existente.
     * Solo permite cancelar reservas en estado PENDIENTE o CONFIRMADA.
     *
     * @param id ID de la reserva a cancelar
     * @throws RuntimeException si la reserva no existe o no se puede cancelar
     */
    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        if (reserva.getEstado() != EstadoReserva.PENDIENTE && 
            reserva.getEstado() != EstadoReserva.CONFIRMADA) {
            throw new RuntimeException("No se puede cancelar una reserva en estado " + reserva.getEstado());
        }
        
        reserva.setEstado(EstadoReserva.CANCELADA);
        reserva.setFechaActualizacion(LocalDateTime.now());
        reservaRepository.save(reserva);
    }

    /**
     * Obtiene una reserva por su ID.
     *
     * @param id ID de la reserva
     * @return La reserva encontrada
     */
    public Optional<Reserva> obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id);
    }

    /**
     * Obtiene todas las reservas de un usuario.
     *
     * @param usuarioId ID del usuario
     * @return Lista de reservas del usuario
     */
    public List<Reserva> obtenerReservasPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return reservaRepository.findByUsuario(usuario);
    }

    /**
     * Obtiene todas las reservas de una habitación.
     *
     * @param habitacionId ID de la habitación
     * @return Lista de reservas de la habitación
     */
    public List<Reserva> obtenerReservasPorHabitacion(Long habitacionId) {
        Habitacion habitacion = habitacionRepository.findById(habitacionId)
            .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
        return reservaRepository.findByHabitacion(habitacion);
    }

    /**
     * Verifica la disponibilidad de una habitación para un rango de fechas.
     *
     * @param habitacion Habitación a verificar
     * @param fechaEntrada Fecha de entrada
     * @param fechaSalida Fecha de salida
     * @return true si la habitación está disponible, false en caso contrario
     */
    private boolean verificarDisponibilidad(Habitacion habitacion, LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
        List<Reserva> reservasExistentes = reservaRepository.findByHabitacionAndFechaEntradaBetween(
            habitacion, fechaEntrada, fechaSalida);
        
        return reservasExistentes.stream()
            .noneMatch(reserva -> 
                reserva.getEstado() != EstadoReserva.CANCELADA &&
                ((fechaEntrada.isAfter(reserva.getFechaEntrada()) && 
                  fechaEntrada.isBefore(reserva.getFechaSalida())) ||
                 (fechaSalida.isAfter(reserva.getFechaEntrada()) && 
                  fechaSalida.isBefore(reserva.getFechaSalida())) ||
                 (fechaEntrada.isBefore(reserva.getFechaEntrada()) && 
                  fechaSalida.isAfter(reserva.getFechaSalida()))));
    }

    public List<Reserva> buscarReservasPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        LocalDateTime fechaInicioDateTime = LocalDateTime.of(fechaInicio, LocalTime.of(0, 0));
        LocalDateTime fechaFinDateTime = LocalDateTime.of(fechaFin, LocalTime.of(23, 59, 59));
        return reservaRepository.findByFechaEntradaBetween(fechaInicioDateTime, fechaFinDateTime);
    }

    public List<Reserva> buscarReservasPorEstado(String estado) {
        return reservaRepository.findByEstado(EstadoReserva.valueOf(estado.toUpperCase()));
    }
} 