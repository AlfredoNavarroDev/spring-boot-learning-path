package com.alfredodev.miniproyectos4.dto;

import java.util.List;

/** Envoltorio simple de paginacion para el listado en memoria (sin Spring Data todavia). */
public record PaginaResponse<T>(List<T> contenido, int pagina, int tamanio, long total) {
}
