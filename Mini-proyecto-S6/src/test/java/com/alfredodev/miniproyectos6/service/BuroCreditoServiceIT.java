package com.alfredodev.miniproyectos6.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.alfredodev.miniproyectos6.cliente.BuroCreditoClient;
import com.alfredodev.miniproyectos6.dto.ScoreCrediticioResponse;
import com.alfredodev.miniproyectos6.exception.BuroCreditoNoDisponibleException;

/**
 * Integracion real: Redis en un contenedor descartable (Testcontainers) para probar
 * que @Cacheable efectivamente evita una segunda llamada al cliente, y que el
 * Circuit Breaker cae al metodo de fallback cuando el cliente simulado siempre falla.
 */
@SpringBootTest
@Testcontainers
class BuroCreditoServiceIT {

    @Container
    static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:7-alpine"))
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
    }

    @Autowired
    private BuroCreditoService buroCreditoService;

    @MockitoBean
    private BuroCreditoClient buroCreditoClient;

    @Test
    void cacheaElResultadoYNoVuelveALlamarAlClienteParaElMismoDni() {
        when(buroCreditoClient.consultar("11111111"))
                .thenReturn(new ScoreCrediticioResponse("11111111", 700, "MEDIO", "EXTERNA"));

        buroCreditoService.consultarScore("11111111");
        buroCreditoService.consultarScore("11111111");

        verify(buroCreditoClient, times(1)).consultar("11111111");
    }

    @Test
    void caeAlFallbackCuandoElClienteSiempreFalla() {
        when(buroCreditoClient.consultar(anyString()))
                .thenThrow(new BuroCreditoNoDisponibleException("simulado: buro caido"));

        ScoreCrediticioResponse resultado = buroCreditoService.consultarScore("22222222");

        assertThat(resultado.fuente()).isEqualTo("FALLBACK");
        assertThat(resultado.nivelRiesgo()).isEqualTo("ALTO");
    }
}
