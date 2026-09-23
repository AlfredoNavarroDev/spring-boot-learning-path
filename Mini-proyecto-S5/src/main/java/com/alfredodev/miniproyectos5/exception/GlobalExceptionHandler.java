package com.alfredodev.miniproyectos5.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ ProductoNoEncontradoException.class, CategoriaNoEncontradaException.class,
            SedeNoEncontradaException.class })
    public ResponseEntity<ApiError> manejarNoEncontrado(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiError.de(404, HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage()));
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
