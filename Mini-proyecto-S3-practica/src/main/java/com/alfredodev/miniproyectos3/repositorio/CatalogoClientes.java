package com.alfredodev.miniproyectos3.repositorio;

import com.alfredodev.miniproyectos3.modelo.Cliente;

import java.util.Map;
import java.util.Optional;

/**
 * Catálogo en memoria de clientes conocidos.
 *
 * Demuestra la regla de oro de {@code Optional}: se usa SOLO como valor de
 * retorno (nunca como campo). {@code buscar} devuelve {@code Optional<Cliente>}
 * en lugar de {@code null} + {@code if (x != null)}.
 */
public class CatalogoClientes {

    private final Map<String, Cliente> porNombre;

    public CatalogoClientes(Map<String, Cliente> porNombre) {
        this.porNombre = Map.copyOf(porNombre);
    }

    public Optional<Cliente> buscar(String nombre) {
        // TODO (Paso 2): buscá el cliente en el mapa `porNombre` y devolvé
        // Optional.ofNullable(...) si puede no existir, o Optional.empty()
        // explícito en el caso vacío. Nunca devuelvas null.
        throw new UnsupportedOperationException("TODO Paso 2: CatalogoClientes.buscar");
    }
}
