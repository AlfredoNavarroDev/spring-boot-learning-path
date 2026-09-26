# Práctica — Semana 9 (Proyecto final: sistema de inventario completo)

Copia de práctica de [`../Mini-proyecto-S9`](../Mini-proyecto-S9) con el código real
reemplazado por `TODO`s. Esta semana no introduce conceptos nuevos: consolida todo el
recorrido anterior en un solo proyecto — JPA/PostgreSQL (S5), JWT (S7), RBAC de 5 roles
(S8) — y le suma Swagger, Docker y CI, el mismo tipo de "todo junto en un repo" que ya
armaste en un backend Nest con TypeORM. Si te trabás, la versión resuelta está en
`../Mini-proyecto-S9`.

Después de cada paso corré:

```
mvn compile
```

Para correrlo de punta a punta:

```bash
docker compose up -d --build
```

- API: http://localhost:8089/api/productos
- Swagger UI: http://localhost:8089/swagger-ui.html

> **Nota sobre `pom.xml`:** en Spring Boot 4, `flyway-core` solo **no alcanza** — la
> autoconfiguración de Flyway (`FlywayAutoConfiguration`) vive en
> `spring-boot-starter-flyway`, que es obligatorio como dependencia (además de
> `flyway-database-postgresql` para el dialecto). Sin el starter, Flyway no corre las
> migraciones al arrancar y las tablas simplemente no existen.

## Orden sugerido

Dominio, DTOs y repositorios ya están completos (son S5/S8 ya practicados); el foco de
esta semana es la integración: seguridad, negocio y endpoints.

### 1. `domain/*.java`, `dto/*.java`, `repository/*.java`, `db/migration/*.sql` (ya completo)
**Propósito:** entidades JPA (`Categoria`, `Producto`, `Sede`, `Usuario`) con sus
relaciones y el enum `Rol`, DTOs planos, interfaces `JpaRepository` y las migraciones
Flyway versionadas — todo ya practicado en S5 (JPA) y S8 (RBAC/`Rol`). Repasalas para
tener fresco el modelo antes de tocar seguridad/negocio, pero no hay nada que completar
acá.

**Archivos:**
- [ ] `domain/Categoria.java`, `domain/Producto.java`, `domain/Rol.java`, `domain/Sede.java`, `domain/Usuario.java`
- [ ] `dto/*.java`, `exception/ApiError.java`
- [ ] `repository/*.java`
- [ ] `src/main/resources/db/migration/V1__create_usuarios.sql` .. `V5__seed_data.sql`

### 2. `security/*.java`, `config/SecurityBeansConfig.java`, `exception/*` — seguridad y errores
**Propósito:** el mismo criterio que ya usaste en S7/S8: `JwtService` firma y valida el
token con el **rol como claim** (`claim("rol", rol)`) para que `JwtAuthenticationFilter`
arme las `GrantedAuthority` sin volver a consultar `UsuarioRepository` en cada request.
`SecurityConfig` cablea la cadena de filtros (rutas públicas, `STATELESS`, filtro JWT
antes que el de user/password). `SecurityBeansConfig` expone los beans (`PasswordEncoder`,
`DaoAuthenticationProvider`, `AuthenticationManager`) que el login necesita. Las
excepciones de dominio necesitan un mensaje útil en el constructor, y
`GlobalExceptionHandler` centraliza el mapeo a HTTP — incluido `AccessDeniedException` →
`403`, el que dispara `@PreAuthorize` cuando el rol no alcanza.

**Archivos:**
- [ ] `security/JwtService.java`
- [ ] `security/JwtAuthenticationFilter.java` (dejá `filterChain.doFilter(request, response);` al final, pase lo que pase)
- [ ] `security/SecurityConfig.java`
- [ ] `security/UsuarioDetailsService.java`
- [ ] `config/SecurityBeansConfig.java`
- [ ] `exception/CategoriaNoEncontradaException.java`, `exception/SedeNoEncontradaException.java`, `exception/ProductoNoEncontradoException.java`, `exception/EmailDuplicadoException.java`
- [ ] `exception/GlobalExceptionHandler.java`

### 3. `service/AuthService.java`, `service/ProductoService.java` — el corazón de la integración
**Propósito:** acá se junta todo. `AuthService.registrar` valida email duplicado, hashea
la password (nunca texto plano) y persiste con el rol elegido; `login` delega en
`AuthenticationManager` (que dispara `UsuarioDetailsService` + `PasswordEncoder` por
debajo) y emite el JWT con `jwtService.generarAccessToken(...)`. `ProductoService` resuelve
categoría/sede antes de crear un producto y no sabe nada de roles — esa decisión vive en
el `@PreAuthorize` del controller, no acá. Los constructores de inyección ya están
armados, no los toques.

**Archivos:**
- [ ] `service/AuthService.java`
- [ ] `service/ProductoService.java`

### 4. `controller/AuthController.java`, `controller/ProductoController.java` — endpoints REST
**Propósito:** cablear cada endpoint contra su service. Las anotaciones `@PreAuthorize`
con los 5 roles **ya están puestas y no se tocan** (igual que en S8) — la matriz de
permisos ya fue diseñada, tu trabajo es solo completar qué hace cada método. Sumale
`config/OpenApiConfig.java`, ya completo por ser un builder simple de metadata Swagger.

**Archivos:**
- [ ] `controller/AuthController.java`
- [ ] `controller/ProductoController.java`
- [ ] `config/OpenApiConfig.java` (ya completo)

## Checklist de cierre

- [ ] Puedo trazar el pipeline completo de una request: JPA/PostgreSQL → JWT → RBAC
      (`@PreAuthorize`) → Swagger documentándolo → tests → todo corriendo en Docker → CI
      verificándolo en cada push
- [ ] Entiendo por qué el test final (`InventarioFlujoCompletoIT`) usa **Postgres real
      vía Testcontainers** y no H2: valida el flujo end-to-end (registro → login → JWT →
      RBAC → CRUD) contra el mismo motor de base de datos que corre en producción, no
      contra una aproximación en memoria que puede esconder diferencias de SQL/tipos
- [ ] Sé por qué `spring-boot-starter-flyway` es obligatorio en Spring Boot 4 (no alcanza
      con `flyway-core` solo)
- [ ] Corrí `docker compose up -d --build` de punta a punta y probé el flujo completo
      contra `http://localhost:8089` (registro, login, y un endpoint protegido con cada rol)
