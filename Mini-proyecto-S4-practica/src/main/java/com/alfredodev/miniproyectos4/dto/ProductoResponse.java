package com.alfredodev.miniproyectos4.dto;

import java.math.BigDecimal;

import com.alfredodev.miniproyectos4.domain.Producto;

/** DTO de salida: nunca devolvemos la entity/dominio directo desde el controller. */
public record ProductoResponse(Long id, String nombre, String categoria, BigDecimal precio, Integer stock) {

    public static ProductoResponse desde(Producto producto) {
        return new ProductoResponse(producto.getId(), producto.getNombre(), producto.getCategoria(),
                producto.getPrecio(), producto.getStock());
    }
}
