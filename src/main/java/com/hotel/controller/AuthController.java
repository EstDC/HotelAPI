package com.hotel.controller;

import com.hotel.model.Usuario;
import com.hotel.service.UsuarioService;
import com.hotel.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controlador REST para la autenticación de usuarios.
 * Maneja el registro y login de usuarios.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Autenticación", description = "API para la autenticación de usuarios")
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    /**
     * Autentica un usuario en el sistema.
     *
     * @param credenciales Credenciales del usuario (email y password)
     * @return Token JWT y datos del usuario autenticado
     */
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    public ResponseEntity<Map<String, Object>> login(
            @Parameter(description = "Credenciales del usuario") @RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");

        if (email == null || password == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Email y password son requeridos");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            Usuario usuario = usuarioService.validarCredenciales(email, password);
            if (usuario == null || !usuario.isActivo()) {
                Map<String, Object> response = new HashMap<>();
                response.put("error", "Credenciales inválidas");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            String token = tokenProvider.generateToken(usuario);
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", usuario);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Credenciales inválidas");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param usuario Datos del usuario a registrar
     * @return Usuario registrado
     */
    @PostMapping("/registro")
    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos"),
        @ApiResponse(responseCode = "409", description = "El email ya está registrado")
    })
    public ResponseEntity<Usuario> registrarUsuario(
            @Parameter(description = "Datos del usuario") @RequestBody Usuario usuario) {
        Usuario usuarioRegistrado = usuarioService.crearUsuario(usuario);
        return new ResponseEntity<>(usuarioRegistrado, HttpStatus.CREATED);
    }

    /**
     * Solicita la recuperación de contraseña.
     *
     * @param email Email del usuario
     * @return Mensaje de confirmación
     */
    @PostMapping("/recuperar-password")
    @Operation(summary = "Recuperar contraseña", description = "Solicita la recuperación de contraseña")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitud procesada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Map<String, String>> recuperarPassword(
            @Parameter(description = "Email del usuario") @RequestParam String email) {
        String token = usuarioService.generarTokenRecuperacion(email);
        
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Se ha enviado un correo con las instrucciones para recuperar la contraseña");
        response.put("token", token); // En producción, esto debería enviarse por correo
        
        return ResponseEntity.ok(response);
    }

    /**
     * Cambia la contraseña usando un token de recuperación.
     *
     * @param token Token de recuperación
     * @param nuevaPassword Nueva contraseña
     * @return Mensaje de confirmación
     */
    @PostMapping("/cambiar-password")
    @Operation(summary = "Cambiar contraseña", description = "Cambia la contraseña usando un token de recuperación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contraseña cambiada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Token inválido o expirado")
    })
    public ResponseEntity<Map<String, String>> cambiarPassword(
            @Parameter(description = "Token de recuperación") @RequestParam String token,
            @Parameter(description = "Nueva contraseña") @RequestParam String nuevaPassword) {
        usuarioService.cambiarPassword(token, nuevaPassword);
        
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Contraseña cambiada exitosamente");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene el perfil del usuario autenticado.
     *
     * @param request HttpServletRequest para obtener el token
     * @return Datos del usuario autenticado
     */
    @GetMapping("/perfil")
    @Operation(
        summary = "Obtener perfil", 
        description = "Retorna los datos del usuario autenticado",
        security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perfil obtenido exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<Usuario> obtenerPerfil(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String email = tokenProvider.getEmailFromToken(token);
            Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
            if (usuario != null && usuario.isActivo()) {
                return ResponseEntity.ok(usuario);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
} 