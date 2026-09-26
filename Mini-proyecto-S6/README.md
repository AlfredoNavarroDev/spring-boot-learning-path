# Práctica — Semana 6 (Resilience4j + Redis: APIs externas resilientes)

Consumir un "buró de crédito" simulado (inestable a propósito) de forma resiliente,
y conectar el motor de reglas de fraude de la Semana 3 a un endpoint real de evaluación.

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

### 1. `cliente/BuroCreditoClient.java` (interfaz) + `BuroCreditoSimuladoClient.java` — DIP
- [ ] `BuroCreditoService` depende de la interfaz, no del cliente simulado directo —
      permite mockear el proveedor externo en tests y, el día que haya un proveedor
      real, cambiarlo sin tocar el service
- [ ] El cliente simulado falla ~35% de las veces a propósito, para poder observar
      el Circuit Breaker abrirse en la práctica

### 2. `motor/`, `reglas/`, `modelo/Transaccion.java` — motor de reglas reusado de la Semana 3

### 3. `service/BuroCreditoService.java` — pipeline de resiliencia
- [ ] `@Cacheable` (Redis, TTL 60s) → `@CircuitBreaker` (abre 5s si ≥50% de las
      últimas 10 llamadas fallan) → `@Retry` (backoff exponencial) →
      `@RateLimiter` (20 llamadas/segundo)
- [ ] Fallback (`scoreDeContingencia`) — score degradado con `nivelRiesgo=ALTO` si
      todo lo anterior falla (mejor un falso rechazo que aprobar a ciegas)

### 4. `service/FraudeService.java` + `controller/FraudeController.java`
- [ ] `GET /api/buro-credito/{dni}` — consulta con cache + resiliencia
- [ ] `POST /api/fraude/evaluar` — motor de reglas + buró de crédito →
      `APROBADA`/`RECHAZADA`/`REVISION_MANUAL`

### 5. `exception/BuroCreditoNoDisponibleException.java` + `GlobalExceptionHandler.java`

### 6. Tests (`FraudeServiceTest`, `BuroCreditoServiceIT`)
- [ ] `FraudeServiceTest` — unitario con Mockito: valida la lógica de decisión sin
      red ni Spring
- [ ] `BuroCreditoServiceIT` — integración real con **Testcontainers**
      (`redis:7-alpine`): confirma que el cache evita una segunda llamada para el
      mismo DNI, y que el Circuit Breaker cae al fallback cuando el cliente
      simulado siempre falla (requiere Docker)

## Checklist de cierre

- [ ] Entiendo el orden del pipeline: cache → circuit breaker → retry → rate
      limiter → fallback
- [ ] Sé por qué el cliente externo se inyecta como interfaz (DIP), no como clase concreta
- [ ] Puedo explicar cuándo se abre un circuit breaker y qué pasa mientras está abierto
- [ ] Probé el pipeline completo con Testcontainers, no solo con mocks
