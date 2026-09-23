package com.alfredodev.miniproyectos8.dto;

import com.alfredodev.miniproyectos8.domain.Producto;

public record ProductoResponse(Long id, String nombre, Integer stock) {

    public static ProductoResponse desde(Producto producto) {
        return new ProductoResponse(producto.getId(), producto.getNombre(), producto.getStock());
    }
}
