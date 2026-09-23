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
        simularLatenciaDeRed();

        if (ThreadLocalRandom.current().nextDouble() < PROBABILIDAD_DE_FALLO) {
            throw new BuroCreditoNoDisponibleException("Timeout consultando el buro de credito para " + dni);
        }

        int score = 300 + ThreadLocalRandom.current().nextInt(551); // rango tipico 300-850
        String nivelRiesgo = score < 500 ? "ALTO" : score < 700 ? "MEDIO" : "BAJO";
        return new ScoreCrediticioResponse(dni, score, nivelRiesgo, "EXTERNA");
    }

    private void simularLatenciaDeRed() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(50, 250));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
