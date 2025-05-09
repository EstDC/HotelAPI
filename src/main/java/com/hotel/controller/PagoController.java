package com.hotel.controller;

import com.hotel.model.Pago;
import com.hotel.service.PagoService;
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
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador REST para gestionar los pagos del sistema.
 * Proporciona endpoints para el procesamiento, confirmación y consulta de pagos,
 * así como operaciones específicas como reembolsos y búsqueda por diferentes criterios.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "API para la gestión de pagos")
public class PagoController {
    
    @Autowired
    private PagoService pagoService;

    /**
     * Procesa un nuevo pago.
     *
     * @param pago Datos del pago a procesar
     * @return Pago procesado
     */
    @PostMapping
    @Operation(summary = "Procesar pago", description = "Procesa un nuevo pago en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pago procesado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de pago inválidos"),
        @ApiResponse(responseCode = "404", description = "Reserva o método de pago no encontrado"),
        @ApiResponse(responseCode = "409", description = "La reserva ya tiene un pago procesado")
    })
    public ResponseEntity<Pago> procesarPago(
            @Parameter(description = "Datos del pago") @RequestBody Pago pago) {
        Pago pagoProcesado = pagoService.procesarPago(pago);
        return new ResponseEntity<>(pagoProcesado, HttpStatus.CREATED);
    }

    /**
     * Confirma un pago existente.
     *
     * @param id ID del pago
     * @return Pago confirmado
     */
    @PutMapping("/{id}/confirmar")
    @Operation(summary = "Confirmar pago", description = "Confirma un pago existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago confirmado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @ApiResponse(responseCode = "409", description = "El pago ya está confirmado o reembolsado")
    })
    public ResponseEntity<Pago> confirmarPago(
            @Parameter(description = "ID del pago") @PathVariable Long id) {
        Pago pagoConfirmado = pagoService.confirmarPago(id);
        return ResponseEntity.ok(pagoConfirmado);
    }

    /**
     * Procesa un reembolso para un pago.
     *
     * @param id ID del pago
     * @return Pago reembolsado
     */
    @PutMapping("/{id}/reembolsar")
    @Operation(summary = "Procesar reembolso", description = "Procesa un reembolso para un pago existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reembolso procesado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @ApiResponse(responseCode = "409", description = "El pago no puede ser reembolsado")
    })
    public ResponseEntity<Pago> procesarReembolso(
            @Parameter(description = "ID del pago") @PathVariable Long id) {
        Pago pagoReembolsado = pagoService.procesarReembolso(id);
        return ResponseEntity.ok(pagoReembolsado);
    }

    /**
     * Obtiene un pago por su ID.
     *
     * @param id ID del pago
     * @return Pago encontrado
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID", description = "Retorna los datos de un pago específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago encontrado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<Pago> obtenerPagoPorId(
            @Parameter(description = "ID del pago") @PathVariable Long id) {
        return pagoService.obtenerPagoPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene todos los pagos de una reserva.
     *
     * @param reservaId ID de la reserva
     * @return Lista de pagos de la reserva
     */
    @GetMapping("/reserva/{reservaId}")
    @Operation(summary = "Listar pagos por reserva", description = "Retorna todos los pagos de una reserva")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pagos obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    public ResponseEntity<List<Pago>> obtenerPagosPorReserva(
            @Parameter(description = "ID de la reserva") @PathVariable Long reservaId) {
        List<Pago> pagos = pagoService.obtenerPagosPorReserva(reservaId);
        return ResponseEntity.ok(pagos);
    }

    /**
     * Busca pagos por estado.
     *
     * @param estado Estado del pago
     * @return Lista de pagos con el estado especificado
     */
    @GetMapping("/buscar/estado")
    @Operation(summary = "Buscar pagos por estado", description = "Retorna los pagos con un estado específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    public ResponseEntity<List<Pago>> obtenerPagosPorEstado(
            @Parameter(description = "Estado del pago") @RequestParam String estado) {
        List<Pago> pagos = pagoService.obtenerPagosPorEstado(estado);
        return ResponseEntity.ok(pagos);
    }

    /**
     * Busca pagos por método de pago.
     *
     * @param metodoPago Método de pago
     * @return Lista de pagos con el método especificado
     */
    @GetMapping("/buscar/metodo")
    @Operation(summary = "Buscar pagos por método", description = "Retorna los pagos con un método específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    public ResponseEntity<List<Pago>> obtenerPagosPorMetodo(
            @Parameter(description = "Método de pago") @RequestParam String metodoPago) {
        List<Pago> pagos = pagoService.obtenerPagosPorMetodo(metodoPago);
        return ResponseEntity.ok(pagos);
    }

    /**
     * Busca pagos por rango de fechas.
     *
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return Lista de pagos en el rango de fechas
     */
    @GetMapping("/buscar/fechas")
    @Operation(summary = "Buscar pagos por fechas", description = "Retorna los pagos en un rango de fechas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Fechas inválidas")
    })
    public ResponseEntity<List<Pago>> buscarPagosPorRangoFechas(
            @Parameter(description = "Fecha de inicio") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @Parameter(description = "Fecha de fin") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        LocalDateTime fechaInicioDateTime = fechaInicio.atStartOfDay();
        LocalDateTime fechaFinDateTime = fechaFin.atTime(23, 59, 59);
        List<Pago> pagos = pagoService.obtenerPagosPorRangoFechas(fechaInicioDateTime, fechaFinDateTime);
        return ResponseEntity.ok(pagos);
    }
} 