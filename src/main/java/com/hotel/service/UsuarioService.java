package com.hotel.service;

import com.hotel.model.Usuario;
import com.hotel.model.Rol;
import com.hotel.repository.UsuarioRepository;
import com.hotel.exception.ResourceNotFoundException;
import com.hotel.exception.DuplicateResourceException;
import com.hotel.exception.UsuarioNoEncontradoException;
import com.hotel.exception.CredencialesInvalidasException;
import com.hotel.exception.EmailYaRegistradoException;
import com.hotel.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Servicio que maneja la lógica de negocio relacionada con los usuarios.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    /**
     * Registra un nuevo usuario en el sistema.
     * Por defecto, los usuarios se crean con rol CLIENTE.
     *
     * @param usuario Datos del usuario a registrar
     * @return Usuario registrado
     * @throws DuplicateResourceException si el email ya está registrado
     */
    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            throw new EmailYaRegistradoException("El email ya está registrado");
        }
        
        usuario.setRol(Rol.CLIENTE);
        usuario.setActivo(true);
        usuario.setFechaRegistro(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    /**
     * Registra un nuevo administrador en el sistema.
     * Este método solo debe ser usado por administradores existentes.
     *
     * @param usuario Datos del administrador a registrar
     * @return Administrador registrado
     * @throws DuplicateResourceException si el email ya está registrado
     */
    @Transactional
    public Usuario registrarAdmin(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new DuplicateResourceException("El email ya está registrado");
        }

        usuario.setRol(Rol.ADMIN);
        usuario.setActivo(true);
        usuario.setFechaRegistro(LocalDateTime.now());

        return usuarioRepository.save(usuario);
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param id ID del usuario a actualizar
     * @param usuarioDatos Datos actualizados del usuario
     * @return Usuario actualizado
     * @throws ResourceNotFoundException si el usuario no existe
     */
    @Transactional
    public Usuario actualizarUsuario(Long id, Usuario usuarioDatos) {
        logger.debug("=== INICIO ACTUALIZACIÓN USUARIO EN SERVICIO ===");
        logger.debug("ID usuario a actualizar: {}", id);
        logger.debug("Datos recibidos en servicio: {}", usuarioDatos);
        
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));
        
        logger.debug("Usuario actual en BD: {}", usuario);

        // Solo actualiza si el campo recibido NO es null
        if (usuarioDatos.getNombre() != null) {
            logger.debug("Actualizando nombre: {} -> {}", usuario.getNombre(), usuarioDatos.getNombre());
            usuario.setNombre(usuarioDatos.getNombre());
        }
        if (usuarioDatos.getApellido() != null) {
            logger.debug("Actualizando apellido: {} -> {}", usuario.getApellido(), usuarioDatos.getApellido());
            usuario.setApellido(usuarioDatos.getApellido());
        }
        if (usuarioDatos.getTelefono() != null) {
            logger.debug("Actualizando teléfono: {} -> {}", usuario.getTelefono(), usuarioDatos.getTelefono());
            usuario.setTelefono(usuarioDatos.getTelefono());
        }
        if (usuarioDatos.getEmail() != null) {
            logger.debug("Actualizando email: {} -> {}", usuario.getEmail(), usuarioDatos.getEmail());
            usuario.setEmail(usuarioDatos.getEmail());
        }
        if (usuarioDatos.getRol() != null) {
            logger.debug("Actualizando rol: {} -> {}", usuario.getRol(), usuarioDatos.getRol());
            usuario.setRol(usuarioDatos.getRol());
        }
        if (usuarioDatos.getPassword() != null && !usuarioDatos.getPassword().isEmpty()) {
            logger.debug("Actualizando contraseña: {} -> {}", usuario.getPassword(), usuarioDatos.getPassword());
            usuario.setPassword(usuarioDatos.getPassword());
        } else {
            logger.debug("No se actualiza la contraseña porque es null o está vacía");
        }
        
        usuario.setUltimaModificacion(LocalDateTime.now());
        logger.debug("Usuario antes de guardar: {}", usuario);
        
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        logger.debug("Usuario después de guardar: {}", usuarioGuardado);
        logger.debug("=== FIN ACTUALIZACIÓN USUARIO EN SERVICIO ===");
        
        return usuarioGuardado;
    }

    /**
     * Cambia el rol de un usuario.
     * Solo los administradores pueden cambiar roles.
     *
     * @param id ID del usuario
     * @param nuevoRol Nuevo rol a asignar
     * @return Usuario actualizado
     * @throws ResourceNotFoundException si el usuario no existe
     */
    @Transactional
    public Usuario cambiarRol(Long id, Rol nuevoRol) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));
        
        usuario.setRol(nuevoRol);
        usuario.setUltimaModificacion(LocalDateTime.now());
        
        return usuarioRepository.save(usuario);
    }

    /**
     * Realiza un borrado lógico de un usuario.
     *
     * @param id ID del usuario a desactivar
     * @throws ResourceNotFoundException si el usuario no existe
     */
    @Transactional
    public void desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));
        
        usuario.setActivo(false);
        usuario.setUltimaModificacion(LocalDateTime.now());
        
        usuarioRepository.save(usuario);
    }

    /**
     * Valida las credenciales de un usuario.
     *
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return Usuario si las credenciales son válidas
     * @throws CredencialesInvalidasException si las credenciales son inválidas
     */
    @Transactional(readOnly = true)
    public Usuario validarCredenciales(String email, String password) {
        if (email == null || password == null) {
            return null;
        }

        Usuario usuario = usuarioRepository.findByEmail(email);
        
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        
        return null;
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario
     * @return Usuario encontrado
     * @throws ResourceNotFoundException si el usuario no existe
     */
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));
    }

    /**
     * Lista todos los usuarios activos.
     *
     * @return Lista de usuarios activos
     */
    public List<Usuario> listarUsuariosActivos() {
        return usuarioRepository.findByActivoTrue();
    }

    /**
     * Lista usuarios por rol.
     *
     * @param rol Rol a filtrar
     * @return Lista de usuarios con el rol especificado
     */
    public List<Usuario> listarUsuariosPorRol(Rol rol) {
        return usuarioRepository.findByRol(rol);
    }

    /**
     * Verifica si un email está disponible para registro.
     *
     * @param email Email a verificar
     * @return true si el email está disponible, false en caso contrario
     */
    public boolean verificarEmailDisponible(String email) {
        return usuarioRepository.findByEmail(email) == null;
    }

    /**
     * Genera un token de recuperación de contraseña.
     *
     * @param email Email del usuario
     * @return Token generado
     * @throws ResourceNotFoundException si el usuario no existe
     */
    @Transactional
    public void iniciarRecuperacionPassword(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null) {
            String token = UUID.randomUUID().toString();
            usuario.setTokenRecuperacion(token);
            usuario.setTokenExpiracion(LocalDateTime.now().plusHours(24));
            usuarioRepository.save(usuario);
        }
    }

    /**
     * Cambia la contraseña de un usuario usando un token de recuperación.
     *
     * @param token Token de recuperación
     * @param nuevaPassword Nueva contraseña
     * @return true si la contraseña se cambió correctamente
     * @throws ResourceNotFoundException si el token no es válido
     */
    @Transactional
    public void cambiarPassword(String token, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findByTokenRecuperacion(token)
            .orElseThrow(() -> new CredencialesInvalidasException("Token inválido o expirado"));
        
        if (usuario.getTokenExpiracion().isBefore(LocalDateTime.now())) {
            throw new CredencialesInvalidasException("Token expirado");
        }
        
        usuario.setPassword(nuevaPassword);
        usuario.setTokenRecuperacion(null);
        usuario.setTokenExpiracion(null);
        usuario.setUltimaModificacion(LocalDateTime.now());
        
        usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        return crearUsuario(usuario);
    }

    @Transactional
    public void eliminarUsuario(Long id) {
        desactivarUsuario(id);
    }

    public List<Usuario> obtenerUsuariosActivos() {
        return listarUsuariosActivos();
    }

    public List<Usuario> obtenerUsuariosPorRol(Rol rol) {
        return listarUsuariosPorRol(rol);
    }

    public boolean existeEmail(String email) {
        return !verificarEmailDisponible(email);
    }

    @Transactional
    public String generarTokenRecuperacion(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado");
        }
        
        String token = UUID.randomUUID().toString();
        usuario.setTokenRecuperacion(token);
        usuario.setTokenExpiracion(LocalDateTime.now().plusHours(24));
        usuarioRepository.save(usuario);
        
        return token;
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public String login(String email, String password) {
        Usuario usuario = validarCredenciales(email, password);
        if (usuario != null && usuario.isActivo()) {
            return tokenProvider.generateToken(usuario);
        }
        return null;
    }
} 