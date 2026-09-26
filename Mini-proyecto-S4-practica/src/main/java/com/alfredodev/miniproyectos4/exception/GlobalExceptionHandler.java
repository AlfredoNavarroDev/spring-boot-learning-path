package com.alfredodev.miniproyectos4.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Un solo punto de manejo de errores para toda la API - equivalente a un
 * ExceptionFilter global en NestJS ({@literal @Catch()} + APP_FILTER).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<ApiError> manejarNoEncontrado(ProductoNoEncontradoException ex) {
        // TODO (Paso 2): devolver 404 con ApiError.de(404, HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage())
        throw new UnsupportedOperationException("TODO");
    }

    @ExceptionHandler(ProductoDuplicadoException.class)
    public ResponseEntity<ApiError> manejarDuplicado(ProductoDuplicadoException ex) {
        // TODO (Paso 2): devolver 409 con ApiError.de(409, HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage())
        throw new UnsupportedOperationException("TODO");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> manejarValidacion(MethodArgumentNotValidException ex) {
        // TODO (Paso 2): armar List<String> detalles mapeando ex.getBindingResult().getFieldErrors()
        // a "campo: mensaje", y devolver 400 con ApiError.de(400, ..., "Datos invalidos", detalles)
        throw new UnsupportedOperationException("TODO");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> manejarGenerico(Exception ex) {
        // TODO (Paso 2): devolver 500 con un mensaje generico (nunca el mensaje/stacktrace interno de ex)
        throw new UnsupportedOperationException("TODO");
    }
}
