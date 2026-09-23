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
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiError.de(409, HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> manejarCredencialesInvalidas(BadCredentialsException ex) {
        // Mensaje generico a proposito: no revelar si el email existe o no (evita enumeracion de usuarios).
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiError.de(401, HttpStatus.UNAUTHORIZED.getReasonPhrase(), "Credenciales invalidas"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> manejarValidacion(MethodArgumentNotValidException ex) {
        List<String> detalles = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiError.de(400, HttpStatus.BAD_REQUEST.getReasonPhrase(), "Datos invalidos", detalles));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> manejarGenerico(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiError.de(500,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Ocurrio un error inesperado"));
    }
}
