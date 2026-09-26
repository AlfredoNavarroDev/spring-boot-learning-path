package com.alfredodev.miniproyectos4.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * DTO de entrada. No lleva id: lo asigna el repositorio al crear.
 * Equivalente al CreateProductoDto / UpdateProductoDto de un CRUD en NestJS con class-validator.
 */
public record ProductoRequest(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @NotBlank(message = "La categoria es obligatoria") String categoria,
        @NotNull(message = "El precio es obligatorio") @Positive(message = "El precio debe ser mayor a 0") BigDecimal precio,
        @NotNull(message = "El stock es obligatorio") @PositiveOrZero(message = "El stock no puede ser negativo") Integer stock) {
}
