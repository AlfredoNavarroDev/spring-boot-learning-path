package com.alfredodev.miniproyectos5.dto;

import java.math.BigDecimal;

import com.alfredodev.miniproyectos5.domain.Producto;

public record ProductoResponse(Long id, String nombre, BigDecimal precio, Integer stock, String categoria,
        String sede) {

    public static ProductoResponse desde(Producto producto) {
        // Acceder a categoria.getNombre()/sede.getNombre() aca dispara el fetch LAZY dentro
        // de la transaccion del service (ver @Transactional en ProductoService) - evita el
        // anti-patron de open-in-view.
        return new ProductoResponse(producto.getId(), producto.getNombre(), producto.getPrecio(),
                producto.getStock(), producto.getCategoria().getNombre(), producto.getSede().getNombre());
    }
}
