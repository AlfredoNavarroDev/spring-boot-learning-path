# Spring Boot Learning Path

Repositorio de práctica para mi ruta de aprendizaje de **Spring Boot**, orientada a un backend developer con experiencia previa en **NestJS + TypeORM**. La idea del roadmap es aprovechar ese conocimiento previo (inyección de dependencias, controllers, DTOs, ORM, guards, testing) y traducirlo al ecosistema Java/Spring, en vez de aprender desde cero.

Como dominio de referencia para todos los mini-proyectos uso el mismo sistema de mi tesis: un **módulo de inventario** con productos, categorías, sedes y 5 roles de usuario (propietario, administrador, vendedor, técnico, abastecedor). Esto convierte la ruta en una pieza de portafolio: el mismo problema de negocio resuelto en dos stacks distintos.

La ruta completa dura **6 semanas**, con un mini-proyecto integrador al final de cada semana. Cada mini-proyecto vive en su propia carpeta `Mini-proyecto-S<N>/`.

## Stack y contexto de la ruta

- **Java 21 (LTS)** — distribución Temurin/Corretto. Es el baseline que más piden las vacantes actuales; evita "desaprender" hábitos de versiones antiguas (8/11/17).
- **Spring Boot 3.x** sobre **Maven**.
- **PostgreSQL** + **Flyway** para persistencia y migraciones.
- **Spring Security + JWT** para autenticación y autorización basada en roles.
- **JUnit 5 + Mockito + MockMvc** para testing.
- **Docker / Docker Compose** para levantar el sistema completo.
- **GitHub Actions** para CI.

## Roadmap semana a semana

### Semana 1 — Fundamentos de Java moderno
**Carpeta:** [`Mini-proyecto-S1/`](./Mini-proyecto-S1)

Antes de tocar Spring hay que dominar la sintaxis y el paradigma de Java moderno. Esta semana es Java puro, sin framework.

Áreas que cubre:
- Tipado estático, clases, constructores, modificadores de acceso e interfaces (comparado contra TypeScript).
- **Records** como equivalente a los DTOs inmutables de TypeScript.
- **Stream API** (`filter`, `map`, `collect`, `reduce`, `Collectors.groupingBy`) — el paralelo directo con `.filter()/.map()/.reduce()` de JS.
- Manejo de excepciones **checked vs unchecked** (concepto que no existe en JS/TS) y `Optional<T>` como alternativa segura a `null`.
- **Maven** como el `package.json`/npm de Java: estructura de proyecto, `pom.xml`, `mvn compile/test/package`.

Fundamentos que quedan interiorizados: sintaxis base de Java, inmutabilidad con records, programación funcional con streams, manejo seguro de nulos y excepciones, y gestión de dependencias con Maven.

**Mini-proyecto:** modelar en Java puro (sin Spring) una mini versión del inventario — un record `Producto`, una clase `Inventario` con `List<Producto>`, búsquedas y cálculos con Stream API, y una excepción personalizada para productos duplicados.

### Semana 2 — Spring Core y arquitectura (Inyección de Dependencias)
Objetivo: entender cómo Spring resuelve DI y modularidad, los mismos problemas que NestJS resuelve con `@Injectable()` y módulos, pero vía anotaciones y un contenedor IoC propio.

Áreas que cubre:
- El `ApplicationContext` como contenedor que crea y gestiona los beans.
- `@Component` / `@Service` / `@Repository` (equivalentes a `@Injectable()`) e inyección por constructor vs `@Autowired` por campo.
- `@Configuration` y `@Bean` para registrar objetos manualmente en el contenedor.
- `@ComponentScan` y organización de paquetes por capa (`controller`, `service`, `repository`, `dto`, `entity`).
- Ciclo de vida de beans (`@PostConstruct`/`@PreDestroy`) y configuración externa con `@Value` (equivalente a `ConfigService`).
- **Profiles** (`@Profile`, `application-dev.properties`) como equivalente a los `.env` por entorno.

Fundamentos que quedan interiorizados: el contenedor IoC de Spring, los distintos scopes de beans, y cómo estructurar un proyecto Spring por capas.

**Mini-proyecto:** capa de servicios del inventario (`InventarioService`, `ProductoService`, `NotificacionService`) interconectada por DI, con un bean de configuración de reglas de negocio y un profile `dev`.

### Semana 3 — Spring MVC: REST APIs y controllers
Objetivo: exponer los servicios como una API REST real, replicando patrones ya conocidos de NestJS (controllers, DTOs, validación, manejo global de errores).

Áreas que cubre:
- `@RestController`, `@RequestMapping` y los verbos HTTP (`@GetMapping`, `@PostMapping`, etc.) — equivalentes a `@Controller()`/`@Get()` de Nest.
- `@RequestBody`, `@PathVariable`, `@RequestParam` (equivalentes a `@Body()`, `@Param()`, `@Query()`).
- DTOs de entrada/salida con records, sin exponer entidades directamente.
- CRUD completo con `ResponseEntity<T>` y códigos de estado HTTP correctos.
- **Bean Validation** (`@NotNull`, `@NotBlank`, `@Min`, `@Valid`) — equivalente a `class-validator`.
- Manejo global de excepciones con `@ControllerAdvice` + `@ExceptionHandler` (equivalente a un exception filter global).
- Documentación automática con **springdoc-openapi** (Swagger), equivalente a `@nestjs/swagger`.

Fundamentos que quedan interiorizados: diseño de APIs REST en Spring MVC, separación DTO/entidad, validación declarativa y manejo centralizado de errores.

