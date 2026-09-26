package com.alfredodev.miniproyectos9.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.alfredodev.miniproyectos9.domain.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @EntityGraph(attributePaths = { "categoria", "sede" })
    Page<Producto> findAll(Pageable pageable);

    boolean existsByNombreAndSedeId(String nombre, Long sedeId);
}
