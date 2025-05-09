package com.hotel.controller;

import com.hotel.model.Hotel;
import com.hotel.service.HotelService;
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
 * Controlador REST para gestionar los hoteles del sistema.
 * Proporciona endpoints para el registro, actualización y consulta de hoteles,
 * así como búsquedas por diferentes criterios.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/hoteles")
@Tag(name = "Hoteles", description = "API para gestión de hoteles")
public class HotelController {
    
    @Autowired
    private HotelService hotelService;

    /**
     * Registra un nuevo hotel en el sistema.
     *
     * @param hotel Datos del hotel a registrar
     * @return Hotel registrado
     */
    @PostMapping
    @Operation(summary = "Crear un nuevo hotel")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hotel creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Hotel> crearHotel(@RequestBody Hotel hotel) {
        return ResponseEntity.ok(hotelService.crearHotel(hotel));
    }

    /**
     * Actualiza los datos de un hotel existente.
     *
     * @param id ID del hotel a actualizar
     * @param hotel Datos actualizados del hotel
     * @return Hotel actualizado
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un hotel existente")
    public ResponseEntity<Hotel> actualizarHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
        hotel.setId(id);
        return ResponseEntity.ok(hotelService.actualizarHotel(hotel));
    }

    /**
     * Obtiene un hotel por su ID.
     *
     * @param id ID del hotel
     * @return Hotel encontrado
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un hotel por ID")
    public ResponseEntity<Hotel> obtenerHotel(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.obtenerPorId(id));
    }

    /**
     * Obtiene todos los hoteles del sistema.
     *
     * @return Lista de hoteles
     */
    @GetMapping
    @Operation(summary = "Obtener todos los hoteles")
    public ResponseEntity<List<Hotel>> obtenerHoteles() {
        return ResponseEntity.ok(hotelService.obtenerTodos());
    }

    /**
     * Busca hoteles por ciudad.
     *
     * @param ciudad Ciudad a buscar
     * @return Lista de hoteles en la ciudad especificada
     */
    @GetMapping("/buscar/ciudad")
    @Operation(summary = "Buscar hoteles por ciudad", description = "Retorna los hoteles en una ciudad específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    public ResponseEntity<List<Hotel>> buscarHotelesPorCiudad(
            @Parameter(description = "Ciudad a buscar") @RequestParam String ciudad) {
        List<Hotel> hoteles = hotelService.buscarHotelesPorCiudad(ciudad);
        return ResponseEntity.ok(hoteles);
    }

    /**
     * Busca hoteles por país.
     *
     * @param pais País a buscar
     * @return Lista de hoteles en el país especificado
     */
    @GetMapping("/buscar/pais")
    @Operation(summary = "Buscar hoteles por país", description = "Retorna los hoteles en un país específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    public ResponseEntity<List<Hotel>> buscarHotelesPorPais(
            @Parameter(description = "País a buscar") @RequestParam String pais) {
        List<Hotel> hoteles = hotelService.buscarHotelesPorPais(pais);
        return ResponseEntity.ok(hoteles);
    }

    /**
     * Busca hoteles por nombre o descripción.
     *
     * @param busqueda Término de búsqueda
     * @return Lista de hoteles que coinciden con la búsqueda
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar hoteles por criterios")
    public ResponseEntity<List<Hotel>> buscarHoteles(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String ciudad,
            @RequestParam(required = false) Integer estrellas) {
        return ResponseEntity.ok(hotelService.buscarHoteles(nombre, ciudad, estrellas));
    }

    /**
     * Obtiene hoteles por rango de estrellas.
     *
     * @param minEstrellas Número mínimo de estrellas
     * @param maxEstrellas Número máximo de estrellas
     * @return Lista de hoteles dentro del rango de estrellas
     */
    @GetMapping("/buscar/estrellas")
    @Operation(summary = "Buscar hoteles por estrellas", description = "Retorna hoteles dentro de un rango de estrellas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    public ResponseEntity<List<Hotel>> buscarHotelesPorEstrellas(
            @Parameter(description = "Número mínimo de estrellas") @RequestParam Integer minEstrellas,
            @Parameter(description = "Número máximo de estrellas") @RequestParam Integer maxEstrellas) {
        List<Hotel> hoteles = hotelService.buscarHotelesPorEstrellas(minEstrellas, maxEstrellas);
        return ResponseEntity.ok(hoteles);
    }

    /**
     * Desactiva un hotel del sistema.
     *
     * @param id ID del hotel a desactivar
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un hotel")
    public ResponseEntity<Void> eliminarHotel(@PathVariable Long id) {
        hotelService.eliminarHotel(id);
        return ResponseEntity.ok().build();
    }
} 