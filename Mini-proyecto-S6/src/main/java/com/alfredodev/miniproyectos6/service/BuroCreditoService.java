package com.alfredodev.miniproyectos6.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alfredodev.miniproyectos6.cliente.BuroCreditoClient;
import com.alfredodev.miniproyectos6.dto.ScoreCrediticioResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

/**
 * Envuelve la llamada al buro de credito externo con el patron estandar para
 * consumir APIs de terceros en banca/fintech: cache (evita pegarle al proveedor por
 * el mismo DNI en la ventana de TTL), retry con backoff, circuit breaker y rate
 * limiter (Resilience4j). El orden de los aspectos AOP (cache vs resilience) no es
 * critico aca porque el fallback y el valor cacheado son ambos una respuesta valida
 * para el caller; en un sistema real conviene decorar el orden explicitamente.
 */
@Service
public class BuroCreditoService {

    private final BuroCreditoClient buroCreditoClient;

    public BuroCreditoService(BuroCreditoClient buroCreditoClient) {
        this.buroCreditoClient = buroCreditoClient;
    }

    @Cacheable(cacheNames = "score-crediticio", key = "#dni")
    @CircuitBreaker(name = "buroCredito", fallbackMethod = "scoreDeContingencia")
    @Retry(name = "buroCredito")
    @RateLimiter(name = "buroCredito")
    public ScoreCrediticioResponse consultarScore(String dni) {
        return buroCreditoClient.consultar(dni);
    }

    // Firma obligatoria de Resilience4j: mismos parametros + Throwable al final.
    private ScoreCrediticioResponse scoreDeContingencia(String dni, Throwable causa) {
        return ScoreCrediticioResponse.deContingencia(dni);
    }
}
