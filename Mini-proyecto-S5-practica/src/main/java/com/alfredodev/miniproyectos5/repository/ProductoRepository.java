package com.alfredodev.miniproyectos5.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alfredodev.miniproyectos5.domain.Producto;

// (ya completo — Spring Data implementa la interfaz)
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Query derivada: Spring Data la arma sola a partir del nombre del metodo.
    boolean existsByNombreAndSedeId(String nombre, Long sedeId);

    // @EntityGraph trae categoria/sede en el mismo SELECT (evita N+1 al mapear a
    // ProductoResponse, que accede a ambas relaciones).
    @EntityGraph(attributePaths = { "categoria", "sede" })
    Page<Producto> findAll(Pageable pageable);

    @EntityGraph(attributePaths = { "categoria", "sede" })
    List<Producto> findByCategoriaId(Long categoriaId);

    // Query JPQL personalizada: productos con stock por debajo del umbral en una sede,
    // el caso de uso real de "alerta de reposicion" del inventario.
    @Query("select p from Producto p where p.sede.id = :sedeId and p.stock < :umbral")
    List<Producto> buscarConStockBajo(@Param("sedeId") Long sedeId, @Param("umbral") int umbral);
}
