package com.hotel.controller;

import com.hotel.model.DatosBancarios;
import com.hotel.service.DatosBancariosService;
import com.hotel.exception.DatosBancariosNoEncontradosException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar los datos bancarios del sistema.
 * Proporciona endpoints para el registro, actualización y consulta de tarjetas,
 * así como operaciones específicas como establecer tarjeta predeterminada.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/datos-bancarios")
@Tag(name = "Datos Bancarios", description = "API para la gestión de datos bancarios")
public class DatosBancariosController {
    
    @Autowired
    private DatosBancariosService datosBancariosService;

    /**
     * Registra una nueva tarjeta para un usuario.
     *
     * @param usuarioId ID del usuario
     * @param datosBancarios Datos de la tarjeta a registrar
     * @return Datos bancarios registrados
     */
    @PostMapping("/usuario/{usuarioId}")
    @Operation(summary = "Registrar tarjeta", description = "Registra una nueva tarjeta para un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tarjeta registrada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de tarjeta inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "409", description = "La tarjeta ya está registrada")
    })
    public ResponseEntity<DatosBancarios> registrarTarjeta(
            @Parameter(description = "ID del usuario") @PathVariable Long usuarioId,
            @Parameter(description = "Datos de la tarjeta") @RequestBody DatosBancarios datosBancarios) {
        DatosBancarios tarjetaRegistrada = datosBancariosService.registrarTarjeta(usuarioId, datosBancarios);
        return new ResponseEntity<>(tarjetaRegistrada, HttpStatus.CREATED);
    }

    /**
     * Actualiza los datos de una tarjeta existente.
     *
     * @param id ID de los datos bancarios
     * @param datosBancarios Datos actualizados de la tarjeta
     * @return Datos bancarios actualizados
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tarjeta", description = "Actualiza los datos de una tarjeta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarjeta actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de tarjeta inválidos"),
        @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada")
    })
    public ResponseEntity<DatosBancarios> actualizarTarjeta(
            @Parameter(description = "ID de los datos bancarios") @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la tarjeta") @RequestBody DatosBancarios datosBancarios) {
        DatosBancarios tarjetaActualizada = datosBancariosService.actualizarTarjeta(id, datosBancarios);
        return ResponseEntity.ok(tarjetaActualizada);
    }

    /**
     * Establece una tarjeta como predeterminada.
     *
     * @param id ID de los datos bancarios
     * @return Datos bancarios actualizados
     */
    @PutMapping("/{id}/predeterminada")
    @Operation(summary = "Establecer tarjeta predeterminada", description = "Establece una tarjeta como predeterminada para el usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarjeta establecida como predeterminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada")
    })
    public ResponseEntity<DatosBancarios> establecerTarjetaPredeterminada(
            @Parameter(description = "ID de los datos bancarios") @PathVariable Long id) {
        DatosBancarios tarjetaPredeterminada = datosBancariosService.establecerTarjetaPredeterminada(id);
        return ResponseEntity.ok(tarjetaPredeterminada);
    }

    /**
     * Elimina una tarjeta (soft delete).
     *
     * @param id ID de los datos bancarios
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tarjeta", description = "Elimina una tarjeta del sistema (soft delete)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tarjeta eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada"),
        @ApiResponse(responseCode = "409", description = "No se puede eliminar la tarjeta predeterminada")
    })
    public ResponseEntity<Void> eliminarTarjeta(
            @Parameter(description = "ID de los datos bancarios") @PathVariable Long id) {
        datosBancariosService.eliminarTarjeta(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene los datos bancarios por su ID.
     *
     * @param id ID de los datos bancarios
     * @return Datos bancarios encontrados
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener datos bancarios por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Datos bancarios encontrados"),
        @ApiResponse(responseCode = "404", description = "Datos bancarios no encontrados")
    })
    public ResponseEntity<DatosBancarios> obtenerDatosBancariosPorId(@PathVariable Long id) {
        return ResponseEntity.ok(datosBancariosService.obtenerDatosBancariosPorId(id));
    }

    /**
     * Obtiene todas las tarjetas activas de un usuario.
     *
     * @param usuarioId ID del usuario
     * @return Lista de tarjetas activas
     */
    @GetMapping("/usuario/{usuarioId}/activas")
    @Operation(summary = "Listar tarjetas activas", description = "Retorna todas las tarjetas activas de un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tarjetas obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<List<DatosBancarios>> obtenerTarjetasActivas(
            @Parameter(description = "ID del usuario") @PathVariable Long usuarioId) {
        List<DatosBancarios> tarjetas = datosBancariosService.obtenerTarjetasActivas(usuarioId);
        return ResponseEntity.ok(tarjetas);
    }

    /**
     * Obtiene la tarjeta predeterminada de un usuario.
     *
     * @param usuarioId ID del usuario
     * @return Tarjeta predeterminada
     */
    @GetMapping("/usuario/{usuarioId}/predeterminada")
    @Operation(summary = "Obtener tarjeta predeterminada", description = "Retorna la tarjeta predeterminada de un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarjeta predeterminada encontrada"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado o sin tarjeta predeterminada")
    })
    public ResponseEntity<DatosBancarios> obtenerTarjetaPredeterminada(
            @Parameter(description = "ID del usuario") @PathVariable Long usuarioId) {
        try {
            DatosBancarios tarjetaPredeterminada = datosBancariosService.obtenerTarjetaPredeterminada(usuarioId);
            return ResponseEntity.ok(tarjetaPredeterminada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Busca tarjetas por tipo.
     *
     * @param usuarioId ID del usuario
     * @param tipo Tipo de tarjeta
     * @return Lista de tarjetas del tipo especificado
     */
    @GetMapping("/usuario/{usuarioId}/tipo")
    @Operation(summary = "Buscar tarjetas por tipo", description = "Retorna las tarjetas de un tipo específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<List<DatosBancarios>> obtenerTarjetasPorTipo(
            @Parameter(description = "ID del usuario") @PathVariable Long usuarioId,
            @Parameter(description = "Tipo de tarjeta") @RequestParam String tipo) {
        List<DatosBancarios> tarjetas = datosBancariosService.obtenerTarjetasPorTipo(usuarioId, tipo);
        return ResponseEntity.ok(tarjetas);
    }

    @PostMapping
    @Operation(summary = "Crear datos bancarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Datos bancarios creados"),
        @ApiResponse(responseCode = "400", description = "Datos bancarios inválidos")
    })
    public ResponseEntity<DatosBancarios> crearDatosBancarios(@RequestBody DatosBancarios datosBancarios) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(datosBancariosService.crearDatosBancarios(datosBancarios));
    }

    @GetMapping("/usuario/{id}")
    @Operation(summary = "Obtener datos bancarios por ID de usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Datos bancarios encontrados"),
        @ApiResponse(responseCode = "404", description = "Datos bancarios no encontrados")
    })
    public ResponseEntity<List<DatosBancarios>> obtenerDatosBancariosPorUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(datosBancariosService.obtenerDatosBancariosPorUsuario(id));
    }

    @GetMapping("/tarjeta/{id}")
    @Operation(summary = "Obtener datos de una tarjeta específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Datos de la tarjeta encontrados"),
        @ApiResponse(responseCode = "404", description = "Datos de la tarjeta no encontrados")
    })
    public ResponseEntity<DatosBancarios> obtenerDatosTarjeta(@PathVariable Long id) {
        return ResponseEntity.ok(datosBancariosService.obtenerDatosBancariosPorId(id));
    }
} 