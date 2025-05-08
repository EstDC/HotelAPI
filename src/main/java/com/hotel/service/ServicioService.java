package com.hotel.service;

import com.hotel.model.Servicio;
import com.hotel.model.Hotel;
import com.hotel.repository.ServicioRepository;
import com.hotel.repository.HotelRepository;
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

    @Transactional
    public Servicio save(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Transactional
    public void deleteById(Long id) {
        servicioRepository.deleteById(id);
    }

    public List<Servicio> findByHotel(Hotel hotel) {
        return servicioRepository.findByHotel(hotel);
    }

    public List<Servicio> findByHotelAndActivo(Hotel hotel) {
        return servicioRepository.findByHotelAndActivo(hotel);
    }

    public List<Servicio> findByActivo(Boolean activo) {
        return servicioRepository.findByActivo(activo);
    }

    @Transactional
    public Servicio addServicioToHotel(Long hotelId, Servicio servicio) {
        Optional<Hotel> hotelOpt = hotelRepository.findById(hotelId);
        if (hotelOpt.isPresent()) {
            Hotel hotel = hotelOpt.get();
            servicio.getHoteles().add(hotel);
            return servicioRepository.save(servicio);
        }
        throw new RuntimeException("Hotel no encontrado");
    }

    @Transactional
    public Servicio removeServicioFromHotel(Long hotelId, Long servicioId) {
        Optional<Hotel> hotelOpt = hotelRepository.findById(hotelId);
        Optional<Servicio> servicioOpt = servicioRepository.findById(servicioId);
        
        if (hotelOpt.isPresent() && servicioOpt.isPresent()) {
            Hotel hotel = hotelOpt.get();
            Servicio servicio = servicioOpt.get();
            servicio.getHoteles().remove(hotel);
            return servicioRepository.save(servicio);
        }
        throw new RuntimeException("Hotel o servicio no encontrado");
    }
} 