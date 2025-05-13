package com.hotel.service;

import com.hotel.model.Extra;
import com.hotel.model.Reserva;
import com.hotel.model.ReservaExtra;
import com.hotel.repository.ExtraRepository;
import com.hotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar los extras o servicios adicionales del hotel.
 * Este servicio maneja todas las operaciones relacionadas con los extras
 * como desayuno, estacionamiento, spa, etc., incluyendo su configuración,
 * asignación a reservas y gestión de precios.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@Service
@Transactional
public class ExtraService {
    
    @Autowired
    private ExtraRepository extraRepository;
    
    @Autowired
    private ReservaRepository reservaRepository;

    /**
     * Crea un nuevo extra para un hotel.
     * Permite definir un nuevo servicio adicional con su precio y disponibilidad.
     *
     * @param extra El extra a crear
     * @return El extra creado
     * @throws RuntimeException si el hotel no existe
     */
    public Extra crearExtra(Extra extra) {
        extra.setDisponible(true);
        return extraRepository.save(extra);
    }

    /**
     * Actualiza un extra existente.
     * Permite modificar los detalles y el estado de un extra.
     *
     * @param extraId ID del extra
     * @param extra El nuevo estado del extra
     * @return El extra actualizado
     * @throws RuntimeException si el extra no existe
     */
    public Extra actualizarExtra(Long extraId, Extra extra) {
        return extraRepository.findById(extraId)
            .map(existingExtra -> {
                existingExtra.setNombre(extra.getNombre());
                existingExtra.setDescripcion(extra.getDescripcion());
                existingExtra.setPrecio(extra.getPrecio());
                existingExtra.setDisponible(extra.getDisponible());
                return extraRepository.save(existingExtra);
            })
            .orElseThrow(() -> new RuntimeException("Extra no encontrado"));
    }

    /**
     * Agrega un extra a una reserva.
     * Verifica la disponibilidad y actualiza el precio total de la reserva.
     *
     * @param reservaId ID de la reserva
     * @param extraId ID del extra a agregar
     * @return La reserva actualizada
     * @throws RuntimeException si la reserva o el extra no existen, o si el extra no está disponible
     */
    public Reserva agregarExtraAReserva(Long reservaId, Long extraId) {
        Reserva reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        Extra extra = extraRepository.findById(extraId)
            .orElseThrow(() -> new RuntimeException("Extra no encontrado"));
        
        if (!extra.getDisponible()) {
            throw new RuntimeException("El extra no está disponible");
        }
        
        ReservaExtra reservaExtra = new ReservaExtra();
        reservaExtra.setReserva(reserva);
        reservaExtra.setExtra(extra);
        reservaExtra.setCantidad(1); // O la cantidad que corresponda
        reservaExtra.setPrecioUnitario(extra.getPrecio());
        reserva.getReservaExtras().add(reservaExtra);
        
        // Actualizar precio total
        reserva.setPrecioTotal(reserva.getPrecioTotal() + extra.getPrecio());
        
        return reservaRepository.save(reserva);
    }

    /**
     * Elimina un extra de una reserva.
     * Actualiza el precio total de la reserva.
     *
     * @param reservaId ID de la reserva
     * @param extraId ID del extra a eliminar
     * @return La reserva actualizada
     * @throws RuntimeException si la reserva o el extra no existen
     */
    public Reserva eliminarExtraDeReserva(Long reservaId, Long extraId) {
        Reserva reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        Extra extra = extraRepository.findById(extraId)
            .orElseThrow(() -> new RuntimeException("Extra no encontrado"));
        
        reserva.getReservaExtras().removeIf(re -> re.getExtra().getId().equals(extra.getId()));
        
        // Actualizar precio total
        reserva.setPrecioTotal(reserva.getPrecioTotal() - extra.getPrecio());
        
        return reservaRepository.save(reserva);
    }

    /**
     * Obtiene un extra por su ID.
     *
     * @param id ID del extra
     * @return El extra encontrado
     */
    public Optional<Extra> obtenerExtraPorId(Long id) {
        return extraRepository.findById(id);
    }

    /**
     * Obtiene todos los extras disponibles de un hotel.
     *
     * @param hotelId ID del hotel
     * @return Lista de extras disponibles del hotel
     */
    public List<Extra> obtenerExtrasDisponibles() {
        return extraRepository.findByDisponible(true);
    }

    /**
     * Busca extras por nombre.
     *
     * @param nombre Nombre del extra
     * @return Lista de extras que coinciden con el nombre
     */
    public List<Extra> buscarExtrasPorNombre(String nombre) {
        return extraRepository.findByNombreContaining(nombre);
    }

    /**
     * Busca extras por rango de precio.
     *
     * @param precioMin Precio mínimo
     * @param precioMax Precio máximo
     * @return Lista de extras dentro del rango de precio
     */
    public List<Extra> buscarExtrasPorRangoPrecio(Double precioMin, Double precioMax) {
        return extraRepository.findByPrecioBetween(precioMin, precioMax);
    }

    /**
     * Obtiene todos los extras disponibles de un hotel.
     *
     * @param hotelId ID del hotel
     * @return Lista de extras disponibles del hotel
     */
    public List<Extra> obtenerTodosLosExtras() {
        return extraRepository.findAll();
    }
} 