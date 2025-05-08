package com.hotel.exception;

public class DatosBancariosNoEncontradosException extends RuntimeException {
    public DatosBancariosNoEncontradosException(String message) {
        super(message);
    }
} 