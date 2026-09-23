package com.alfredodev.miniproyectos8.dto;

import jakarta.validation.constraints.NotNull;

public record AjusteStockRequest(@NotNull Integer stock) {
}
