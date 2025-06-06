package com.hotel.controller;

import com.hotel.model.Reserva;
import com.hotel.service.ReservaService;
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
import java.util.Map;

/**
 * Controlador REST para gestionar las reservas del sistema.
 * Proporciona endpoints para la creación, modificación y consulta de reservas,
 * así como operaciones específicas como cancelación y búsqueda por diferentes criterios.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Reservas", description = "API para la gestión de reservas")
public class ReservaController {
    
    @Autowired
    private ReservaService reservaService;

    /**
     * Crea una nueva reserva.
     *
     * @param reserva Datos de la reserva a crear
     * @return Reserva creada
     */
    @PostMapping(consumes = {"application/json", "application/json;charset=UTF-8"})
    @Operation(summary = "Crear reserva", description = "Crea una nueva reserva en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reserva creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de reserva inválidos"),
        @ApiResponse(responseCode = "404", description = "Habitación o usuario no encontrado"),
        @ApiResponse(responseCode = "409", description = "Habitación no disponible en las fechas especificadas")
    })
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
           try{ System.out.println("=== DEPURACIÓN POST /api/reservas ===");
            System.out.println("Reserva recibida: " + reserva);
            System.out.println("Usuario: " + (reserva.getUsuario() != null ? reserva.getUsuario() : "null"));
            System.out.println("Habitación: " + (reserva.getHabitacion() != null ? reserva.getHabitacion() : "null"));
            System.out.println("Extras: " + (reserva.getReservaExtras() != null ? reserva.getReservaExtras().size() + " extras" : "null"));
            System.out.println("========================================");
    
        Reserva nuevaReserva = reservaService.crearReserva(reserva);
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
        
        }catch (Exception e) {
        System.out.println("=== ERROR EN crearReserva ===");
        System.out.println("Tipo de error: " + e.getClass().getSimpleName());
        System.out.println("Mensaje: " + e.getMessage());
        e.printStackTrace();
        System.out.println("=============================");
        throw e;
        }
    }

    /**
     * Actualiza una reserva existente.
     *
     * @param id ID de la reserva
     * @param reserva Datos actualizados de la reserva
     * @return Reserva actualizada
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reserva", description = "Actualiza los datos de una reserva existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reserva actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de reserva inválidos"),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
        @ApiResponse(responseCode = "409", description = "No se puede modificar una reserva cancelada o completada")
    })
    public ResponseEntity<Reserva> actualizarReserva(
            @Parameter(description = "ID de la reserva") @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la reserva") @RequestBody Reserva reserva) {
        Reserva reservaActualizada = reservaService.actualizarReserva(id, reserva);
        return ResponseEntity.ok(reservaActualizada);
    }

    /**
     * Cancela una reserva existente.
     *
     * @param id ID de la reserva
     * @return Reserva cancelada
     */
    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar reserva", description = "Cancela una reserva existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reserva cancelada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
        @ApiResponse(responseCode = "409", description = "No se puede cancelar una reserva ya cancelada o completada")
    })
    public ResponseEntity<Void> cancelarReserva(
            @Parameter(description = "ID de la reserva") @PathVariable Long id) {
        reservaService.cancelarReserva(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Obtiene una reserva por su ID.
     *
     * @param id ID de la reserva
     * @return Reserva encontrada
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener reserva por ID", description = "Retorna los datos de una reserva específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    public ResponseEntity<Reserva> obtenerReservaPorId(
            @Parameter(description = "ID de la reserva") @PathVariable Long id) {
        return reservaService.obtenerReservaPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene todas las reservas de un usuario.
     *
     * @param usuarioId ID del usuario
     * @return Lista de reservas del usuario
     */
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar reservas por usuario", description = "Retorna todas las reservas de un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de reservas obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<List<Reserva>> obtenerReservasPorUsuario(
            @Parameter(description = "ID del usuario") @PathVariable Long usuarioId) {
        List<Reserva> reservas = reservaService.obtenerReservasPorUsuario(usuarioId);
        return ResponseEntity.ok(reservas);
    }

    /**
     * Obtiene todas las reservas de una habitación.
     *
     * @param habitacionId ID de la habitación
     * @return Lista de reservas de la habitación
     */
    @GetMapping("/habitacion/{habitacionId}")
    @Operation(summary = "Listar reservas por habitación", description = "Retorna todas las reservas de una habitación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de reservas obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    public ResponseEntity<List<Reserva>> obtenerReservasPorHabitacion(
            @Parameter(description = "ID de la habitación") @PathVariable Long habitacionId) {
        List<Reserva> reservas = reservaService.obtenerReservasPorHabitacion(habitacionId);
        return ResponseEntity.ok(reservas);
    }

    /**
     * Busca reservas por rango de fechas.
     *
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return Lista de reservas en el rango de fechas
     */
    @GetMapping("/buscar/fechas")
    @Operation(summary = "Buscar reservas por fechas", description = "Retorna las reservas en un rango de fechas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Fechas inválidas")
    })
    public ResponseEntity<List<Reserva>> buscarReservasPorFechas(
            @Parameter(description = "Fecha de inicio") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @Parameter(description = "Fecha de fin") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<Reserva> reservas = reservaService.buscarReservasPorFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(reservas);
    }

    /**
     * Busca reservas por estado.
     *
     * @param estado Estado de la reserva
     * @return Lista de reservas con el estado especificado
     */
    @GetMapping("/buscar/estado")
    @Operation(summary = "Buscar reservas por estado", description = "Retorna las reservas con un estado específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    public ResponseEntity<List<Reserva>> buscarReservasPorEstado(
            @Parameter(description = "Estado de la reserva") @RequestParam String estado) {
        List<Reserva> reservas = reservaService.buscarReservasPorEstado(estado);
        return ResponseEntity.ok(reservas);
    }

    /**
     * Devuelve las fechas ocupadas de una habitación (excluyendo reservas canceladas).
     * @param habitacionId ID de la habitación
     * @return Lista de fechas ocupadas (String ISO)
     */
    @GetMapping("/habitacion/{habitacionId}/fechas-ocupadas")
    @Operation(summary = "Fechas ocupadas de una habitación", description = "Devuelve las fechas ocupadas de una habitación (excluyendo reservas canceladas)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de fechas ocupadas obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    public ResponseEntity<List<String>> obtenerFechasOcupadasPorHabitacion(
            @Parameter(description = "ID de la habitación") @PathVariable Long habitacionId) {
        List<String> fechasOcupadas = reservaService.obtenerFechasOcupadasPorHabitacion(habitacionId);
        return ResponseEntity.ok(fechasOcupadas);
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar solo el estado de la reserva", description = "Permite cambiar el estado de la reserva sin modificar otros datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    public ResponseEntity<Reserva> actualizarEstadoReserva(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        Reserva reservaActualizada = reservaService.actualizarEstadoReserva(id, nuevoEstado);
        return ResponseEntity.ok(reservaActualizada);
    }
} 