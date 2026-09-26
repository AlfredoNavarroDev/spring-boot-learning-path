# Práctica — Semana 4 (Spring MVC: REST APIs y controllers)

CRUD REST del inventario **en memoria** (sin base de datos todavía, eso llega en la
Semana 5), pensado para fijar el equivalente directo de un controller de NestJS.

Después de cada paso corré:

```
mvn compile
```

Para correrlo:

```bash
mvn spring-boot:run
```

- Swagger UI: http://localhost:8084/swagger-ui.html
- OpenAPI JSON: http://localhost:8084/v3/api-docs

## Orden sugerido

### 1. `domain/Producto.java` y DTOs (`ProductoRequest`, `ProductoResponse`, `PaginaResponse`) — ya completo
- [ ] Entity + DTOs de entrada/salida separados de la entity

### 2. `exception/` — excepciones + advice global
- [ ] `ProductoDuplicadoException`, `ProductoNoEncontradoException`
- [ ] `GlobalExceptionHandler` (`@RestControllerAdvice`) — equivalente al
      `ExceptionFilter` global de Nest (`@Catch()`)
- [ ] `ApiError` — forma consistente de las respuestas de error
- [ ] Captura `MethodArgumentNotValidException` → 400 automático (equivalente a
      `ValidationPipe`)

### 3. `repository/ProductoRepository.java` y `service/ProductoService.java`
- [ ] Reglas de negocio (duplicados, no encontrado) en el service, sin depender de Spring

### 4. `controller/ProductoController.java` — endpoints REST
- [ ] `GET /api/productos?pagina=0&tamanio=10` — lista paginada
- [ ] `GET /api/productos/{id}` — detalle (404 si no existe)
- [ ] `POST /api/productos` — crea (400 validación, 409 nombre duplicado)
- [ ] `PUT /api/productos/{id}` — actualiza
- [ ] `DELETE /api/productos/{id}` — elimina (204)

### 5. `config/OpenApiConfig.java` — documentación
- [ ] Swagger UI + OpenAPI JSON vía `springdoc-openapi-starter-webmvc-ui`

### 6. Tests (`ProductoServiceTest`, `ProductoControllerTest`)
- [ ] `ProductoServiceTest` — reglas de negocio sin contexto de Spring
- [ ] `ProductoControllerTest` — `@WebMvcTest` + `MockMvc`, service mockeado con `@MockitoBean`

## Checklist de cierre

- [ ] Puedo mapear verbos HTTP a `@GetMapping/@PostMapping/@PutMapping/@DeleteMapping`
- [ ] Separo DTO de entity y valido con Bean Validation (`@NotBlank`, etc.)
- [ ] Centralizo el manejo de errores en un `@RestControllerAdvice`
- [ ] Puedo testear controller y service por separado (`@WebMvcTest` vs. unitario puro)
