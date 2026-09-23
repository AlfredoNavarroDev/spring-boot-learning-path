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
        return List.copyOf(almacen.values());
    }

    public Optional<Producto> findById(Long id) {
        return Optional.ofNullable(almacen.get(id));
    }

    public boolean existsByNombre(String nombre) {
        return almacen.values().stream().anyMatch(p -> p.getNombre().equalsIgnoreCase(nombre));
    }

    public Producto save(Producto producto) {
        if (producto.getId() == null) {
            producto.setId(secuenciaId.incrementAndGet());
        }
        almacen.put(producto.getId(), producto);
        return producto;
    }

    public boolean existsById(Long id) {
        return almacen.containsKey(id);
    }

    public void deleteById(Long id) {
        almacen.remove(id);
    }
}
