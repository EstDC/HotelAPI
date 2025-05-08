package com.hotel.service;

import com.hotel.model.Habitacion;
import com.hotel.model.Hotel;
import com.hotel.model.Reserva;
import com.hotel.repository.HabitacionRepository;
import com.hotel.repository.HotelRepository;
import com.hotel.repository.ReservaRepository;
import com.hotel.exception.HabitacionNoEncontradaException;
import com.hotel.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class HabitacionService {
    
    @Autowired
    private HabitacionRepository habitacionRepository;
    
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ReservaRepository reservaRepository;

    public Habitacion crearHabitacion(Long hotelId, Habitacion habitacion) {
        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));
        habitacion.setHotel(hotel);
        habitacion.setActiva(true);
        return habitacionRepository.save(habitacion);
    }

    public Habitacion actualizarHabitacion(Long id, Habitacion habitacionActualizada) {
        return habitacionRepository.findById(id)
            .map(habitacion -> {
                habitacion.setNumero(habitacionActualizada.getNumero());
                habitacion.setTipo(habitacionActualizada.getTipo());
                habitacion.setDescripcion(habitacionActualizada.getDescripcion());
                habitacion.setCapacidad(habitacionActualizada.getCapacidad());
                habitacion.setPrecioPorNoche(habitacionActualizada.getPrecioPorNoche());
                habitacion.setExtras(habitacionActualizada.getExtras());
                return habitacionRepository.save(habitacion);
            })
            .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
    }

    public void eliminarHabitacion(Long id) {
        Habitacion habitacion = habitacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
        habitacion.setActiva(false);
        habitacionRepository.save(habitacion);
    }

    public Habitacion obtenerHabitacionPorId(Long id) {
        return habitacionRepository.findById(id)
            .orElseThrow(() -> new HabitacionNoEncontradaException("Habitación no encontrada"));
    }

    public List<Habitacion> obtenerHabitacionesPorHotel(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));
        return habitacionRepository.findByHotel(hotel);
    }

    public List<Habitacion> obtenerHabitacionesPorTipo(Long hotelId, String tipo) {
        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));
        return habitacionRepository.findByHotelAndTipo(hotel, tipo);
    }

    public List<Habitacion> obtenerHabitacionesPorCapacidad(Long hotelId, Integer capacidad) {
        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));
        return habitacionRepository.findByHotelAndCapacidadGreaterThanEqual(hotel, capacidad);
    }

    public List<Habitacion> obtenerHabitacionesActivas(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));
        return habitacionRepository.findByHotelAndActiva(hotel, true);
    }

    public Habitacion registrarHabitacion(Long hotelId, Habitacion habitacion) {
        Hotel hotel = hotelService.obtenerPorId(hotelId);
        habitacion.setHotel(hotel);
        habitacion.setActiva(true);
        return habitacionRepository.save(habitacion);
    }

    public List<Habitacion> buscarHabitacionesDisponibles(Long hotelId, LocalDate fechaInicio, LocalDate fechaFin) {
        Hotel hotel = hotelService.obtenerPorId(hotelId);
        return habitacionRepository.findByHotelAndActiva(hotel, true).stream()
            .filter(habitacion -> verificarDisponibilidad(habitacion.getId(), fechaInicio, fechaFin))
            .collect(Collectors.toList());
    }

    public List<Habitacion> buscarHabitacionesPorTipo(Long hotelId, String tipo) {
        Hotel hotel = hotelService.obtenerPorId(hotelId);
        return habitacionRepository.findByHotelAndTipoAndActiva(hotel, tipo, true);
    }

    public List<Habitacion> buscarHabitacionesPorPrecio(Long hotelId, Double precioMin, Double precioMax) {
        Hotel hotel = hotelService.obtenerPorId(hotelId);
        return habitacionRepository.findByHotelAndPrecioPorNocheBetweenAndActiva(hotel, precioMin, precioMax, true);
    }

    public void desactivarHabitacion(Long id) {
        Habitacion habitacion = obtenerHabitacionPorId(id);
        habitacion.setActiva(false);
        habitacionRepository.save(habitacion);
    }

    @Transactional(readOnly = true)
    public boolean verificarDisponibilidad(Long habitacionId, LocalDate fechaInicio, LocalDate fechaFin) {
        LocalDateTime fechaInicioDateTime = fechaInicio.atTime(14, 0); // Check-in a las 14:00
        LocalDateTime fechaFinDateTime = fechaFin.atTime(12, 0); // Check-out a las 12:00
        
        return reservaRepository.findOverlappingReservations(
            habitacionId, fechaInicioDateTime, fechaFinDateTime).isEmpty();
    }

    public List<Habitacion> buscarHabitaciones(Long hotelId, String tipo, Double precioMin, Double precioMax) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel no encontrado"));

        if (tipo != null && precioMin != null && precioMax != null) {
            return habitacionRepository.findByHotelAndTipoAndPrecioPorNocheBetweenAndActiva(hotel, tipo, precioMin, precioMax, true);
        } else if (tipo != null) {
            return habitacionRepository.findByHotelAndTipoAndActiva(hotel, tipo, true);
        } else if (precioMin != null && precioMax != null) {
            return habitacionRepository.findByHotelAndPrecioPorNocheBetweenAndActiva(hotel, precioMin, precioMax, true);
        } else {
            return habitacionRepository.findByHotelAndActiva(hotel, true);
        }
    }
} 