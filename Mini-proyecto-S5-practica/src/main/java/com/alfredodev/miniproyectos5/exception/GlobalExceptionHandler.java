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
        // TODO Paso 3: devolver 404 con ApiError.de(404, HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage())
        throw new UnsupportedOperationException("TODO");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> manejarValidacion(MethodArgumentNotValidException ex) {
        // TODO Paso 3: mapear ex.getBindingResult().getFieldErrors() a List<String> "campo: mensaje"
        // y devolver 400 con ApiError.de(400, ..., "Datos invalidos", detalles)
        throw new UnsupportedOperationException("TODO");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> manejarGenerico(Exception ex) {
        // TODO Paso 3: devolver 500 con ApiError.de(500, ..., "Ocurrio un error inesperado")
        throw new UnsupportedOperationException("TODO");
    }
}
