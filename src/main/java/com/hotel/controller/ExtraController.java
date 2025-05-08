package com.hotel.controller;

import com.hotel.model.Extra;
import com.hotel.model.Reserva;
import com.hotel.service.ExtraService;
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
 * Controlador REST para gestionar los servicios adicionales del hotel.
 * Proporciona endpoints para la creación, actualización y consulta de extras,
 * así como operaciones específicas como agregar extras a reservas.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/extras")
@Tag(name = "Extra", description = "API para la gestión de servicios adicionales")
public class ExtraController {
    
    @Autowired
    private ExtraService extraService;

    /**
     * Crea un nuevo servicio adicional.
     *
     * @param extra Datos del servicio adicional
     * @return Servicio adicional creado
     */
    @PostMapping
    @Operation(summary = "Crear servicio adicional", description = "Crea un nuevo servicio adicional")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Servicio adicional creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de servicio adicional inválidos")
    })
    public ResponseEntity<Extra> crearExtra(
            @Parameter(description = "Datos del servicio adicional") @RequestBody Extra extra) {
        Extra extraCreado = extraService.crearExtra(extra);
        return new ResponseEntity<>(extraCreado, HttpStatus.CREATED);
    }

    /**
     * Actualiza un servicio adicional existente.
     *
     * @param id ID del servicio adicional
     * @param extra Datos actualizados del servicio
     * @return Servicio adicional actualizado
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar servicio adicional", description = "Actualiza los datos de un servicio adicional existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Servicio adicional actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de servicio adicional inválidos"),
        @ApiResponse(responseCode = "404", description = "Servicio adicional no encontrado")
    })
    public ResponseEntity<Extra> actualizarExtra(
            @Parameter(description = "ID del servicio adicional") @PathVariable Long id,
            @Parameter(description = "Datos actualizados del servicio") @RequestBody Extra extra) {
        Extra extraActualizado = extraService.actualizarExtra(id, extra);
        return ResponseEntity.ok(extraActualizado);
    }

    /**
     * Agrega un servicio adicional a una reserva.
     *
     * @param reservaId ID de la reserva
     * @param extraId ID del servicio adicional
     * @return Servicio adicional agregado
     */
    @PostMapping("/reserva/{reservaId}/extra/{extraId}")
    @Operation(summary = "Agregar extra a reserva", description = "Agrega un extra a una reserva existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Extra agregado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reserva o extra no encontrado"),
        @ApiResponse(responseCode = "400", description = "Extra no disponible")
    })
    public ResponseEntity<Reserva> agregarExtraAReserva(
            @Parameter(description = "ID de la reserva") @PathVariable Long reservaId,
            @Parameter(description = "ID del extra") @PathVariable Long extraId) {
        Reserva reserva = extraService.agregarExtraAReserva(reservaId, extraId);
        return ResponseEntity.ok(reserva);
    }

    /**
     * Elimina un servicio adicional de una reserva.
     *
     * @param reservaId ID de la reserva
     * @param extraId ID del servicio adicional
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/reserva/{reservaId}/extra/{extraId}")
    @Operation(summary = "Eliminar extra de reserva", description = "Elimina un servicio adicional de una reserva")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Servicio adicional eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reserva o servicio adicional no encontrado")
    })
    public ResponseEntity<Void> eliminarExtraDeReserva(
            @Parameter(description = "ID de la reserva") @PathVariable Long reservaId,
            @Parameter(description = "ID del servicio adicional") @PathVariable Long extraId) {
        extraService.eliminarExtraDeReserva(reservaId, extraId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene un servicio adicional por su ID.
     *
     * @param id ID del servicio adicional
     * @return Servicio adicional encontrado
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener extra por ID", description = "Retorna los datos de un servicio adicional específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Servicio adicional encontrado"),
        @ApiResponse(responseCode = "404", description = "Servicio adicional no encontrado")
    })
    public ResponseEntity<Extra> obtenerExtraPorId(
            @Parameter(description = "ID del servicio adicional") @PathVariable Long id) {
        return extraService.obtenerExtraPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene todos los servicios adicionales disponibles.
     *
     * @return Lista de servicios adicionales disponibles
     */
    @GetMapping
    @Operation(summary = "Listar todos los extras", description = "Retorna todos los servicios adicionales disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de servicios adicionales obtenida exitosamente")
    })
    public ResponseEntity<List<Extra>> obtenerTodosLosExtras() {
        List<Extra> extras = extraService.obtenerTodosLosExtras();
        return ResponseEntity.ok(extras);
    }

    /**
     * Obtiene todos los servicios adicionales disponibles.
     *
     * @return Lista de servicios adicionales disponibles
     */
    @GetMapping("/disponibles")
    @Operation(summary = "Listar extras disponibles", description = "Retorna todos los servicios adicionales disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de servicios adicionales obtenida exitosamente")
    })
    public ResponseEntity<List<Extra>> obtenerExtrasDisponibles() {
        List<Extra> extras = extraService.obtenerExtrasDisponibles();
        return ResponseEntity.ok(extras);
    }

    /**
     * Busca servicios adicionales por nombre.
     *
     * @param nombre Nombre del servicio adicional
     * @return Lista de servicios adicionales que coinciden con el nombre
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar extras por nombre", description = "Retorna los servicios adicionales que coinciden con el nombre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    public ResponseEntity<List<Extra>> buscarExtrasPorNombre(
            @Parameter(description = "Nombre del extra") @RequestParam String nombre) {
        List<Extra> extras = extraService.buscarExtrasPorNombre(nombre);
        return ResponseEntity.ok(extras);
    }

    /**
     * Busca servicios adicionales por rango de precio.
     *
     * @param precioMin Precio mínimo
     * @param precioMax Precio máximo
     * @return Lista de servicios adicionales en el rango de precio
     */
    @GetMapping("/buscar/precio")
    @Operation(summary = "Buscar extras por precio", description = "Retorna los servicios adicionales dentro de un rango de precio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    public ResponseEntity<List<Extra>> buscarExtrasPorRangoPrecio(
            @Parameter(description = "Precio mínimo") @RequestParam Double precioMin,
            @Parameter(description = "Precio máximo") @RequestParam Double precioMax) {
        List<Extra> extras = extraService.buscarExtrasPorRangoPrecio(precioMin, precioMax);
        return ResponseEntity.ok(extras);
    }
} 