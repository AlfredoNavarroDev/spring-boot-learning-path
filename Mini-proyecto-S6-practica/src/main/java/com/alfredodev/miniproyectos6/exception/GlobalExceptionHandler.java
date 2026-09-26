package com.alfredodev.miniproyectos6.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> manejarValidacion(MethodArgumentNotValidException ex) {
        // TODO (Paso 6): armá List<String> detalles mapeando
        // ex.getBindingResult().getFieldErrors() a "campo: mensaje", y devolvé 400 con
        // ApiError.de(400, HttpStatus.BAD_REQUEST.getReasonPhrase(), "Datos invalidos", detalles).
        throw new UnsupportedOperationException("TODO Paso 6: GlobalExceptionHandler.manejarValidacion");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> manejarGenerico(Exception ex) {
        // TODO (Paso 6): devolvé 500 con un mensaje genérico (nunca el mensaje/stacktrace
        // interno de ex) usando ApiError.de(500, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Ocurrio un error inesperado").
        throw new UnsupportedOperationException("TODO Paso 6: GlobalExceptionHandler.manejarGenerico");
    }
}
