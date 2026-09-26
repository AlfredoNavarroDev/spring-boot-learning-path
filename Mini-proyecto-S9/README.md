# Práctica — Semana 9 (Proyecto final: sistema de inventario completo)

Consolida todas las semanas anteriores en un solo sistema: JPA + PostgreSQL (S5),
JWT (S7), RBAC con los 5 roles reales (S8), Swagger, testing en las 3 capas, y
`docker-compose up` de punta a punta. Resilience4j/Redis quedó fuera de este
mini-proyecto por alcance.

Después de cada paso corré:

```
mvn compile
```

Para correrlo:

```bash
docker compose up -d --build
```

Levanta PostgreSQL y la API juntos. La API corre las migraciones Flyway (esquema +
datos de ejemplo) al arrancar.

- API: http://localhost:8089/api
- Swagger UI: http://localhost:8089/swagger-ui.html
- Health check: http://localhost:8089/actuator/health

## Orden sugerido

### 1. `domain/` (`Producto`, `Categoria`, `Sede`, `Usuario`, `Rol`) + Flyway — de la Semana 5
- [ ] Migraciones + entidades JPA reusadas y ajustadas al dominio final

### 2. `security/` (`JwtService`, `JwtAuthenticationFilter`, `SecurityConfig`) — de la Semana 7/8
- [ ] Rol como claim del JWT, sin round-trip a la base por request

### 3. `controller/ProductoController.java` — `@PreAuthorize` con los 5 roles reales — de la Semana 8

### 4. `config/OpenApiConfig.java` — Swagger con esquema Bearer — de la Semana 4

### 5. `Dockerfile` multi-stage + `docker-compose.yml`
- [ ] Etapa `builder` (`eclipse-temurin:21-jdk-alpine`) compila con Maven y se
      descarta entera al final
- [ ] Etapa runtime (`eclipse-temurin:21-jre-alpine`) — solo el JRE + el jar ya
      compilado, corre como usuario `spring` no-root, con `HEALTHCHECK` contra
      `/actuator/health`

### 6. `.github/workflows/ci.yml` — CI
- [ ] Corre `mvn test` (incluye la integración con Testcontainers — los runners de
      GitHub Actions ya traen Docker) y valida que la imagen Docker compile, en
      cada push/PR que toque esta carpeta

### 7. Tests (`AuthServiceTest`, `ProductoServiceTest`, `InventarioFlujoCompletoIT`)
- [ ] `InventarioFlujoCompletoIT` — **e2e real contra PostgreSQL** (Testcontainers,
      no H2): dos usuarios con roles distintos (`ABASTECEDOR`, `VENDEDOR`) se
      registran, loguean, y el flujo alta → lectura → intento de borrado se
      comporta según el rol de cada uno. Cubre en un solo test JWT + RBAC + JPA +
      Postgres funcionando juntos

## Nota importante — Spring Boot 4 modularizó Flyway

En Spring Boot 4, `FlywayAutoConfiguration` se movió del `spring-boot-autoconfigure`
monolítico a su propio módulo. Tener `flyway-core` solo en el classpath **no
alcanza**: hay que declarar `spring-boot-starter-flyway` explícitamente, o Flyway
nunca corre y Hibernate falla con `Schema validation: missing table [...]`. Aplica
igual a las Semanas 5, 7 y 8 de este repo.

## Checklist de cierre

- [ ] Puedo armar de memoria el pipeline completo: JPA → JWT → RBAC → Swagger →
      tests → Docker → CI
- [ ] Entiendo por qué el test de integración final usa Postgres real
      (Testcontainers) y no H2
- [ ] Sé por qué `spring-boot-starter-flyway` es obligatorio en Spring Boot 4
- [ ] `docker compose up --build` levanta todo (app + Postgres) sin pasos manuales
