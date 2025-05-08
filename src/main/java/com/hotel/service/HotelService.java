package com.hotel.service;

import com.hotel.model.Hotel;
import com.hotel.repository.HotelRepository;
import com.hotel.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HotelService {
    
    @Autowired
    private HotelRepository hotelRepository;

    public Hotel crearHotel(Hotel hotel) {
        hotel.setActivo(true);
        return hotelRepository.save(hotel);
    }

    public List<Hotel> obtenerTodos() {
        return hotelRepository.findByActivoTrue();
    }

    public Hotel obtenerPorId(Long id) {
        return hotelRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel no encontrado"));
    }

    public Hotel actualizarHotel(Hotel hotel) {
        if (!hotelRepository.existsById(hotel.getId())) {
            throw new ResourceNotFoundException("Hotel no encontrado");
        }
        return hotelRepository.save(hotel);
    }

    public void eliminarHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel no encontrado"));
        hotel.setActivo(false);
        hotelRepository.save(hotel);
    }

    public List<Hotel> buscarHotelesPorCiudad(String ciudad) {
        return hotelRepository.findByCiudadAndActivoTrue(ciudad);
    }

    public List<Hotel> buscarHotelesPorPais(String pais) {
        return hotelRepository.findByPaisAndActivoTrue(pais);
    }

    public List<Hotel> buscarHotelesPorEstrellas(Integer minEstrellas, Integer maxEstrellas) {
        return hotelRepository.findByEstrellasGreaterThanEqualAndEstrellasLessThanEqualAndActivoTrue(minEstrellas, maxEstrellas);
    }

    public List<Hotel> buscarHoteles(String nombre, String ciudad, Integer estrellas) {
        if (nombre != null && ciudad != null && estrellas != null) {
            return hotelRepository.findByNombreContainingAndCiudadAndEstrellasAndActivoTrue(nombre, ciudad, estrellas);
        } else if (nombre != null && ciudad != null) {
            return hotelRepository.findByNombreContainingAndCiudadAndActivoTrue(nombre, ciudad);
        } else if (nombre != null && estrellas != null) {
            return hotelRepository.findByNombreContainingAndEstrellasAndActivoTrue(nombre, estrellas);
        } else if (ciudad != null && estrellas != null) {
            return hotelRepository.findByCiudadAndEstrellasAndActivoTrue(ciudad, estrellas);
        } else if (nombre != null) {
            return hotelRepository.findByNombreContainingAndActivoTrue(nombre);
        } else if (ciudad != null) {
            return hotelRepository.findByCiudadAndActivoTrue(ciudad);
        } else if (estrellas != null) {
            return hotelRepository.findByEstrellasAndActivoTrue(estrellas);
        }
        return hotelRepository.findByActivoTrue();
    }
} 