package com.alfredodev.miniproyectos4.exception;

import java.time.Instant;
import java.util.List;

/** Forma consistente de error para toda la API, equivalente al ExceptionFilter de Nest. */
public record ApiError(Instant timestamp, int status, String error, String message, List<String> detalles) {

    public static ApiError de(int status, String error, String message) {
        return new ApiError(Instant.now(), status, error, message, List.of());
    }

    public static ApiError de(int status, String error, String message, List<String> detalles) {
        return new ApiError(Instant.now(), status, error, message, detalles);
    }
}
