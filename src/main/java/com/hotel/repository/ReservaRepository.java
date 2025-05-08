package com.hotel.repository;

import com.hotel.model.Reserva;
import com.hotel.model.Usuario;
import com.hotel.model.Habitacion;
import com.hotel.model.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuario(Usuario usuario);
    List<Reserva> findByHabitacion(Habitacion habitacion);
    List<Reserva> findByEstado(EstadoReserva estado);
    List<Reserva> findByUsuarioAndEstado(Usuario usuario, EstadoReserva estado);
    List<Reserva> findByHabitacionAndFechaEntradaBetween(
        Habitacion habitacion, 
        LocalDateTime fechaInicio, 
        LocalDateTime fechaFin
    );
    List<Reserva> findByFechaEntradaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<Reserva> findByHabitacionAndFechaSalidaAfter(Habitacion habitacion, LocalDateTime fecha);
    
    @Query("SELECT r FROM Reserva r WHERE r.habitacion.id = :habitacionId " +
           "AND r.estado != 'CANCELADA' " +
           "AND ((r.fechaEntrada BETWEEN :fechaInicio AND :fechaFin) " +
           "OR (r.fechaSalida BETWEEN :fechaInicio AND :fechaFin) " +
           "OR (:fechaInicio BETWEEN r.fechaEntrada AND r.fechaSalida))")
    List<Reserva> findOverlappingReservations(
        @Param("habitacionId") Long habitacionId,
        @Param("fechaInicio") LocalDateTime fechaInicio,
        @Param("fechaFin") LocalDateTime fechaFin
    );
} 