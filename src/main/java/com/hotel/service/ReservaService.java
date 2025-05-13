package com.hotel.service;

import com.hotel.model.Reserva;
import com.hotel.model.Usuario;
import com.hotel.model.Habitacion;
import com.hotel.model.EstadoReserva;
import com.hotel.model.Extra;
import com.hotel.repository.ReservaRepository;
import com.hotel.repository.UsuarioRepository;
import com.hotel.repository.HabitacionRepository;
import com.hotel.repository.ExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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

    @Autowired
    private ExtraRepository extraRepository;

    /**
     * Crea una nueva reserva.
     * Verifica la disponibilidad de la habitación y la validez de las fechas.
     *
     * @param reserva Datos de la reserva (incluyendo usuario, habitacion y reservaExtras)
     * @return La reserva creada
     * @throws RuntimeException si la habitación no está disponible o las fechas son inválidas
     */
    public Reserva crearReserva(Reserva reserva) {
        // Cargar usuario y habitación completos por ID
        Long usuarioId = reserva.getUsuario().getId();
        Long habitacionId = reserva.getHabitacion().getId();
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Habitacion habitacion = habitacionRepository.findById(habitacionId)
            .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        // Convertir LocalDateTime a la hora estándar de check-in/check-out si es necesario
        LocalDateTime fechaEntrada = reserva.getFechaEntrada();
        LocalDateTime fechaSalida = reserva.getFechaSalida();

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

        // Inicializar precio total con el precio de la habitación
        final Double precioInicial = habitacion.getPrecioPorNoche();
        final AtomicReference<Double> precioTotal = new AtomicReference<>(precioInicial);
        reserva.setPrecioTotal(precioInicial);

        // Asociar correctamente los ReservaExtra y calcular precio total
        if (reserva.getReservaExtras() != null) {
            reserva.getReservaExtras().forEach(re -> {
                re.setReserva(reserva);
                if (re.getExtra() != null && re.getExtra().getId() != null) {
                    Extra extra = extraRepository.findById(re.getExtra().getId())
                        .orElseThrow(() -> new RuntimeException("Extra no encontrado"));
                    re.setExtra(extra);
                    re.setPrecioUnitario(extra.getPrecio());
                    // Sumar al precio total: precio del extra * cantidad
                    precioTotal.updateAndGet(current -> current + (extra.getPrecio() * re.getCantidad()));
                }
            });
            reserva.setPrecioTotal(precioTotal.get());
        }

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
                
                if (reservaActualizada.getReservaExtras() != null) {
                    // Recalcular precio total con los nuevos extras
                    final Double precioInicial = reserva.getHabitacion().getPrecioPorNoche();
                    final AtomicReference<Double> precioTotal = new AtomicReference<>(precioInicial);
                    
                    reservaActualizada.getReservaExtras().forEach(re -> {
                        re.setReserva(reserva);
                        if (re.getExtra() != null && re.getExtra().getId() != null) {
                            Extra extra = extraRepository.findById(re.getExtra().getId())
                                .orElseThrow(() -> new RuntimeException("Extra no encontrado"));
                            re.setExtra(extra);
                            re.setPrecioUnitario(extra.getPrecio());
                            precioTotal.updateAndGet(current -> current + (extra.getPrecio() * re.getCantidad()));
                        }
                    });
                    reserva.setReservaExtras(reservaActualizada.getReservaExtras());
                    reserva.setPrecioTotal(precioTotal.get());
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

    /**
     * Devuelve una lista de fechas ocupadas (String ISO) para una habitación (excluyendo reservas canceladas).
     * @param habitacionId ID de la habitación
     * @return Lista de fechas ocupadas (String ISO yyyy-MM-dd)
     */
    public List<String> obtenerFechasOcupadasPorHabitacion(Long habitacionId) {
        Habitacion habitacion = habitacionRepository.findById(habitacionId)
            .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
        List<Reserva> reservas = reservaRepository.findByHabitacion(habitacion);
        List<String> fechasOcupadas = new java.util.ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getEstado() != EstadoReserva.CANCELADA) {
                LocalDate start = r.getFechaEntrada().toLocalDate();
                LocalDate end = r.getFechaSalida().toLocalDate();
                LocalDate current = start;
                while (!current.isAfter(end.minusDays(1))) {
                    fechasOcupadas.add(current.toString());
                    current = current.plusDays(1);
                }
            }
        }
        return fechasOcupadas;
    }
} 