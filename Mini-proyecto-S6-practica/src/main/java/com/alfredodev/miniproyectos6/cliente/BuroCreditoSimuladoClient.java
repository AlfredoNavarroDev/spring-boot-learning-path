package com.alfredodev.miniproyectos6.cliente;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import com.alfredodev.miniproyectos6.dto.ScoreCrediticioResponse;
import com.alfredodev.miniproyectos6.exception.BuroCreditoNoDisponibleException;

/**
 * Simula un "buro de credito" externo inestable: falla ~35% de las veces y tiene
 * latencia variable, para poder demostrar Circuit Breaker/Retry/Rate Limiter sin
 * depender de una API de terceros real en el mini-proyecto.
 */
@Component
public class BuroCreditoSimuladoClient implements BuroCreditoClient {

    private static final double PROBABILIDAD_DE_FALLO = 0.35;

    @Override
    public ScoreCrediticioResponse consultar(String dni) {
        // TODO (Paso 3): simular la llamada real:
        // 1. Llamá a simularLatenciaDeRed() para simular latencia de red.
        // 2. Con probabilidad PROBABILIDAD_DE_FALLO (ThreadLocalRandom.current().nextDouble() < PROBABILIDAD_DE_FALLO)
        //    lanzá new BuroCreditoNoDisponibleException("Timeout consultando el buro de credito para " + dni).
        // 3. Si no falla, generá un score aleatorio 300-850
        //    (300 + ThreadLocalRandom.current().nextInt(551)), derivá nivelRiesgo
        //    ("ALTO" si < 500, "MEDIO" si < 700, si no "BAJO") y devolvé
        //    new ScoreCrediticioResponse(dni, score, nivelRiesgo, "EXTERNA").
        throw new UnsupportedOperationException("TODO Paso 3: BuroCreditoSimuladoClient.consultar");
    }

    private void simularLatenciaDeRed() {
        // TODO (Paso 3): Thread.sleep con una latencia aleatoria entre 50 y 250 ms
        // (ThreadLocalRandom.current().nextInt(50, 250)), capturando InterruptedException
        // y volviendo a marcar el hilo interrumpido con Thread.currentThread().interrupt().
        throw new UnsupportedOperationException("TODO Paso 3: BuroCreditoSimuladoClient.simularLatenciaDeRed");
    }
}
