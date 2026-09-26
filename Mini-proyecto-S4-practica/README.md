# Práctica — Semana 4 (Spring MVC: REST APIs y controllers)

CRUD REST del inventario, todavía en memoria (sin base de datos, eso llega en
la Semana 5). Completá a mano el `@RestController`, el `@Service` con la
lógica de negocio, el repositorio en memoria y el manejo centralizado de
errores — el equivalente a un CRUD de NestJS con `@Controller`/`@Injectable`/
`class-validator`, pero con las convenciones de Spring MVC. Cada pieza
marcada con `// TODO` tiene el cuerpo reemplazado por un
`throw new UnsupportedOperationException("TODO")`: el proyecto compila tal
cual está, pero los tests fallan hasta que implementes cada método.

```bash
mvn compile
```

Debe compilar limpio desde el principio (los `throw` satisfacen el tipo de
retorno de cada método). Corré `mvn test` para ver cuántos TODOs te faltan.

Para correrlo:

```bash
mvn spring-boot:run
```

- API: http://localhost:8084/api/productos
- Swagger UI: http://localhost:8084/swagger-ui.html

## Orden sugerido

### 1. `domain/Producto.java`, `dto/ProductoRequest.java`, `dto/ProductoResponse.java`, `dto/PaginaResponse.java` — entity y DTOs

**Propósito:** ver la separación DTO (entrada/salida) vs entity de dominio,
con Bean Validation (`@NotBlank`, `@NotNull`, `@Positive`, `@PositiveOrZero`)
en el DTO de entrada — el equivalente a los decoradores de `class-validator`
en un `CreateProductoDto` de NestJS. No requieren trabajo: ya están
completos.

**Archivos:** `domain/Producto.java`, `dto/ProductoRequest.java`,
`dto/ProductoResponse.java`, `dto/PaginaResponse.java`

- [ ] Leer los cuatro archivos y entender por qué el controller nunca
      devuelve `Producto` directo (sin editar nada)

### 2. `exception/` — excepciones de negocio y manejo centralizado

**Propósito:** excepciones de dominio (404 y 409) con su mensaje, y un
`@RestControllerAdvice` único que las traduce a `ResponseEntity<ApiError>` —
el equivalente a un `ExceptionFilter` global (`@Catch()` + `APP_FILTER`) en
NestJS, en vez de manejar errores dispersos en cada controller.

**Archivos:** `exception/ProductoDuplicadoException.java`,
`exception/ProductoNoEncontradoException.java`, `exception/ApiError.java`
(ya completo, es un record plano), `exception/GlobalExceptionHandler.java`

- [ ] `ProductoDuplicadoException(nombre)`: mensaje que incluya el nombre
      duplicado
- [ ] `ProductoNoEncontradoException(id)`: mensaje que incluya el id
      buscado
- [ ] `GlobalExceptionHandler.manejarNoEncontrado`: 404 con
      `ApiError.de(404, ..., ex.getMessage())`
- [ ] `GlobalExceptionHandler.manejarDuplicado`: 409 con
      `ApiError.de(409, ..., ex.getMessage())`
- [ ] `GlobalExceptionHandler.manejarValidacion`: 400 mapeando los
      `FieldError` de `MethodArgumentNotValidException` a `"campo: mensaje"`
- [ ] `GlobalExceptionHandler.manejarGenerico`: 500 con mensaje genérico
      (nunca el mensaje/stacktrace interno de la excepción)

### 3. `repository/ProductoRepository.java` y `service/ProductoService.java` — negocio

**Propósito:** repositorio en memoria (`ConcurrentHashMap` + `AtomicLong`,
seguro ante requests concurrentes) y el service con toda la lógica de
negocio del CRUD — el equivalente al `*.service.ts` de NestJS. El
constructor de `ProductoService` que inyecta `ProductoRepository` ya está
completo, no lo toques.

**Archivos:** `repository/ProductoRepository.java`,
`service/ProductoService.java`

- [ ] `ProductoRepository.findAll/findById/existsByNombre/save/existsById/deleteById`
- [ ] `ProductoService.listar`: paginado manual sobre la lista en memoria
- [ ] `ProductoService.obtener`: buscar por id o lanzar 404
- [ ] `ProductoService.crear`: chequeo de duplicado por nombre antes de
      guardar
- [ ] `ProductoService.actualizar`: buscar, pisar campos, guardar
- [ ] `ProductoService.eliminar`: validar que exista antes de borrar
- [ ] `ProductoService.buscarOFallar` (privado): el `orElseThrow` común

### 4. `controller/ProductoController.java` — endpoints REST

**Propósito:** el controller solo recibe/valida DTOs y delega en el
service, sin lógica de negocio — el equivalente al `@Controller('productos')`
de NestJS. Las anotaciones (`@RestController`, `@RequestMapping`,
`@GetMapping`, etc.) y las firmas de cada método ya están, así Spring sigue
registrando las rutas; solo falta el cuerpo.

**Archivos:** `controller/ProductoController.java`

- [ ] `listar`: delegar en `productoService.listar(pagina, tamanio)` → 200
- [ ] `obtener`: delegar en `productoService.obtener(id)` → 200
- [ ] `crear`: delegar en `productoService.crear(request)` → 201 CREATED
- [ ] `actualizar`: delegar en `productoService.actualizar(id, request)` →
      200
- [ ] `eliminar`: delegar en `productoService.eliminar(id)` → 204 NO
      CONTENT

### 5. `config/OpenApiConfig.java` — documentación (ya completo)

**Propósito:** config declarativa de metadata para Swagger/OpenAPI (un
único `@Bean OpenAPI` armado con builder). No es el objetivo pedagógico de
esta semana, así que ya está completo.

**Archivos:** `config/OpenApiConfig.java`

- [ ] Leer el bean y confirmar que Swagger UI muestra ese título/versión
      (sin editar nada)

No toques `MiniProyectoS4Application.java` — es el arranque de Spring Boot,
no cambia entre pasos.

## Checklist de cierre

- [ ] Cada endpoint usa el verbo HTTP correcto (`GET`/`POST`/`PUT`/`DELETE`)
      con el status code apropiado (200/201/204/404/409/400)
- [ ] El controller nunca expone la entity de dominio: siempre DTO de
      entrada validado con Bean Validation (`ProductoRequest`) y DTO de
      salida (`ProductoResponse`)
- [ ] Los errores se manejan en un único `@RestControllerAdvice`, no con
      `try/catch` dispersos en el controller
- [ ] Sé testear el controller (`@WebMvcTest` + `MockMvc`, mockeando el
      service) por separado del service (test unitario puro, sin contexto de
      Spring)

¿Te trabaste? La versión resuelta está en [`../Mini-proyecto-S4`](../Mini-proyecto-S4) — mismo código, sin TODOs.
