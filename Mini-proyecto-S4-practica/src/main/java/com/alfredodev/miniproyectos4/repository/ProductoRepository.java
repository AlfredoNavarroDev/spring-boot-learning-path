package com.alfredodev.miniproyectos4.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.alfredodev.miniproyectos4.domain.Producto;

/**
 * Repositorio en memoria (todavia sin base de datos, eso es la Semana 5 con Spring Data JPA).
 * ConcurrentHashMap + AtomicLong para que sea seguro ante requests concurrentes.
 */
@Repository
public class ProductoRepository {

    private final Map<Long, Producto> almacen = new ConcurrentHashMap<>();
    private final AtomicLong secuenciaId = new AtomicLong(0);

    public List<Producto> findAll() {
        // TODO (Paso 3): devolver una copia inmutable de los valores del mapa (List.copyOf(almacen.values()))
        throw new UnsupportedOperationException("TODO");
    }

    public Optional<Producto> findById(Long id) {
        // TODO (Paso 3): buscar en el mapa y envolver el resultado en Optional (Optional.ofNullable)
        throw new UnsupportedOperationException("TODO");
    }

    public boolean existsByNombre(String nombre) {
        // TODO (Paso 3): chequeo de duplicado - recorrer los valores del mapa y comparar
        // p.getNombre() ignorando mayusculas/minusculas (equalsIgnoreCase)
        throw new UnsupportedOperationException("TODO");
    }

    public Producto save(Producto producto) {
        // TODO (Paso 3): si producto.getId() es null, asignarle un id nuevo con secuenciaId.incrementAndGet();
        // despues guardarlo en el mapa (almacen.put) y devolverlo
        throw new UnsupportedOperationException("TODO");
    }

    public boolean existsById(Long id) {
        // TODO (Paso 3): chequear si el mapa contiene la key (almacen.containsKey)
        throw new UnsupportedOperationException("TODO");
    }

    public void deleteById(Long id) {
        // TODO (Paso 3): eliminar del mapa (almacen.remove)
        throw new UnsupportedOperationException("TODO");
    }
}
