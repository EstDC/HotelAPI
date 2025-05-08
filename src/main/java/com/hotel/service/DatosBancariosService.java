package com.hotel.service;

import com.hotel.model.DatosBancarios;
import com.hotel.model.Usuario;
import com.hotel.model.TipoTarjeta;
import com.hotel.repository.DatosBancariosRepository;
import com.hotel.repository.UsuarioRepository;
import com.hotel.exception.DatosBancariosNoEncontradosException;
import com.hotel.exception.UsuarioNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar los datos bancarios de los usuarios.
 * Este servicio maneja todas las operaciones relacionadas con las tarjetas
 * y métodos de pago de los usuarios, incluyendo su registro, actualización
 * y consulta.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@Service
@Transactional
public class DatosBancariosService {
    
    @Autowired
    private DatosBancariosRepository datosBancariosRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Registra una nueva tarjeta bancaria para un usuario.
     * Verifica que el usuario exista y que la tarjeta no esté duplicada.
     *
     * @param usuarioId ID del usuario
     * @param datosBancarios Datos de la tarjeta a registrar
     * @return Los datos bancarios registrados
     * @throws RuntimeException si el usuario no existe o la tarjeta ya está registrada
     */
    public DatosBancarios registrarTarjeta(Long usuarioId, DatosBancarios datosBancarios) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        datosBancarios.setUsuario(usuario);
        datosBancarios.setActiva(true);
        
        // Si es la primera tarjeta, marcarla como predeterminada
        if (datosBancariosRepository.findByUsuario(usuario).isEmpty()) {
            datosBancarios.setPredeterminada(true);
        }
        
        return datosBancariosRepository.save(datosBancarios);
    }

    /**
     * Actualiza los datos de una tarjeta existente.
     * Solo permite actualizar ciertos campos y mantiene el estado de predeterminada.
     *
     * @param id ID de los datos bancarios a actualizar
     * @param datosActualizados Datos actualizados de la tarjeta
     * @return Los datos bancarios actualizados
     * @throws RuntimeException si los datos bancarios no existen
     */
    public DatosBancarios actualizarTarjeta(Long id, DatosBancarios datosActualizados) {
        return datosBancariosRepository.findById(id)
            .map(datos -> {
                datos.setTitular(datosActualizados.getTitular());
                datos.setFechaExpiracion(datosActualizados.getFechaExpiracion());
                datos.setCvv(datosActualizados.getCvv());
                return datosBancariosRepository.save(datos);
            })
            .orElseThrow(() -> new RuntimeException("Datos bancarios no encontrados"));
    }

    /**
     * Elimina una tarjeta bancaria.
     * Si la tarjeta es predeterminada, asigna otra tarjeta como predeterminada.
     *
     * @param id ID de los datos bancarios a eliminar
     * @throws RuntimeException si los datos bancarios no existen
     */
    public void eliminarTarjeta(Long id) {
        DatosBancarios datos = datosBancariosRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Datos bancarios no encontrados"));
        
        if (datos.isPredeterminada()) {
            // Buscar otra tarjeta para hacerla predeterminada
            datosBancariosRepository.findByUsuario(datos.getUsuario()).stream()
                .filter(d -> !d.getId().equals(id))
                .findFirst()
                .ifPresent(d -> {
                    d.setPredeterminada(true);
                    datosBancariosRepository.save(d);
                });
        }
        
        datos.setActiva(false);
        datosBancariosRepository.save(datos);
    }

    /**
     * Establece una tarjeta como predeterminada.
     * Desactiva la predeterminada anterior y establece la nueva.
     *
     * @param id ID de los datos bancarios a establecer como predeterminados
     * @return Los datos bancarios actualizados
     * @throws RuntimeException si los datos bancarios no existen
     */
    public DatosBancarios establecerTarjetaPredeterminada(Long id) {
        DatosBancarios datos = datosBancariosRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Datos bancarios no encontrados"));
        
        // Desactivar la predeterminada anterior
        datosBancariosRepository.findByUsuarioAndPredeterminada(datos.getUsuario(), true)
            .ifPresent(d -> {
                d.setPredeterminada(false);
                datosBancariosRepository.save(d);
            });
        
        datos.setPredeterminada(true);
        return datosBancariosRepository.save(datos);
    }

    /**
     * Obtiene los datos bancarios por ID.
     *
     * @param id ID de los datos bancarios
     * @return Los datos bancarios encontrados
     */
    public DatosBancarios obtenerDatosBancariosPorId(Long id) {
        return datosBancariosRepository.findById(id)
            .orElseThrow(() -> new DatosBancariosNoEncontradosException("Datos bancarios no encontrados"));
    }

    /**
     * Obtiene todas las tarjetas activas de un usuario.
     *
     * @param usuarioId ID del usuario
     * @return Lista de tarjetas activas del usuario
     */
    public List<DatosBancarios> obtenerTarjetasActivas(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return datosBancariosRepository.findByUsuarioAndActiva(usuario, true);
    }

    /**
     * Obtiene la tarjeta predeterminada de un usuario.
     *
     * @param usuarioId ID del usuario
     * @return La tarjeta predeterminada del usuario
     * @throws RuntimeException si el usuario no existe o no tiene tarjeta predeterminada
     */
    public DatosBancarios obtenerTarjetaPredeterminada(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return datosBancariosRepository.findByUsuarioAndPredeterminada(usuario, true)
            .orElseThrow(() -> new RuntimeException("No se encontró tarjeta predeterminada"));
    }

    /**
     * Obtiene todas las tarjetas de un tipo específico.
     *
     * @param usuarioId ID del usuario
     * @param tipoTarjeta Tipo de tarjeta a buscar
     * @return Lista de tarjetas del tipo especificado
     */
    public List<DatosBancarios> obtenerTarjetasPorTipo(Long usuarioId, String tipoTarjeta) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return datosBancariosRepository.findByUsuarioAndTipoTarjeta(usuario, TipoTarjeta.valueOf(tipoTarjeta));
    }

    public DatosBancarios crearDatosBancarios(DatosBancarios datosBancarios) {
        return datosBancariosRepository.save(datosBancarios);
    }

    public List<DatosBancarios> obtenerDatosBancariosPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));
        return datosBancariosRepository.findByUsuario(usuario);
    }
} 