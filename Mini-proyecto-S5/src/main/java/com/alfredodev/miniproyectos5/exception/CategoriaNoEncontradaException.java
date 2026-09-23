package com.alfredodev.miniproyectos5.exception;

public class CategoriaNoEncontradaException extends RuntimeException {
    public CategoriaNoEncontradaException(Long id) {
        super("No se encontro la categoria con id " + id);
    }
}
