# Práctica — Semana 6 (Resilience4j + Redis: APIs externas resilientes)

Mini-proyecto integrador de la semana 6: un buró de crédito simulado (inestable a
propósito) consumido de forma resiliente, conectado al motor de reglas de fraude
de la Semana 3 para decidir si una transacción se aprueba, se rechaza o va a
revisión manual.
Todos los archivos tienen comentarios `TODO` en vez de la solución.
Si te trabás, mirá `../Mini-proyecto-S6` (mismo tema ya resuelto).

Después de cada paso corré:

```
mvn compile
```

Para correrlo:

```bash
docker compose up -d          # Redis en localhost:6380
mvn spring-boot:run
```

- `GET /api/buro-credito/{dni}` — consulta el score con cache + resiliencia.
- `POST /api/fraude/evaluar` — corre el motor de reglas (Semana 3) + el buró de
  crédito y devuelve una decisión (`APROBADA` / `RECHAZADA` / `REVISION_MANUAL`).

## Orden sugerido

### 1. `modelo/Transaccion.java`, `dto/*.java` — records planos (ya completo)
**Propósito:** el objeto de dominio sobre el que corren las reglas de fraude
(portado de la Semana 3), y los DTOs de entrada/salida de la API. Como son
records sin lógica, no hay nada que implementar — arrancá leyéndolos para
entender el modelo antes de tocar el resto.
**Archivos:** `modelo/Transaccion.java`, `dto/TransaccionRequest.java`,
`dto/ScoreCrediticioResponse.java`, `dto/EvaluacionFraudeResponse.java`
- [ ] Entiendo qué campo de `Transaccion` usa cada regla de fraude
- [ ] Entiendo por qué `ScoreCrediticioResponse` implementa `Serializable`
      (Spring Cache/Redis necesita serializar el valor cacheado)

### 2. `motor/RegistroRegla.java` (ya completo), `motor/MotorValidacion.java`, `reglas/ReglasFraude.java` — motor de reglas reusado de la Semana 3
**Propósito:** practicar de nuevo la composición de `Predicate<Transaccion>`
de la Semana 3 antes de conectarla a algo nuevo (una API externa resiliente).
Es el mismo motor, sin el velocity check (necesita historial fuera de alcance acá).
**Archivos:** `motor/MotorValidacion.java`, `reglas/ReglasFraude.java`,
`motor/RegistroRegla.java`
- [ ] `MotorValidacion.evaluar` — filtra las reglas que disparan y mapea a su nombre
- [ ] `ReglasFraude.montoSobreLimite` — `monto > LIMITE_DIARIO`
- [ ] `ReglasFraude.paisBloqueado` — país en `PAISES_BLOQUEADOS`
- [ ] `ReglasFraude.horarioInusual` — madrugada 00:00–05:00

### 3. `cliente/BuroCreditoClient.java` (interfaz, ya completa), `cliente/BuroCreditoSimuladoClient.java` — DIP + falla simulada
**Propósito:** `BuroCreditoService` va a depender de la interfaz, no de esta
implementación concreta — eso permite mockear el proveedor externo en tests y,
el día que haya un proveedor real, cambiarlo sin tocar el service (Dependency
Inversion Principle). Esta implementación simula un proveedor inestable
(~35% de fallos) para poder ejercitar Circuit Breaker/Retry/Rate Limiter sin
depender de una API de terceros real.
**Archivos:** `cliente/BuroCreditoSimuladoClient.java`, `cliente/BuroCreditoClient.java`
- [ ] `simularLatenciaDeRed` — `Thread.sleep` con latencia aleatoria 50-250ms
- [ ] `consultar` — ~35% de probabilidad de lanzar `BuroCreditoNoDisponibleException`;
      si no falla, genera un score aleatorio 300-850 y deriva el nivel de riesgo

