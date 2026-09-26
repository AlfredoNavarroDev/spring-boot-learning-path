package com.alfredodev.miniproyectos5.exception;

public class CategoriaNoEncontradaException extends RuntimeException {
    public CategoriaNoEncontradaException(Long id) {
        // TODO Paso 3: super(...) con un mensaje que incluya el id, ej: "No se encontro la categoria con id " + id
        super((String) null);
    }
}
