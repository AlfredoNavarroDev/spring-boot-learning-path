package com.alfredodev.miniproyectos6.dto;

import java.util.List;

public record EvaluacionFraudeResponse(String decision, List<String> reglasDisparadas,
        ScoreCrediticioResponse scoreCrediticio) {
}