**Mini-proyecto:** API REST completa (en memoria, sin BD todavía) del inventario, con CRUD, validación, manejo global de excepciones y Swagger funcionando, probada con Postman/Insomnia.

### Semana 4 — Spring Data JPA + PostgreSQL
Objetivo: persistir datos reales en PostgreSQL con Spring Data JPA. Es la semana con el paralelismo más directo respecto a TypeORM.

Áreas que cubre:
- `@Entity`, `@Id`, `@GeneratedValue`, `@Column` (equivalentes a `@Entity()`, `@PrimaryGeneratedColumn()`, `@Column()` de TypeORM).
- Relaciones `@OneToMany`/`@ManyToOne` con `mappedBy` y `@JoinColumn`, y la diferencia entre **lazy vs eager loading**.
- `JpaRepository<T, ID>` y **queries derivadas** por nombre de método (`findByNombreContaining`, `findByStockLessThan`).
- Queries personalizadas con `@Query` (JPQL) y `@Param` para reportes más complejos.
- Migraciones versionadas con **Flyway** (`V1__init.sql`, `V2__...`), equivalente a las migraciones de TypeORM.
- PostgreSQL local vía Docker.

Fundamentos que quedan interiorizados: mapeo objeto-relacional con JPA/Hibernate, diseño de relaciones entre entidades, y control de esquema de base de datos versionado.

**Mini-proyecto:** conectar la API de la semana 3 a PostgreSQL real, con entidades `Producto`/`Categoría`/`Sede` relacionadas, repositorios con queries derivadas y personalizadas, y migraciones Flyway.

### Semana 5 — Spring Security + JWT
Objetivo: proteger la API con autenticación JWT y autorización basada en roles, replicando el flujo ya implementado en NestJS para la tesis.

Áreas que cubre:
- Fundamentos de Spring Security: `Authentication`, `SecurityContext`, `UserDetails`.
- Entidad `Usuario` y modelado de los **5 roles** (`PROPIETARIO`, `ADMINISTRADOR`, `VENDEDOR`, `TECNICO`, `ABASTECEDOR`) como enum, con contraseñas hasheadas vía `BCryptPasswordEncoder`.
- Configuración de `SecurityFilterChain`: rutas públicas vs protegidas, CSRF deshabilitado y sesión `STATELESS` para una API basada en tokens.
- Generación y validación de JWT (`JwtService`) y endpoint `/auth/login`.
- `JwtAuthenticationFilter` personalizado que valida el token en cada request (equivalente a una `JwtStrategy` de Passport).
- Autorización por rol a nivel de método con `@PreAuthorize("hasRole('...')")` (equivalente a `@Roles()` + `RolesGuard`).

Fundamentos que quedan interiorizados: el filter chain de Spring Security, emisión/validación de JWT, y control de acceso granular por rol.

**Mini-proyecto:** sistema de auth completo sobre la API de inventario — registro, login con JWT, los 5 roles de la tesis funcionando, y endpoints protegidos según el rol correspondiente.

### Semana 6 — Testing, Docker y proyecto final
Objetivo: cerrar la ruta con buenas prácticas de testing, contenedores y un proyecto integrador de portafolio.

Áreas que cubre:
- **JUnit 5**: `@Test`, `@BeforeEach`, `@AfterEach` y assertions (`assertEquals`, `assertThrows`).
- **Mockito**: `@Mock`, `@InjectMocks`, `when().thenReturn()` para aislar dependencias al testear servicios (equivalente a `jest.mock()`).
- Testing de controllers con `@WebMvcTest` + `MockMvc` (equivalente a `supertest`).
- Tests de integración con `@SpringBootTest` y, opcionalmente, Testcontainers para levantar PostgreSQL real en los tests.
- Logging con **SLF4J** en vez de `System.out.println`.
- **Dockerfile multi-stage** (build con Maven, runtime JDK 21 slim) integrado al `docker-compose.yml` junto a PostgreSQL.
- **CI con GitHub Actions**: workflow que corre `mvn test` en cada push/PR.

Fundamentos que quedan interiorizados: pirámide de testing en Spring (unitario, MockMvc, integración), containerización de una app Spring Boot, y automatización de pruebas en CI.

**Mini-proyecto (final):** sistema de inventario completo — entidades, CRUD, validación, auth JWT con los 5 roles, tests unitarios y de integración, Swagger, y todo corriendo vía `docker-compose up`. Cierra con un README propio del mini-proyecto y actualización del CV como evidencia de aprendizaje activo.

## Progreso

| Semana | Tema | Estado |
|---|---|---|
| 1 | Fundamentos de Java moderno | ✅ Completado |
| 2 | Spring Core y arquitectura (DI) | ⬜ Pendiente |
| 3 | Spring MVC — REST APIs y controllers | ⬜ Pendiente |
| 4 | Spring Data JPA + PostgreSQL | ⬜ Pendiente |
| 5 | Spring Security + JWT | ⬜ Pendiente |
| 6 | Testing, Docker y proyecto final | ⬜ Pendiente |

## Por qué este enfoque

Cada semana está diseñada como una traducción directa desde NestJS/TypeORM hacia su equivalente en Spring (DI, controllers, DTOs, ORM, guards de roles, testing), para acelerar el aprendizaje apoyándome en conceptos que ya domino en vez de partir de cero. Usar el mismo dominio de negocio de la tesis (inventario con productos, sedes, categorías y 5 roles) en todos los mini-proyectos convierte esta ruta en una pieza de portafolio comparable: el mismo sistema resuelto en dos stacks distintos.
