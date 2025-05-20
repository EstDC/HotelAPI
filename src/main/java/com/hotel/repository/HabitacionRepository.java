package com.hotel.repository;

import com.hotel.model.Habitacion;
import com.hotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    List<Habitacion> findByHotel(Hotel hotel);
    List<Habitacion> findByHotelAndTipo(Hotel hotel, String tipo);
    List<Habitacion> findByHotelAndCapacidadGreaterThanEqual(Hotel hotel, Integer capacidad);
    List<Habitacion> findByHotelAndActiva(Hotel hotel, boolean activa);
    List<Habitacion> findByHotelAndTipoAndActiva(Hotel hotel, String tipo, boolean activa);
    List<Habitacion> findByHotelAndPrecioPorNocheBetweenAndActiva(Hotel hotel, Double precioMin, Double precioMax, boolean activa);
    List<Habitacion> findByHotelAndTipoAndPrecioPorNocheBetweenAndActiva(Hotel hotel, String tipo, Double precioMin, Double precioMax, boolean activa);
    List<Habitacion> findAll();
} 