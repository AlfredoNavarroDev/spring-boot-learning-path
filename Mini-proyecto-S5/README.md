# Semana 5 — Spring Data JPA + PostgreSQL

Persistencia real con PostgreSQL (via Docker Compose local), Flyway para versionar el esquema, y Spring Data JPA con queries derivadas + JPQL — el paralelismo casi directo de lo que ya conocés de TypeORM.

## Cómo correrlo

```bash
# 1. Levantar Postgres local
docker compose up -d

# 2. Correr la app (Flyway migra el esquema y siembra datos de ejemplo al arrancar)
mvn spring-boot:run
```

- API: http://localhost:8085/api/productos
- Swagger UI: http://localhost:8085/swagger-ui.html

## Equivalencias TypeORM → Spring Data JPA

| TypeORM | Spring Data JPA |
|---|---|
| `@Entity()` | `@Entity @Table(name = "...")` |
| `@PrimaryGeneratedColumn()` | `@Id @GeneratedValue(strategy = GenerationType.IDENTITY)` |
| `@ManyToOne(() => Categoria)` | `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "categoria_id")` |
| `Repository<Producto>` (`find`, `findOneBy`) | `JpaRepository<Producto, Long>` + queries derivadas (`existsByNombreAndSedeId`) |
| Query builder / `createQueryBuilder()` | `@Query("select p from Producto p where ...")` (JPQL) |
| Migraciones con `typeorm migration:generate` | Migraciones SQL versionadas en `src/main/resources/db/migration` (Flyway) |
| `relations: ['categoria', 'sede']` en `find()` | `@EntityGraph(attributePaths = {"categoria", "sede"})` (evita N+1) |

## Modelo de datos

`Categoria` (1) → (N) `Producto` (N) ← (1) `Sede`. Migraciones Flyway en orden: `V1` categorias, `V2` sedes, `V3` productos (con FKs y constraint `UNIQUE(nombre, sede_id)`), `V4` datos semilla.

## Por qué `open-in-view: false`

Se desactiva a propósito: fuerza a que cualquier acceso a una relación `LAZY` (`producto.getCategoria().getNombre()`) pase por el service dentro de la transacción, no por la vista/serializador Jackson después de que la transacción ya cerró — evita el clásico `LazyInitializationException` silencioso y hace explícito dónde se paga el costo de cada fetch.

## Tests

```bash
mvn test
```

- `ProductoServiceTest` — unitario con Mockito, sin Spring ni base de datos.
- `ProductoControllerTest` — `@WebMvcTest` + `MockMvc`, service mockeado.
- `ProductoRepositoryIT` — integración real con **Testcontainers** (`postgres:16-alpine`): levanta un Postgres descartable, corre las migraciones Flyway reales contra él y valida las queries derivadas y `@Query`. Requiere Docker corriendo.
