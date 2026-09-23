package com.alfredodev.miniproyectos9.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductoRequest(
        @NotBlank String nombre,
        @NotNull @Positive BigDecimal precio,
        @NotNull @PositiveOrZero Integer stock,
        @NotNull Long categoriaId,
        @NotNull Long sedeId) {
}
