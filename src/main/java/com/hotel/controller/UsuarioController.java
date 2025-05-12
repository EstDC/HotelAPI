package com.hotel.controller;

import com.hotel.model.Usuario;
import com.hotel.model.Rol;
import com.hotel.service.UsuarioService;
import com.hotel.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador REST para la gestión de usuarios.
 * Proporciona endpoints para la gestión de usuarios del sistema.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "API para la gestión de usuarios")
public class UsuarioController {
    
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtTokenProvider tokenProvider;

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
    @Operation(
        summary = "Actualizar usuario", 
        description = "Actualiza los datos de un usuario existente",
        security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "No tiene permiso para acceder a este recurso"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> actualizarUsuario(
            @Parameter(description = "ID del usuario") @PathVariable Long id,
            @Parameter(description = "Datos actualizados del usuario") @RequestBody Usuario usuario,
            HttpServletRequest request) {
        try {
            logger.debug("=== INICIO ACTUALIZACIÓN USUARIO ===");
            logger.debug("ID usuario a actualizar: {}", id);
            logger.debug("Datos recibidos: {}", usuario);
            logger.debug("Contraseña recibida: {}", usuario.getPassword());
            logger.debug("Cuerpo de la petición: {}", request.getReader().lines().collect(Collectors.joining()));
            
            String token = request.getHeader("Authorization");
            if (token == null || !token.startsWith("Bearer ")) {
                logger.warn("Token no encontrado o formato inválido");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            token = token.substring(7);
            String email = tokenProvider.getEmailFromToken(token);
            logger.debug("Email del usuario que realiza la actualización: {}", email);

            Usuario requestingUser = usuarioService.obtenerUsuarioPorEmail(email);
            if (requestingUser == null || !requestingUser.isActivo()) {
                logger.warn("Usuario no encontrado o inactivo: {}", email);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Verificar permisos
            if (!requestingUser.getId().equals(id) && requestingUser.getRol() != Rol.ADMIN) {
                logger.warn("Usuario no autorizado para actualizar: {}", email);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            // Obtener el usuario actual
            Usuario usuarioActual = usuarioService.obtenerUsuarioPorId(id);
            if (usuarioActual == null) {
                logger.warn("Usuario a actualizar no encontrado: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            logger.debug("Usuario actual en BD: {}", usuarioActual);
            logger.debug("Nueva contraseña recibida: {}", usuario.getPassword());

            // Actualizar los campos permitidos
            usuarioActual.setNombre(usuario.getNombre());
            usuarioActual.setApellido(usuario.getApellido());
            usuarioActual.setEmail(usuario.getEmail());
            usuarioActual.setTelefono(usuario.getTelefono());
            usuarioActual.setUltimaModificacion(LocalDateTime.now());
            
            // Actualizar la contraseña si se proporciona una nueva
            if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                logger.debug("Actualizando contraseña para usuario: {}", email);
                usuarioActual.setPassword(usuario.getPassword());
                logger.debug("Contraseña actualizada en objeto: {}", usuarioActual.getPassword());
            } else {
                logger.debug("No se actualiza la contraseña porque es null o está vacía");
            }

            // Asegurarnos de que todos los campos necesarios estén establecidos
            usuarioActual.setActivo(true);
            usuarioActual.setRol(usuarioActual.getRol()); // Mantener el rol actual
            usuarioActual.setFechaRegistro(usuarioActual.getFechaRegistro()); // Mantener la fecha de registro

            logger.debug("Datos a enviar al servicio: {}", usuarioActual);
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuarioActual);
            logger.debug("Usuario actualizado por el servicio: {}", usuarioActualizado);
            
            // No enviar la contraseña en la respuesta
            usuarioActualizado.setPassword(null);
            logger.debug("=== FIN ACTUALIZACIÓN USUARIO ===");
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            logger.error("Error al actualizar usuario: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario
     * @return Usuario encontrado
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener usuario por ID", 
        description = "Retorna los datos de un usuario específico",
        security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "No tiene permiso para acceder a este recurso"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> obtenerUsuarioPorId(
            @Parameter(description = "ID del usuario") @PathVariable Long id,
            HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String email = tokenProvider.getEmailFromToken(token);
            Usuario requestingUser = usuarioService.obtenerUsuarioPorEmail(email);
            Usuario requestedUser = usuarioService.obtenerUsuarioPorId(id);
            
            // Allow access if user is requesting their own data or is an admin
            if (requestingUser != null && 
                (requestingUser.getId().equals(id) || requestingUser.getRol() == Rol.ADMIN)) {
                return ResponseEntity.ok(requestedUser);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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

    /**
     * Obtiene el perfil del usuario autenticado.
     *
     * @param request Request HTTP
     * @return Datos del usuario autenticado
     */
    @GetMapping("/perfil")
    @Operation(
        summary = "Obtener perfil de usuario", 
        description = "Retorna los datos del usuario autenticado",
        security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perfil obtenido exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> obtenerPerfil(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (token == null || !token.startsWith("Bearer ")) {
                logger.warn("Token no encontrado o formato inválido");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            token = token.substring(7);
            
            // Validar el token antes de extraer el email
            if (!tokenProvider.validateToken(token)) {
                logger.warn("Token inválido o expirado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String email = tokenProvider.getEmailFromToken(token);
            logger.debug("Obteniendo perfil para usuario: {}", email);

            Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
            if (usuario == null) {
                logger.warn("Usuario no encontrado: {}", email);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (!usuario.isActivo()) {
                logger.warn("Usuario inactivo: {}", email);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // No enviar la contraseña en la respuesta
            usuario.setPassword(null);

            logger.debug("Perfil obtenido exitosamente para usuario: {}", email);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            logger.error("Error al obtener perfil: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/perfil")
    @Operation(
        summary = "Actualizar perfil de usuario", 
        description = "Actualiza los datos del usuario autenticado",
        security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perfil actualizado exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> actualizarPerfil(
            @Parameter(description = "Datos actualizados del usuario") @RequestBody Usuario usuarioDatos,
            HttpServletRequest request) {
        try {
            logger.info("=== INICIO ACTUALIZACIÓN PERFIL ===");
            logger.info("Datos recibidos: {}", usuarioDatos);
            
            String token = request.getHeader("Authorization");
            if (token == null || !token.startsWith("Bearer ")) {
                logger.warn("Token no encontrado o formato inválido");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            token = token.substring(7);
            String email = tokenProvider.getEmailFromToken(token);
            logger.info("Email del usuario que actualiza su perfil: {}", email);

            Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
            if (usuario == null) {
                logger.warn("Usuario no encontrado: {}", email);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (!usuario.isActivo()) {
                logger.warn("Usuario inactivo: {}", email);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            logger.info("Usuario actual en BD: {}", usuario);
            logger.info("Nueva contraseña recibida: {}", usuarioDatos.getPassword());

            // Actualizar solo los campos permitidos
            usuario.setNombre(usuarioDatos.getNombre());
            usuario.setApellido(usuarioDatos.getApellido());
            usuario.setTelefono(usuarioDatos.getTelefono());
            usuario.setUltimaModificacion(LocalDateTime.now());
            
            if (usuarioDatos.getPassword() != null && !usuarioDatos.getPassword().isEmpty()) {
                logger.info("Actualizando contraseña para usuario: {}", email);
                usuario.setPassword(usuarioDatos.getPassword());
                logger.info("Contraseña actualizada en objeto: {}", usuario.getPassword());
            }

            logger.info("Datos a enviar al servicio: {}", usuario);
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuario.getId(), usuario);
            logger.info("Usuario actualizado por el servicio: {}", usuarioActualizado);
            
            // No enviar la contraseña en la respuesta
            usuarioActualizado.setPassword(null);
            logger.info("=== FIN ACTUALIZACIÓN PERFIL ===");
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            logger.error("Error al actualizar perfil: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 