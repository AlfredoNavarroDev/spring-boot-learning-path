package com.alfredodev.miniproyectos7.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailDuplicadoException.class)
    public ResponseEntity<ApiError> manejarDuplicado(EmailDuplicadoException ex) {
        // TODO (Paso 6): devolver 409 con ApiError.de(409, HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage())
        throw new UnsupportedOperationException("TODO");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> manejarCredencialesInvalidas(BadCredentialsException ex) {
        // TODO (Paso 6): devolver 401 con un mensaje GENERICO ("Credenciales invalidas"), nunca ex.getMessage()
        // ni revelar si el email existe o no (evita enumeracion de usuarios): ApiError.de(401,
        // HttpStatus.UNAUTHORIZED.getReasonPhrase(), "Credenciales invalidas")
        throw new UnsupportedOperationException("TODO");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> manejarValidacion(MethodArgumentNotValidException ex) {
        // TODO (Paso 6): armar List<String> detalles mapeando ex.getBindingResult().getFieldErrors()
        // a "campo: mensaje", y devolver 400 con ApiError.de(400, ..., "Datos invalidos", detalles)
        throw new UnsupportedOperationException("TODO");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> manejarGenerico(Exception ex) {
        // TODO (Paso 6): devolver 500 con un mensaje generico (nunca el mensaje/stacktrace interno de ex)
        throw new UnsupportedOperationException("TODO");
    }
}
