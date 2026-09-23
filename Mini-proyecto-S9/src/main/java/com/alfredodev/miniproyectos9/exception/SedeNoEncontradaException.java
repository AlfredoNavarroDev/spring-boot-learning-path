package com.alfredodev.miniproyectos9.exception;

public class SedeNoEncontradaException extends RuntimeException {
    public SedeNoEncontradaException(Long id) {
        super("No se encontro la sede con id " + id);
    }
}
