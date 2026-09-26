# Práctica — Semana 5 (Spring Data JPA + PostgreSQL)

Copia de práctica de [`../Mini-proyecto-S5`](../Mini-proyecto-S5) con el código real
reemplazado por `TODO`s. Mismo dominio (Categoria/Sede/Producto sobre PostgreSQL vía
Flyway), pensado para completar vos mismo la persistencia real y el manejo de
relaciones `LAZY` — el paralelismo casi directo de lo que ya conocés de TypeORM.
Si te trabás, la versión resuelta está en `../Mini-proyecto-S5`.

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

### 1. `domain/Categoria.java`, `domain/Sede.java`, `domain/Producto.java` — entidades JPA (ya completo)
**Propósito:** ver cómo se mapean relaciones `@ManyToOne` con `FetchType.LAZY` en JPA/Hibernate,
el equivalente directo del `@ManyToOne` de TypeORM. Las migraciones Flyway (`db/migration/V1-V4`)
ya están completas también — son SQL versionado, no código a completar; estudialas para ver
cómo se arman las FKs y el `UNIQUE(nombre, sede_id)` que el service va a necesitar respetar.

**Archivos:**
- [ ] `domain/Categoria.java`, `domain/Sede.java`, `domain/Producto.java`
- [ ] `src/main/resources/db/migration/V1__create_categorias.sql` .. `V4__seed_data.sql`

### 2. `repository/CategoriaRepository.java`, `repository/SedeRepository.java`, `repository/ProductoRepository.java` — Spring Data JPA (ya completo)
**Propósito:** son interfaces — Spring Data las implementa en runtime, no hay cuerpos que
completar. Estudiá cómo `existsByNombreAndSedeId` arma una query derivada del nombre del
método, cómo `@EntityGraph(attributePaths = {"categoria", "sede"})` evita el N+1 al traer
relaciones LAZY en un solo `SELECT`, y cómo `@Query` te da JPQL crudo (el equivalente al
`createQueryBuilder()` de TypeORM).

**Archivos:**
- [ ] `repository/CategoriaRepository.java`, `repository/SedeRepository.java`, `repository/ProductoRepository.java`

### 3. `exception/` — manejo de errores
**Propósito:** las excepciones de dominio (`CategoriaNoEncontradaException`,
`SedeNoEncontradaException`, `ProductoNoEncontradoException`) necesitan un mensaje útil en el
constructor. El `GlobalExceptionHandler` (`@RestControllerAdvice`) centraliza el mapeo de esas
excepciones + `MethodArgumentNotValidException` a respuestas HTTP consistentes (`ApiError`, ya
completo por ser un DTO plano).

**Archivos:**
- [ ] `exception/CategoriaNoEncontradaException.java`, `exception/SedeNoEncontradaException.java`, `exception/ProductoNoEncontradoException.java`
- [ ] `exception/GlobalExceptionHandler.java`

### 4. `service/ProductoService.java` — reglas de negocio + `open-in-view: false`
**Propósito:** el archivo más importante de la semana. Implementá crear/listar/buscar/
actualizar/eliminar producto usando los repositories (el constructor de inyección ya está
armado, no lo toques). En `application.yml` está `open-in-view: false`: cualquier acceso a
`producto.getCategoria()`/`producto.getSede()` (relaciones LAZY) tiene que resolverse **acá
dentro**, mientras la transacción `@Transactional` sigue abierta — si lo dejás para después
(por ejemplo en el controller o en Jackson al serializar), explota con
`LazyInitializationException`.

**Archivos:**
- [ ] `service/ProductoService.java`

### 5. `controller/ProductoController.java` — endpoints REST
**Propósito:** cablear los endpoints (`GET`/`POST`/`PUT`/`DELETE` sobre `/api/productos` +
`GET /api/productos/stock-bajo`) contra el service. Anotaciones y firmas ya están, solo falta
el cuerpo de cada método. `config/OpenApiConfig.java` ya está completo (solo builder de
metadata Swagger).

**Archivos:**
- [ ] `controller/ProductoController.java`

## Checklist de cierre

- [ ] Puedo mapear relaciones `@ManyToOne`/`@JoinColumn` igual que el `@ManyToOne` de TypeORM
- [ ] Entiendo la diferencia entre versionar el esquema con Flyway (`V1__...sql`) y generar
      migraciones automáticas tipo `typeorm migration:generate`
- [ ] Entiendo por qué `open-in-view: false` obliga a resolver relaciones `LAZY` dentro del
      service y evita el `LazyInitializationException` silencioso en la capa de vista
- [ ] Puedo leer/ejecutar un test de integración real con Testcontainers (`ProductoRepositoryIT`,
      requiere Docker corriendo)
