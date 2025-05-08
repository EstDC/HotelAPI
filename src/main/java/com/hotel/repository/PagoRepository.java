package com.hotel.repository;

import com.hotel.model.Pago;
import com.hotel.model.Reserva;
import com.hotel.model.EstadoPago;
import com.hotel.model.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByReserva(Reserva reserva);
    List<Pago> findByEstado(EstadoPago estado);
    List<Pago> findByMetodoPago(MetodoPago metodoPago);
    List<Pago> findByFechaPagoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<Pago> findByReservaAndEstado(Reserva reserva, EstadoPago estado);
} 