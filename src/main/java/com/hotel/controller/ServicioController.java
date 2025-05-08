package com.hotel.controller;

import com.hotel.model.Servicio;
import com.hotel.model.Hotel;
import com.hotel.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public List<Servicio> getAllServicios() {
        return servicioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> getServicioById(@PathVariable Long id) {
        return servicioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Servicio createServicio(@RequestBody Servicio servicio) {
        return servicioService.save(servicio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> updateServicio(@PathVariable Long id, @RequestBody Servicio servicio) {
        return servicioService.findById(id)
                .map(existingServicio -> {
                    servicio.setId(id);
                    return ResponseEntity.ok(servicioService.save(servicio));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicio(@PathVariable Long id) {
        return servicioService.findById(id)
                .map(servicio -> {
                    servicioService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/hotel/{hotelId}")
    public List<Servicio> getServiciosByHotel(@PathVariable Long hotelId) {
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        return servicioService.findByHotel(hotel);
    }

    @GetMapping("/hotel/{hotelId}/activos")
    public List<Servicio> getServiciosActivosByHotel(@PathVariable Long hotelId) {
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        return servicioService.findByHotelAndActivo(hotel);
    }

    @GetMapping("/activos")
    public List<Servicio> getServiciosActivos() {
        return servicioService.findByActivo(true);
    }

    @PostMapping("/hotel/{hotelId}")
    public ResponseEntity<Servicio> addServicioToHotel(
            @PathVariable Long hotelId,
            @RequestBody Servicio servicio) {
        try {
            Servicio savedServicio = servicioService.addServicioToHotel(hotelId, servicio);
            return ResponseEntity.ok(savedServicio);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/hotel/{hotelId}/servicio/{servicioId}")
    public ResponseEntity<Servicio> removeServicioFromHotel(
            @PathVariable Long hotelId,
            @PathVariable Long servicioId) {
        try {
            Servicio updatedServicio = servicioService.removeServicioFromHotel(hotelId, servicioId);
            return ResponseEntity.ok(updatedServicio);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 