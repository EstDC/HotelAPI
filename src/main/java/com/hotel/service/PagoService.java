package com.hotel.service;

import com.hotel.model.Pago;
import com.hotel.model.Reserva;
import com.hotel.model.EstadoPago;
import com.hotel.model.MetodoPago;
import com.hotel.repository.PagoRepository;
import com.hotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Servicio para gestionar los pagos del sistema.
 * Este servicio maneja todas las operaciones relacionadas con los pagos,
 * incluyendo su procesamiento, confirmación, reembolso y consulta.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@Service
@Transactional
public class PagoService {
    
    @Autowired
    private PagoRepository pagoRepository;
    
    @Autowired
    private ReservaRepository reservaRepository;

    /**
     * Procesa un nuevo pago para una reserva.
     * Crea un registro de pago y lo asocia con la reserva correspondiente.
     *
     * @param pago El pago a procesar
     * @return El pago procesado
     * @throws RuntimeException si la reserva no existe o el pago no puede ser procesado
     */
    public Pago procesarPago(Pago pago) {
        Reserva reserva = reservaRepository.findById(pago.getReserva().getId())
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        pago.setReserva(reserva);
        pago.setEstado(EstadoPago.PENDIENTE);
        pago.setReferenciaPago(generarReferenciaPago());
        pago.setFechaPago(LocalDateTime.now());
        
        return pagoRepository.save(pago);
    }

    /**
     * Confirma un pago existente.
     * Actualiza el estado del pago a COMPLETADO y registra la fecha de confirmación.
     *
     * @param pagoId ID del pago a confirmar
     * @return El pago confirmado
     * @throws RuntimeException si el pago no existe o no puede ser confirmado
     */
    public Pago confirmarPago(Long pagoId) {
        return pagoRepository.findById(pagoId)
            .map(pago -> {
                if (pago.getEstado() != EstadoPago.PENDIENTE) {
                    throw new RuntimeException("El pago no está en estado pendiente");
                }
                pago.setEstado(EstadoPago.COMPLETADO);
                pago.setFechaPago(LocalDateTime.now());
                return pagoRepository.save(pago);
            })
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }

    /**
     * Procesa un reembolso para un pago existente.
     * Actualiza el estado del pago a REEMBOLSADO y registra la fecha del reembolso.
     *
     * @param pagoId ID del pago a reembolsar
     * @return El pago reembolsado
     * @throws RuntimeException si el pago no existe o no puede ser reembolsado
     */
    public Pago procesarReembolso(Long pagoId) {
        return pagoRepository.findById(pagoId)
            .map(pago -> {
                if (pago.getEstado() != EstadoPago.COMPLETADO) {
                    throw new RuntimeException("Solo se pueden reembolsar pagos completados");
                }
                pago.setEstado(EstadoPago.REEMBOLSADO);
                pago.setObservaciones("Reembolso solicitado por el cliente");
                return pagoRepository.save(pago);
            })
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }

    /**
     * Obtiene un pago por su ID.
     *
     * @param id ID del pago
     * @return El pago encontrado
     */
    public Optional<Pago> obtenerPagoPorId(Long id) {
        return pagoRepository.findById(id);
    }

    /**
     * Obtiene todos los pagos de una reserva.
     *
     * @param reservaId ID de la reserva
     * @return Lista de pagos de la reserva
     */
    public List<Pago> obtenerPagosPorReserva(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        return pagoRepository.findByReserva(reserva);
    }

    /**
     * Obtiene todos los pagos por estado.
     *
     * @param estado Estado de los pagos a buscar
     * @return Lista de pagos con el estado especificado
     */
    public List<Pago> obtenerPagosPorEstado(String estado) {
        return pagoRepository.findByEstado(EstadoPago.valueOf(estado));
    }

    /**
     * Obtiene todos los pagos por método de pago.
     *
     * @param metodoPago Método de pago a buscar
     * @return Lista de pagos con el método de pago especificado
     */
    public List<Pago> obtenerPagosPorMetodo(String metodoPago) {
        return pagoRepository.findByMetodoPago(MetodoPago.valueOf(metodoPago));
    }

    /**
     * Obtiene todos los pagos realizados en un rango de fechas.
     *
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Lista de pagos realizados en el rango de fechas
     */
    public List<Pago> obtenerPagosPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return pagoRepository.findByFechaPagoBetween(fechaInicio, fechaFin);
    }

    /**
     * Genera una referencia única para el pago.
     *
     * @return Referencia única generada
     */
    private String generarReferenciaPago() {
        return "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
} 