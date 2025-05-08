package com.hotel.repository;

import com.hotel.model.Usuario;
import com.hotel.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de usuarios en la base de datos.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su email.
     *
     * @param email Email del usuario
     * @return Usuario encontrado
     */
    Usuario findByEmail(String email);

    /**
     * Lista todos los usuarios activos.
     *
     * @return Lista de usuarios activos
     */
    List<Usuario> findByActivoTrue();

    /**
     * Lista usuarios por rol y estado activo.
     *
     * @param rol Rol a filtrar
     * @return Lista de usuarios con el rol especificado y activos
     */
    List<Usuario> findByRolAndActivoTrue(Rol rol);

    /**
     * Busca usuarios por nombre o apellido que contengan el texto especificado.
     *
     * @param nombre Nombre o apellido a buscar
     * @return Lista de usuarios que coinciden con la búsqueda
     */
    List<Usuario> findByNombreContainingOrApellidoContaining(String nombre, String apellido);

    /**
     * Busca usuarios activos por nombre o apellido que contengan el texto especificado.
     *
     * @param nombre Nombre o apellido a buscar
     * @return Lista de usuarios activos que coinciden con la búsqueda
     */
    List<Usuario> findByNombreContainingOrApellidoContainingAndActivoTrue(String nombre, String apellido);

    /**
     * Busca usuarios por rol y nombre o apellido que contengan el texto especificado.
     *
     * @param rol Rol a filtrar
     * @param nombre Nombre o apellido a buscar
     * @return Lista de usuarios que coinciden con la búsqueda
     */
    List<Usuario> findByRolAndNombreContainingOrApellidoContaining(Rol rol, String nombre, String apellido);

    /**
     * Busca usuarios activos por rol y nombre o apellido que contengan el texto especificado.
     *
     * @param rol Rol a filtrar
     * @param nombre Nombre o apellido a buscar
     * @return Lista de usuarios activos que coinciden con la búsqueda
     */
    List<Usuario> findByRolAndNombreContainingOrApellidoContainingAndActivoTrue(Rol rol, String nombre, String apellido);

    /**
     * Busca un usuario por su token de recuperación.
     *
     * @param token Token de recuperación
     * @return Usuario encontrado
     */
    Optional<Usuario> findByTokenRecuperacion(String token);

    /**
     * Verifica si existe un usuario con el email especificado.
     *
     * @param email Email a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByEmail(String email);

    /**
     * Busca un usuario por su email y contraseña, asegurando que esté activo.
     *
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return Usuario encontrado
     */
    Usuario findByEmailAndPasswordAndActivoTrue(String email, String password);

    /**
     * Lista usuarios por rol.
     *
     * @param rol Rol a filtrar
     * @return Lista de usuarios con el rol especificado
     */
    List<Usuario> findByRol(Rol rol);
} 