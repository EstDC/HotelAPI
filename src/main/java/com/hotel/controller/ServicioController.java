package com.hotel.controller;

import com.hotel.model.Servicio;
import com.hotel.model.Hotel;
import com.hotel.service.ServicioService;
import com.hotel.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@Tag(name = "Servicios", description = "API para la gestión de servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    @Operation(summary = "Listar todos los servicios", description = "Obtiene todos los servicios del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de servicios obtenida exitosamente")
    })
    public List<Servicio> getAllServicios() {
        return servicioService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener servicio por ID", description = "Obtiene un servicio por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Servicio encontrado"),
        @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    public Servicio getServicioById(@PathVariable Long id) {
        return servicioService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado con ID: " + id));
    }

    @PostMapping
    @Operation(summary = "Crear servicio", description = "Crea un nuevo servicio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Servicio creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de servicio inválidos")
    })
    public Servicio createServicio(@Valid @RequestBody Servicio servicio) {
        return servicioService.save(servicio);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar servicio", description = "Actualiza un servicio existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Servicio actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de servicio inválidos"),
        @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    public Servicio updateServicio(@PathVariable Long id, @Valid @RequestBody Servicio servicio) {
        if (!servicioService.existsById(id)) {
            throw new ResourceNotFoundException("Servicio no encontrado con ID: " + id);
        }
        servicio.setId(id);
        return servicioService.save(servicio);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar servicio", description = "Elimina un servicio por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Servicio eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    public void deleteServicio(@PathVariable Long id) {
        if (!servicioService.existsById(id)) {
            throw new ResourceNotFoundException("Servicio no encontrado con ID: " + id);
        }
        servicioService.deleteById(id);
    }

    @GetMapping("/hotel/{hotelId}")
    @Operation(summary = "Listar servicios por hotel", description = "Obtiene todos los servicios de un hotel")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de servicios obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Hotel no encontrado")
    })
    public List<Servicio> getServiciosByHotel(@PathVariable Long hotelId) {
        return servicioService.findByHotelId(hotelId);
    }

    @GetMapping("/hotel/{hotelId}/activos")
    @Operation(summary = "Listar servicios activos por hotel", description = "Obtiene todos los servicios activos de un hotel")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de servicios activos obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Hotel no encontrado")
    })
    public List<Servicio> getServiciosActivosByHotel(@PathVariable Long hotelId) {
        return servicioService.findByHotelIdAndActivo(hotelId);
    }

    @GetMapping("/activos")
    @Operation(summary = "Listar servicios activos", description = "Obtiene todos los servicios activos del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de servicios activos obtenida exitosamente")
    })
    public List<Servicio> getServiciosActivos() {
        return servicioService.findByActivo(true);
    }

    @PostMapping("/hotel/{hotelId}")
    @Operation(summary = "Agregar servicio a hotel", description = "Agrega un servicio a un hotel")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Servicio agregado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Hotel no encontrado")
    })
    public Servicio addServicioToHotel(@PathVariable Long hotelId, @Valid @RequestBody Servicio servicio) {
        return servicioService.addServicioToHotel(hotelId, servicio);
    }

    @DeleteMapping("/hotel/{hotelId}/servicio/{servicioId}")
    @Operation(summary = "Eliminar servicio de hotel", description = "Elimina un servicio de un hotel")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Servicio eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Hotel o servicio no encontrado")
    })
    public Servicio removeServicioFromHotel(@PathVariable Long hotelId, @PathVariable Long servicioId) {
        return servicioService.removeServicioFromHotel(hotelId, servicioId);
    }
} 