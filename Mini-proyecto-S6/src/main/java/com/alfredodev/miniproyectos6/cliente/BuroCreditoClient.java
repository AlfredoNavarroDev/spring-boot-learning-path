package com.alfredodev.miniproyectos6.cliente;

import com.alfredodev.miniproyectos6.dto.ScoreCrediticioResponse;

/**
 * Puerto (DIP): el service depende de esta interfaz, no de la implementacion concreta
 * que llama al proveedor real. Permite mockearla en tests y cambiar de proveedor sin
 * tocar BuroCreditoService.
 */
public interface BuroCreditoClient {

    ScoreCrediticioResponse consultar(String dni);
}
