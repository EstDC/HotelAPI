package com.hotel.controller;

import com.hotel.model.Usuario;
import com.hotel.model.Rol;
import com.hotel.service.UsuarioService;
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
 * Controlador REST para la gestión de usuarios.
 * Proporciona endpoints para la gestión de usuarios del sistema.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuario", description = "API para la gestión de usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param usuario Datos del usuario a registrar
     * @return Usuario registrado
     */
    @PostMapping
    @Operation(summary = "Registrar nuevo usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos"),
        @ApiResponse(responseCode = "409", description = "El email ya está registrado")
    })
    public ResponseEntity<Usuario> registrarUsuario(
            @Parameter(description = "Datos del usuario a registrar") @RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param id ID del usuario
     * @param usuario Datos actualizados del usuario
     * @return Usuario actualizado
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> actualizarUsuario(
            @Parameter(description = "ID del usuario") @PathVariable Long id,
            @Parameter(description = "Datos actualizados del usuario") @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario
     * @return Usuario encontrado
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Retorna los datos de un usuario específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> obtenerUsuarioPorId(
            @Parameter(description = "ID del usuario") @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    /**
     * Elimina un usuario (soft delete).
     *
     * @param id ID del usuario
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema (soft delete)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> eliminarUsuario(
            @Parameter(description = "ID del usuario") @PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene todos los usuarios activos.
     *
     * @return Lista de usuarios activos
     */
    @GetMapping("/activos")
    @Operation(summary = "Listar usuarios activos", description = "Retorna todos los usuarios activos del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    })
    public ResponseEntity<List<Usuario>> obtenerUsuariosActivos() {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosActivos();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtiene usuarios por rol.
     *
     * @param rol Rol de los usuarios a buscar
     * @return Lista de usuarios con el rol especificado
     */
    @GetMapping("/rol/{rol}")
    @Operation(summary = "Listar usuarios por rol", description = "Retorna los usuarios con un rol específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    })
    public ResponseEntity<List<Usuario>> obtenerUsuariosPorRol(
            @Parameter(description = "Rol de los usuarios") @PathVariable Rol rol) {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosPorRol(rol);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Verifica si un email está disponible.
     *
     * @param email Email a verificar
     * @return true si el email está disponible, false en caso contrario
     */
    @GetMapping("/verificar-email")
    @Operation(summary = "Verificar email", description = "Verifica si un email está disponible para registro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Verificación realizada exitosamente")
    })
    public ResponseEntity<Boolean> verificarEmailDisponible(
            @Parameter(description = "Email a verificar") @RequestParam String email) {
        boolean disponible = !usuarioService.existeEmail(email);
        return ResponseEntity.ok(disponible);
    }
} 