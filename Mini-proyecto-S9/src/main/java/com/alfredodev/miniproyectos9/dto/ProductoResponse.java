package com.alfredodev.miniproyectos9.dto;

import java.math.BigDecimal;

import com.alfredodev.miniproyectos9.domain.Producto;

public record ProductoResponse(Long id, String nombre, BigDecimal precio, Integer stock, String categoria,
        String sede) {

    public static ProductoResponse desde(Producto producto) {
        return new ProductoResponse(producto.getId(), producto.getNombre(), producto.getPrecio(),
                producto.getStock(), producto.getCategoria().getNombre(), producto.getSede().getNombre());
    }
}
