# Semana 6 — Resilience4j + Redis: APIs externas resilientes

Consumir un "buró de crédito" simulado (inestable a propósito) de forma resiliente, y conectar el motor de reglas de fraude de la Semana 3 a un endpoint real de evaluación.

## Cómo correrlo

```bash
docker compose up -d          # Redis en localhost:6380
mvn spring-boot:run
```

- `GET /api/buro-credito/{dni}` — consulta el score con cache + resiliencia.
- `POST /api/fraude/evaluar` — corre el motor de reglas (Semana 3) + el buró de crédito y devuelve una decisión (`APROBADA` / `RECHAZADA` / `REVISION_MANUAL`).

## Patrón de resiliencia (el estándar para consumir APIs de terceros en fintech)

```
Cliente → @Cacheable (Redis) → @CircuitBreaker → @Retry → @RateLimiter → BuroCreditoClient (externo)
```

| Aspecto | Qué resuelve | Config (`application.yml`) |
|---|---|---|
| **Cache (Redis)** | No pegarle al proveedor por el mismo DNI dentro de la ventana de TTL (60s). | `spring.cache.redis.time-to-live` |
| **Retry** | Reintenta con backoff exponencial ante fallos transitorios (timeouts). | `resilience4j.retry.instances.buroCredito` |
| **Circuit Breaker** | Si ≥50% de las últimas 10 llamadas fallan, abre el circuito 5s y deja de insistir — evita saturar un proveedor caído. | `resilience4j.circuitbreaker.instances.buroCredito` |
| **Rate Limiter** | Tope de 20 llamadas/segundo hacia el proveedor externo. | `resilience4j.ratelimiter.instances.buroCredito` |
| **Fallback** | Si todo lo anterior falla, devuelve un score degradado con `nivelRiesgo=ALTO` (mejor un falso rechazo que aprobar a ciegas). | `BuroCreditoService.scoreDeContingencia` |

`BuroCreditoSimuladoClient` falla ~35% de las veces a propósito, para poder observar el Circuit Breaker abrirse en la práctica sin depender de una API de terceros real.

## Por qué una interfaz para el cliente externo (DIP)

`BuroCreditoService` depende de `BuroCreditoClient` (interfaz), no de `BuroCreditoSimuladoClient` directo — permite mockear el proveedor externo en tests (`BuroCreditoServiceIT`) y, el día que haya un proveedor real, cambiarlo sin tocar el service ni los endpoints.

## Tests

```bash
mvn test
```

- `FraudeServiceTest` — unitario con Mockito: valida la lógica de decisión (reglas + score) sin red ni Spring.
- `BuroCreditoServiceIT` — integración real con **Testcontainers** (`redis:7-alpine`): confirma que el cache evita una segunda llamada al cliente para el mismo DNI, y que el Circuit Breaker cae al fallback cuando el cliente simulado siempre falla. Requiere Docker corriendo.
