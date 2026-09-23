# Semana 9 — Proyecto final: sistema de inventario completo

Consolida todas las semanas anteriores en un solo sistema: JPA + PostgreSQL (S5), Resilience4j/Redis quedó fuera de este mini-proyecto por alcance, JWT (S7), RBAC con los 5 roles reales (S8), Swagger, testing en las 3 capas, y `docker-compose up` de punta a punta.

## Cómo correrlo

```bash
docker compose up -d --build
```

Levanta PostgreSQL y la API juntos. La API corre las migraciones Flyway (esquema + datos de ejemplo) al arrancar.

- API: http://localhost:8089/api
- Swagger UI: http://localhost:8089/swagger-ui.html
- Health check: http://localhost:8089/actuator/health

```bash
# Registro + login
curl -X POST http://localhost:8089/api/auth/registro -H "Content-Type: application/json" \
  -d '{"email":"owner@correo.com","password":"clave12345","rol":"PROPIETARIO"}'
curl -X POST http://localhost:8089/api/auth/login -H "Content-Type: application/json" \
  -d '{"email":"owner@correo.com","password":"clave12345"}'

# CRUD de inventario con el token recibido
curl http://localhost:8089/api/productos -H "Authorization: Bearer <token>"
```

## Qué junta esta semana

| Pieza | De qué semana viene |
|---|---|
| `Producto`/`Categoria`/`Sede` con Spring Data JPA + Flyway | Semana 5 |
| `JwtService` + `JwtAuthenticationFilter` (rol como claim) | Semana 7/8 |
| `@PreAuthorize` con los 5 roles reales | Semana 8 |
| Swagger (`springdoc-openapi`) con esquema Bearer | Semana 4 |
| Tests unitarios (Mockito), `@WebMvcTest`, integración real con Testcontainers | Semana 5/9 |
| `Dockerfile` multi-stage + `docker-compose.yml` (app + Postgres) | Semana 9 |
| CI en GitHub Actions (`.github/workflows/ci.yml`) | Semana 9 |

## Nota importante — Spring Boot 4 modularizó Flyway

En Spring Boot 4, `FlywayAutoConfiguration` se movió del `spring-boot-autoconfigure` monolítico a su propio módulo. Tener `flyway-core` solo en el classpath **no alcanza**: hay que declarar `spring-boot-starter-flyway` explícitamente, o Flyway nunca corre y Hibernate falla con `Schema validation: missing table [...]` al validar contra un esquema vacío. Aplica igual a las Semanas 5, 7 y 8 de este repo.

## Dockerfile multi-stage

- **Etapa `builder`** (`eclipse-temurin:21-jdk-alpine`): compila con Maven; se descarta entera al final.
- **Etapa runtime** (`eclipse-temurin:21-jre-alpine`): solo el JRE + el jar ya compilado, corre como usuario `spring` no-root, con `HEALTHCHECK` contra `/actuator/health`.

## Tests

```bash
mvn test
```

- `AuthServiceTest` / `ProductoServiceTest` — unitarios con Mockito.
- `InventarioFlujoCompletoIT` — **e2e real contra PostgreSQL** (Testcontainers, no H2): dos usuarios con roles distintos (`ABASTECEDOR`, `VENDEDOR`) se registran, loguean, y el flujo alta → lectura → intento de borrado se comporta según el rol de cada uno. Cubre en un solo test JWT + RBAC + JPA + Postgres funcionando juntos.

## CI

`.github/workflows/ci.yml` corre `mvn test` (incluye la integración con Testcontainers — los runners de GitHub Actions ya traen Docker) y valida que la imagen Docker compile, en cada push/PR que toque esta carpeta.
