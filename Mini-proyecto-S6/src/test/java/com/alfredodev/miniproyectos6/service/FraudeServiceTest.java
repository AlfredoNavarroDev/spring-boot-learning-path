package com.alfredodev.miniproyectos6.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alfredodev.miniproyectos6.dto.ScoreCrediticioResponse;
import com.alfredodev.miniproyectos6.dto.TransaccionRequest;

@ExtendWith(MockitoExtension.class)
class FraudeServiceTest {

    // Hora fija en horario "normal" (mediodia) para que HORARIO_INUSUAL nunca se dispare sola.
    private static final LocalDateTime HORA_DIURNA = LocalDateTime.of(2026, Month.MARCH, 10, 12, 0);

    @Mock
    private BuroCreditoService buroCreditoService;

    @Test
    void rechazaCuandoUnaReglaDeFraudeSeDispara() {
        FraudeService fraudeService = new FraudeService(buroCreditoService);
        when(buroCreditoService.consultarScore(anyString()))
                .thenReturn(new ScoreCrediticioResponse("12345678", 750, "BAJO", "EXTERNA"));

        var request = new TransaccionRequest("12345678", 15_000.0, "PEN", HORA_DIURNA, "cliente-1", "WEB");

        var resultado = fraudeService.evaluar(request);

        assertThat(resultado.decision()).isEqualTo("RECHAZADA");
        assertThat(resultado.reglasDisparadas()).contains("MONTO_SOBRE_LIMITE");
    }

    @Test
    void enviaARevisionManualCuandoElBuroDevuelveRiesgoAlto() {
        FraudeService fraudeService = new FraudeService(buroCreditoService);
        when(buroCreditoService.consultarScore(anyString()))
                .thenReturn(new ScoreCrediticioResponse("12345678", 320, "ALTO", "EXTERNA"));

        var request = new TransaccionRequest("12345678", 100.0, "PEN", HORA_DIURNA, "cliente-1", "WEB");

        var resultado = fraudeService.evaluar(request);

        assertThat(resultado.decision()).isEqualTo("REVISION_MANUAL");
        assertThat(resultado.reglasDisparadas()).isEmpty();
    }

    @Test
    void apruebaCuandoNoHayReglasDisparadasYElRiesgoEsBajo() {
        FraudeService fraudeService = new FraudeService(buroCreditoService);
        when(buroCreditoService.consultarScore(anyString()))
                .thenReturn(new ScoreCrediticioResponse("12345678", 780, "BAJO", "EXTERNA"));

        var request = new TransaccionRequest("12345678", 100.0, "PEN", HORA_DIURNA, "cliente-1", "WEB");

        var resultado = fraudeService.evaluar(request);

        assertThat(resultado.decision()).isEqualTo("APROBADA");
    }
}
