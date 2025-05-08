package com.hotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción lanzada cuando un usuario intenta acceder a un recurso sin autorización.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String resourceName, String action) {
        super(String.format("No tiene autorización para %s en %s", action, resourceName));
    }
} 