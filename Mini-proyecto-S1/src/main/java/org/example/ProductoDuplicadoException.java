package org.example;

public class ProductoDuplicadoException extends RuntimeException {

    public ProductoDuplicadoException(String id) {
        super("Ya existe un producto con id: " + id);
    }
}
