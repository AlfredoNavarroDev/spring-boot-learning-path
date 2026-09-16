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
        return Optional.ofNullable(porNombre.get(nombre));
    }
}
