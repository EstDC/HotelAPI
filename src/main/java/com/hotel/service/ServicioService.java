package com.hotel.service;

import com.hotel.model.Servicio;
import com.hotel.model.Hotel;
import com.hotel.repository.ServicioRepository;
import com.hotel.repository.HotelRepository;
import com.hotel.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    public Optional<Servicio> findById(Long id) {
        return servicioRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return servicioRepository.existsById(id);
    }

    @Transactional
    public Servicio save(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Transactional
    public void deleteById(Long id) {
        servicioRepository.deleteById(id);
    }

    public List<Servicio> findByHotelId(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel no encontrado con ID: " + hotelId));
        return servicioRepository.findByHotel(hotel);
    }

    public List<Servicio> findByHotelIdAndActivo(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel no encontrado con ID: " + hotelId));
        return servicioRepository.findByHotelAndActivo(hotel);
    }

    public List<Servicio> findByActivo(Boolean activo) {
        return servicioRepository.findByActivo(activo);
    }

    @Transactional
    public Servicio addServicioToHotel(Long hotelId, Servicio servicio) {
        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel no encontrado con ID: " + hotelId));
        
        servicio.getHoteles().add(hotel);
        return servicioRepository.save(servicio);
    }

    @Transactional
    public Servicio removeServicioFromHotel(Long hotelId, Long servicioId) {
        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel no encontrado con ID: " + hotelId));
        Servicio servicio = servicioRepository.findById(servicioId)
            .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado con ID: " + servicioId));
        
        servicio.getHoteles().remove(hotel);
        return servicioRepository.save(servicio);
    }
} 