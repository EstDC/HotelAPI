package com.hotel.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Clase que representa una respuesta de error de validación.
 * Incluye un mapa de errores por campo.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@Getter
@Setter
public class ValidationErrorResponse extends ErrorResponse {
    private Map<String, String> errors;

    public ValidationErrorResponse(int status, String message, LocalDateTime timestamp, Map<String, String> errors) {
        super(status, message, timestamp);
        this.errors = errors;
    }
} 