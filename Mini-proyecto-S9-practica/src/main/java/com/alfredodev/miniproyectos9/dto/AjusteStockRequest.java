package com.alfredodev.miniproyectos9.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AjusteStockRequest(@NotNull @PositiveOrZero Integer stock) {
}
