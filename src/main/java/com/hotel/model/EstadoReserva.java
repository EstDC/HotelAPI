package com.hotel.model;

public enum EstadoReserva {
    PENDIENTE,      // Reserva creada pero no confirmada
    CONFIRMADA,     // Reserva confirmada y pagada
    EN_CURSO,       // Cliente está actualmente en el hotel
    COMPLETADA,     // Cliente ya se ha ido
    CANCELADA,      // Reserva cancelada
    NO_SHOW         // Cliente no se presentó
} 