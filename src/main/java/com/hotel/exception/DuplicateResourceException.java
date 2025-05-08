package com.hotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción lanzada cuando se intenta crear un recurso que ya existe.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateResourceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s ya existe con %s : '%s'", resourceName, fieldName, fieldValue));
    }
} 