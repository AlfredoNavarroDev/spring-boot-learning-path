package com.alfredodev.miniproyectos5.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.alfredodev.miniproyectos5.domain.Categoria;
import com.alfredodev.miniproyectos5.domain.Sede;

/**
 * Integracion real contra PostgreSQL con Testcontainers: valida que las migraciones
 * Flyway y las queries derivadas/@Query funcionan contra el motor real, no contra H2
 * (que puede esconder incompatibilidades de SQL especifico de Postgres).
 */
@SpringBootTest
@Testcontainers
class ProductoRepositoryIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private SedeRepository sedeRepository;

    @Test
    void aplicaMigracionesYSeedData() {
        assertThat(categoriaRepository.count()).isEqualTo(3);
        assertThat(sedeRepository.count()).isEqualTo(2);
        assertThat(productoRepository.count()).isEqualTo(3);
    }

    @Test
    void encuentraProductosConStockBajo() {
        Sede sedeCentral = sedeRepository.findAll().stream().filter(s -> s.getNombre().equals("Sede Central"))
                .findFirst().orElseThrow();

        var resultado = productoRepository.buscarConStockBajo(sedeCentral.getId(), 10);

        assertThat(resultado).extracting(p -> p.getNombre()).contains("Silla ergonomica");
    }

    @Test
    void findByCategoriaIdTraeLaCategoriaSinLazyInitException() {
        Categoria perifericos = categoriaRepository.findAll().stream()
                .filter(c -> c.getNombre().equals("Perifericos")).findFirst().orElseThrow();

        var resultado = productoRepository.findByCategoriaId(perifericos.getId());

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getCategoria().getNombre()).isEqualTo("Perifericos");
    }
}
