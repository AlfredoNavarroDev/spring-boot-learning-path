# Spring Boot Learning Path

Repositorio de práctica para mi ruta de aprendizaje de **Spring Boot**, orientada a un backend developer con experiencia previa en **NestJS + TypeORM**. La idea del roadmap es aprovechar ese conocimiento previo (inyección de dependencias, controllers, DTOs, ORM, guards, testing) y traducirlo al ecosistema Java/Spring, en vez de aprender desde cero.

Como hilo conductor arranco con el módulo de inventario de mi tesis (productos, categorías, sedes, 5 roles) y derivo hacia el **proyecto de portafolio del track: un Motor de Scoring Crediticio y Detección de Fraude** — reglas de negocio, resiliencia ante fallos externos, seguridad por roles y despliegue real. No es otro CRUD de inventario.

La ruta dura **10 semanas**, agrupadas en 3 niveles. Cada semana cierra con un mini-proyecto integrador que vive en su propia carpeta (`Mini-proyecto-S<N>/` o `semana<N>-practica/`).

## Stack y contexto de la ruta

- **Java 21 (LTS)** — distribución Temurin/Corretto.
- **Spring Boot 4.1.x** sobre **Maven** (nota: en Boot 4 la autoconfiguración de Flyway se movió a su propio starter, `spring-boot-starter-flyway` — ver README de la Semana 9).
- **PostgreSQL** + **Flyway** para persistencia y migraciones.
- **Redis** para caching.
- **Resilience4j** para circuit breaker / retry.
- **Spring Security + JWT** para autenticación y autorización por roles.
- **JUnit 5 + Mockito + MockMvc** para testing.
- **Docker / Docker Compose** para levantar el sistema completo.
- **Cloudflare + Render + Neon** para el despliegue real (free tier).
- **GitHub Actions** para CI.

## Roadmap semana a semana

### 🟡 Básico — Fundamentos + lógica de negocio

**Semana 1 — Fundamentos de Java moderno** ✅
**Carpeta:** [`Mini-proyecto-S1/`](./Mini-proyecto-S1)

Java puro, sin framework. Tipado estático, clases, modificadores de acceso e interfaces (contra TypeScript), **records** como DTOs inmutables, **Stream API** básica, excepciones **checked vs unchecked** y `Optional<T>`, y **Maven** como el `package.json`/npm de Java.

**Mini-proyecto:** modelar en Java puro una mini versión del inventario — record `Producto`, clase `Inventario` con `List<Producto>`, búsquedas y cálculos con Stream API, y una excepción personalizada para productos duplicados.

**Semana 2 — Spring Core y Arquitectura (DI)** ✅
**Carpeta:** [`semana02-practica/`](./semana02-practica)

Cómo Spring resuelve DI y modularidad, lo mismo que NestJS hace con `@Injectable()` y módulos. `ApplicationContext`, `@Component`/`@Service`/`@Repository`, inyección por constructor vs `@Autowired`, `@Configuration` + `@Bean`, `@ComponentScan` y paquetes por capa, ciclo de vida (`@PostConstruct`/`@PreDestroy`), `@Value` y **profiles**.

**Mini-proyecto:** capa de servicios del inventario (`InventarioService`, `ProductoService`, `NotificacionService`) interconectada por DI, con un bean de configuración y un profile `dev`.

**Semana 3 — Streams API y motor de reglas de validación** ✅ Completado
**Carpeta:** [`Mini-proyecto-S3/`](./Mini-proyecto-S3)

Java puro. Pensar las reglas de negocio como **funciones componibles**, no como un `if` gigante: interfaces funcionales (`Predicate<T>`, `Function`, `Supplier`, `Consumer`) y composición `.and()/.or()/.negate()`, `Optional` como valor de retorno, **Stream API** (`filter`, `map`, `collect(groupingBy/summingDouble)`), y un motor de reglas (`MotorValidacion` + `RegistroRegla`).

**Mini-proyecto:** motor de detección de fraude sobre `Transaccion` — 4 reglas (monto sobre límite, país bloqueado, velocity check, horario inusual) que devuelven los motivos de rechazo.

### 🟠 Intermedio — APIs REST + resiliencia

**Semana 4 — Spring MVC: REST APIs y controllers** ✅

Exponer servicios como API REST, replicando patrones de NestJS. `@RestController`, `@RequestMapping` y verbos (`@GetMapping`, `@PostMapping`, etc.), `@RequestBody`/`@PathVariable`/`@RequestParam`, DTOs de entrada/salida con records, CRUD con `ResponseEntity<T>`, **Bean Validation** (`@NotNull`, `@Valid`), manejo global de errores con `@ControllerAdvice` + `@ExceptionHandler`, y documentación con **springdoc-openapi** (Swagger).

**Mini-proyecto:** [`Mini-proyecto-S4/`](./Mini-proyecto-S4) — API REST completa en memoria (sin BD) del inventario, con CRUD, validación, manejo global de excepciones y Swagger.

**Semana 5 — Spring Data JPA + PostgreSQL** ✅

Persistir en PostgreSQL con Spring Data JPA (paralelismo casi directo con TypeORM). `@Entity`, `@Id`, `@GeneratedValue`, `@Column`, relaciones `@OneToMany`/`@ManyToOne` con `mappedBy` y `@JoinColumn`, lazy vs eager, `JpaRepository` y **queries derivadas**, `@Query` JPQL, y migraciones con **Flyway**.

