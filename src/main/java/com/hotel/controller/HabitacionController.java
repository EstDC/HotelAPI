package com.hotel.controller;

import com.hotel.model.Habitacion;
import com.hotel.service.HabitacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para gestionar las habitaciones del sistema.
 * Proporciona endpoints para el registro, actualización y consulta de habitaciones,
 * así como búsquedas de disponibilidad y filtros por diferentes criterios.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/habitaciones")
@Tag(name = "Habitación", description = "API para la gestión de habitaciones")
public class HabitacionController {
    
    @Autowired
    private HabitacionService habitacionService;

    /**
     * Registra una nueva habitación en un hotel.
     *
     * @param hotelId ID del hotel
     * @param habitacion Datos de la habitación a registrar
     * @return Habitación registrada
     */
    @PostMapping("/hotel/{hotelId}")
    @Operation(summary = "Registrar nueva habitación", description = "Crea una nueva habitación en un hotel específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Habitación creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de habitación inválidos"),
        @ApiResponse(responseCode = "404", description = "Hotel no encontrado")
    })
    public ResponseEntity<Habitacion> registrarHabitacion(
            @Parameter(description = "ID del hotel") @PathVariable Long hotelId,
            @Parameter(description = "Datos de la habitación") @RequestBody Habitacion habitacion) {
        Habitacion nuevaHabitacion = habitacionService.registrarHabitacion(hotelId, habitacion);
        return new ResponseEntity<>(nuevaHabitacion, HttpStatus.CREATED);
    }

    /**
     * Actualiza los datos de una habitación existente.
     *
     * @param id ID de la habitación
     * @param habitacion Datos actualizados de la habitación
     * @return Habitación actualizada
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar habitación", description = "Actualiza los datos de una habitación existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Habitación actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de habitación inválidos"),
        @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    public ResponseEntity<Habitacion> actualizarHabitacion(
            @Parameter(description = "ID de la habitación") @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la habitación") @RequestBody Habitacion habitacion) {
        Habitacion habitacionActualizada = habitacionService.actualizarHabitacion(id, habitacion);
        return ResponseEntity.ok(habitacionActualizada);
    }

    /**
     * Obtiene una habitación por su ID.
     *
     * @param id ID de la habitación
     * @return Habitación encontrada
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener habitación por ID", description = "Retorna los datos de una habitación específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Habitación encontrada"),
        @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    public ResponseEntity<Habitacion> obtenerHabitacionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(habitacionService.obtenerHabitacionPorId(id));
    }

    /**
     * Obtiene todas las habitaciones de un hotel.
     *
     * @param hotelId ID del hotel
     * @return Lista de habitaciones del hotel
     */
    @GetMapping("/hotel/{hotelId}")
    @Operation(summary = "Listar habitaciones por hotel", description = "Retorna todas las habitaciones de un hotel")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de habitaciones obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Hotel no encontrado")
    })
    public ResponseEntity<List<Habitacion>> obtenerHabitacionesPorHotel(
            @Parameter(description = "ID del hotel") @PathVariable Long hotelId) {
        List<Habitacion> habitaciones = habitacionService.obtenerHabitacionesPorHotel(hotelId);
        return ResponseEntity.ok(habitaciones);
    }

    /**
     * Busca habitaciones disponibles en un rango de fechas.
     *
     * @param hotelId ID del hotel
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return Lista de habitaciones disponibles
     */
    @GetMapping("/disponibles")
    @Operation(summary = "Buscar habitaciones disponibles", description = "Retorna las habitaciones disponibles en un rango de fechas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Fechas inválidas")
    })
    public ResponseEntity<List<Habitacion>> buscarHabitacionesDisponibles(
            @Parameter(description = "ID del hotel") @RequestParam Long hotelId,
            @Parameter(description = "Fecha de inicio") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @Parameter(description = "Fecha de fin") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<Habitacion> habitaciones = habitacionService.buscarHabitacionesDisponibles(hotelId, fechaInicio, fechaFin);
        return ResponseEntity.ok(habitaciones);
    }

    /**
     * Busca habitaciones por tipo.
     *
     * @param hotelId ID del hotel
     * @param tipo Tipo de habitación
     * @return Lista de habitaciones del tipo especificado
     */
    @GetMapping("/tipo")
    @Operation(summary = "Buscar habitaciones por tipo", description = "Retorna las habitaciones de un tipo específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    public ResponseEntity<List<Habitacion>> buscarHabitacionesPorTipo(
            @Parameter(description = "ID del hotel") @RequestParam Long hotelId,
            @Parameter(description = "Tipo de habitación") @RequestParam String tipo) {
        List<Habitacion> habitaciones = habitacionService.buscarHabitacionesPorTipo(hotelId, tipo);
        return ResponseEntity.ok(habitaciones);
    }

    /**
     * Busca habitaciones por rango de precio.
     *
     * @param hotelId ID del hotel
     * @param precioMinimo Precio mínimo
     * @param precioMaximo Precio máximo
     * @return Lista de habitaciones dentro del rango de precio
     */
    @GetMapping("/precio")
    @Operation(summary = "Buscar habitaciones por precio", description = "Retorna las habitaciones dentro de un rango de precio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    public ResponseEntity<List<Habitacion>> buscarHabitacionesPorPrecio(
            @Parameter(description = "ID del hotel") @RequestParam Long hotelId,
            @Parameter(description = "Precio mínimo") @RequestParam Double precioMinimo,
            @Parameter(description = "Precio máximo") @RequestParam Double precioMaximo) {
        List<Habitacion> habitaciones = habitacionService.buscarHabitacionesPorPrecio(hotelId, precioMinimo, precioMaximo);
        return ResponseEntity.ok(habitaciones);
    }

    /**
     * Desactiva una habitación.
     *
     * @param id ID de la habitación
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar habitación", description = "Desactiva una habitación del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Habitación desactivada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    public ResponseEntity<Void> desactivarHabitacion(
            @Parameter(description = "ID de la habitación") @PathVariable Long id) {
        habitacionService.desactivarHabitacion(id);
        return ResponseEntity.noContent().build();
    }
} 