### 4. `service/BuroCreditoService.java` — pipeline de resiliencia (el corazón de la semana)
**Propósito:** el patrón estándar para consumir una API de terceros inestable en
banca/fintech. Las anotaciones (`@Cacheable`, `@CircuitBreaker`, `@Retry`,
`@RateLimiter`) son configuración declarativa de Resilience4j — no hay que
tocarlas ni entender su implementación interna, solo completar el cuerpo del
método que envuelven.
**Archivos:** `service/BuroCreditoService.java`, `cliente/BuroCreditoClient.java`
- [ ] `consultarScore` — delega en `buroCreditoClient.consultar(dni)` (una línea;
      toda la resiliencia vive en las anotaciones, no en el cuerpo)
- [ ] `scoreDeContingencia` (fallback, firma obligatoria `(String, Throwable)`) —
      devuelve `ScoreCrediticioResponse.deContingencia(dni)`: score degradado con
      `nivelRiesgo=ALTO` (mejor un falso rechazo que aprobar a ciegas)

### 5. `service/FraudeService.java` — combina motor de reglas + buró de crédito
**Propósito:** conecta el motor de reglas en memoria de la Semana 3 (sin
dependencias externas) con el buró de crédito resiliente de esta semana, para
llegar a una decisión final de negocio.
**Archivos:** `service/FraudeService.java`
- [ ] `evaluar` — arma el `Transaccion` desde el request, corre
      `motorValidacion.evaluar`, consulta `buroCreditoService.consultarScore` y
      arma el `EvaluacionFraudeResponse` con la decisión
- [ ] `decidir` — `RECHAZADA` si hay reglas disparadas; si no, `REVISION_MANUAL`
      si `nivelRiesgo` es `ALTO` (incluye el caso fallback); si no, `APROBADA`

### 6. `controller/FraudeController.java` + `exception/BuroCreditoNoDisponibleException.java` + `exception/GlobalExceptionHandler.java`
**Propósito:** exponer los dos endpoints REST y centralizar el manejo de
errores, igual que en semanas anteriores — el controller solo orquesta HTTP,
sin lógica de negocio.
**Archivos:** `controller/FraudeController.java`,
`exception/BuroCreditoNoDisponibleException.java`,
`exception/GlobalExceptionHandler.java`
- [ ] `POST /api/fraude/evaluar` — delega en `fraudeService.evaluar`
- [ ] `GET /api/buro-credito/{dni}` — delega en `buroCreditoService.consultarScore`
- [ ] `BuroCreditoNoDisponibleException` — pasa el mensaje a `super(...)`
- [ ] `GlobalExceptionHandler.manejarValidacion` — 400 con detalles de campo
- [ ] `GlobalExceptionHandler.manejarGenerico` — 500 con mensaje genérico

### 7. Tests (`FraudeServiceTest`, `BuroCreditoServiceIT`) — ya completos
**Propósito:** verificar el trabajo de los pasos anteriores. No hace falta
tocarlos, pero correrlos (una vez implementado todo) confirma que el pipeline
funciona de punta a punta.
**Archivos:** `src/test/java/.../service/FraudeServiceTest.java`,
`src/test/java/.../service/BuroCreditoServiceIT.java`
- [ ] `FraudeServiceTest` — unitario con Mockito: valida la lógica de decisión
      sin red ni Spring
- [ ] `BuroCreditoServiceIT` — integración real con **Testcontainers**
      (`redis:7-alpine`): confirma que el cache evita una segunda llamada para
      el mismo DNI, y que el Circuit Breaker cae al fallback cuando el cliente
      simulado siempre falla (requiere Docker)

## Checklist de cierre

- [ ] Entiendo el orden del pipeline: cache → circuit breaker → retry → rate
      limiter → fallback
- [ ] Sé por qué el cliente externo se inyecta como interfaz (DIP), no como
      clase concreta
- [ ] Puedo explicar cuándo se abre un circuit breaker y qué pasa mientras
      está abierto
- [ ] Probé el pipeline completo con Testcontainers, no solo con mocks