**Mini-proyecto:** [`Mini-proyecto-S5/`](./Mini-proyecto-S5) — API conectada a PostgreSQL real (docker-compose) — entidades `Producto`/`Categoria`/`Sede`, repositorios con queries derivadas y personalizadas, migraciones Flyway, y tests de integración con Testcontainers.

**Semana 6 — Resilience4j + Redis: APIs externas resilientes** ✅

Hacer que la API sobreviva a servicios externos que fallan. **Circuit Breaker**, **Retry** con backoff, **Rate Limiter** (Resilience4j), y caching con **Redis** (`@Cacheable`). El patrón estándar para consumir APIs de terceros en banca/fintech.

**Mini-proyecto:** [`Mini-proyecto-S6/`](./Mini-proyecto-S6) — consumo resiliente de un "buró de crédito" simulado (con cache Redis, circuit breaker y fallback), conectado al motor de reglas de la Semana 3 en un endpoint real.

### 🔴 Avanzado — Seguridad, testing y cloud real

**Semana 7 — Spring Security + JWT** ✅

Proteger la API con autenticación JWT. `SecurityFilterChain`, `UserDetails`, `BCryptPasswordEncoder`, `JwtService` y endpoint `/auth/login`, `JwtAuthenticationFilter` (equivalente a una `JwtStrategy` de Passport), sesión `STATELESS` y CSRF deshabilitado.

**Mini-proyecto:** [`Mini-proyecto-S7/`](./Mini-proyecto-S7) — registro + login con JWT sobre una base de usuarios propia, con test e2e real (registro → login → endpoint protegido).

**Semana 8 — Spring Security: autorización por roles (RBAC)** ✅

Controlar qué puede hacer cada usuario según su rol. Enum con los **5 roles** de la tesis (`PROPIETARIO`, `ADMINISTRADOR`, `VENDEDOR`, `TECNICO`, `ABASTECEDOR`), rol como claim en el JWT, `@PreAuthorize("hasRole(...)")` a nivel de método (equivalente a `@Roles()` + `RolesGuard`), y `AccessDeniedException` → 403.

**Mini-proyecto:** [`Mini-proyecto-S8/`](./Mini-proyecto-S8) — endpoints del inventario restringidos por rol, con matriz de tests RBAC (permitido + denegado por regla).

**Semana 9 — Testing, Docker y Proyecto Final** ✅

Cerrar con buenas prácticas. **JUnit 5** (`@Test`, `@BeforeEach`, assertions), **Mockito** (`@Mock`, `when().thenReturn()`), testing de controllers con `@WebMvcTest` + **MockMvc**, integración con `@SpringBootTest` (+ Testcontainers), **Dockerfile multi-stage** + `docker-compose`, y **CI con GitHub Actions**.

**Mini-proyecto (final):** [`Mini-proyecto-S9/`](./Mini-proyecto-S9) — sistema de inventario completo: entidades JPA, CRUD, validación, JWT con 5 roles, Swagger, tests en las 3 capas (unitario/MockMvc/Testcontainers), corriendo vía `docker-compose up`.

**Semana 10 — Cloud real: Cloudflare + Render** ✅

Llevar el sistema a un entorno real sin gastar. **Cloudflare** (DNS, CDN/WAF, Access), deploy a **Render** con Docker, **Neon** para Postgres, CI/CD con GitHub Actions, y el mapeo AWS/Azure para hablarlo en entrevista.

**Mini-proyecto:** [`Mini-proyecto-S10/`](./Mini-proyecto-S10) — `render.yaml`, workflow de CD (test → deploy hook), y guía paso a paso para el despliegue real del sistema de la Semana 9. El deploy en vivo requiere cuentas propias del usuario en Render/Neon/Cloudflare; queda documentado y verificado localmente (build de imagen + docker-compose), no automatizado sin esas credenciales.

## Proyecto de portafolio del track

**Motor de Scoring Crediticio y Detección de Fraude** — reglas de negocio componibles, resiliencia ante fallos externos, seguridad por roles y despliegue real. Es el mismo patrón de un sistema real de banca/fintech, no otro CRUD de inventario.

## Progreso

| Semana | Tema | Estado |
|---|---|---|
| 1 | Fundamentos de Java moderno | ✅ Completado |
| 2 | Spring Core y arquitectura (DI) | ✅ Completado |
| 3 | Streams API y motor de reglas | ✅ Completado |
| 4 | Spring MVC — REST APIs y controllers | ✅ Completado |
| 5 | Spring Data JPA + PostgreSQL | ✅ Completado |
| 6 | Resilience4j + Redis | ✅ Completado |
| 7 | Spring Security + JWT | ✅ Completado |
| 8 | Spring Security — RBAC | ✅ Completado |
| 9 | Testing, Docker y Proyecto Final | ✅ Completado |
| 10 | Cloud real — Cloudflare + Render | ✅ Completado |

## Por qué este enfoque

Cada semana está diseñada como una traducción directa desde NestJS/TypeORM hacia su equivalente en Spring (DI, controllers, DTOs, ORM, guards de roles, testing), para acelerar el aprendizaje apoyándome en conceptos que ya domino en vez de partir de cero. Usar un dominio real (primero el inventario de la tesis, luego un motor de scoring/fraude) convierte la ruta en una pieza de portafolio comparable: el mismo tipo de sistema resuelto en dos stacks distintos.
