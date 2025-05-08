package com.hotel.model;

/**
 * Enum que define los roles de usuario en el sistema.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
public enum Rol {
    /**
     * Usuario público sin registro.
     * Solo puede realizar búsquedas de hoteles y habitaciones.
     */
    PUBLICO,

    /**
     * Usuario registrado.
     * Puede realizar reservas, gestionar sus datos y ver su historial.
     */
    CLIENTE,

    /**
     * Administrador del sistema.
     * Tiene acceso completo a todas las funcionalidades.
     */
    ADMIN
} 