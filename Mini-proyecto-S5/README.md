# Práctica — Semana 5 (Spring Data JPA + PostgreSQL)

Persistencia real con PostgreSQL (via Docker Compose local), Flyway para versionar
el esquema, y Spring Data JPA con queries derivadas + JPQL — el paralelismo casi
directo de lo que ya conocés de TypeORM.

Después de cada paso corré:

```
mvn compile
```

Para correrlo:

```bash
docker compose up -d
mvn spring-boot:run
```

- API: http://localhost:8085/api/productos
- Swagger UI: http://localhost:8085/swagger-ui.html

## Orden sugerido

### 1. `domain/Categoria.java`, `domain/Sede.java`, `domain/Producto.java` — entidades JPA
- [ ] `@Entity @Table(name = "...")` + `@Id @GeneratedValue(strategy = GenerationType.IDENTITY)`
- [ ] `Categoria` (1) → (N) `Producto` (N) ← (1) `Sede` con
      `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(...)`

### 2. `src/main/resources/db/migration/` — migraciones Flyway
- [ ] `V1` categorías, `V2` sedes, `V3` productos (FKs + `UNIQUE(nombre, sede_id)`),
      `V4` datos semilla — equivalente versionado a `typeorm migration:generate`

### 3. `repository/*Repository.java` — `JpaRepository` + queries derivadas/JPQL
- [ ] Queries derivadas (ej. `existsByNombreAndSedeId`)
- [ ] `@Query("select p from Producto p where ...")` para lo que en TypeORM sería
      `createQueryBuilder()`
- [ ] `@EntityGraph(attributePaths = {"categoria", "sede"})` para evitar N+1

### 4. `exception/` — `CategoriaNoEncontradaException`, `ProductoNoEncontradoException`, `SedeNoEncontradaException`, `GlobalExceptionHandler`, `ApiError`

### 5. `service/ProductoService.java` — `open-in-view: false`
- [ ] Cualquier acceso a relación `LAZY` pasa por el service dentro de la
      transacción, no por Jackson después de que la transacción cerró — evita
      `LazyInitializationException` silencioso

### 6. `controller/ProductoController.java` + `config/OpenApiConfig.java`

### 7. Tests (`ProductoServiceTest`, `ProductoControllerTest`, `ProductoRepositoryIT`)
- [ ] `ProductoRepositoryIT` — integración real con **Testcontainers**
      (`postgres:16-alpine`): migraciones Flyway reales + queries derivadas contra
      un Postgres descartable (requiere Docker)

## Checklist de cierre

- [ ] Mapeo relaciones `@ManyToOne`/`@JoinColumn` igual que `@ManyToOne` de TypeORM
- [ ] Versiono el esquema con Flyway en vez de `migration:generate`
- [ ] Entiendo por qué `open-in-view: false` evita el `LazyInitializationException` silencioso
- [ ] Puedo escribir un test de integración real con Testcontainers
