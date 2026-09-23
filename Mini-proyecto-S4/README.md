# Semana 4 — Spring MVC: REST APIs y controllers

CRUD REST del inventario **en memoria** (sin base de datos todavia, eso llega en la Semana 5), pensado para fijar el equivalente directo de lo que ya conoces de NestJS.

## Cómo correrlo

```bash
mvn spring-boot:run
```

- Swagger UI: http://localhost:8084/swagger-ui.html
- OpenAPI JSON: http://localhost:8084/v3/api-docs

## Equivalencias NestJS → Spring MVC

| NestJS | Spring MVC |
|---|---|
| `@Controller('productos')` | `@RestController @RequestMapping("/api/productos")` |
| `@Get()/@Post()/@Put()/@Delete()` | `@GetMapping/@PostMapping/@PutMapping/@DeleteMapping` |
| `@Body() dto: CreateProductoDto` | `@Valid @RequestBody ProductoRequest` |
| DTO con `class-validator` (`@IsNotEmpty()`) | `record` con Bean Validation (`@NotBlank`) |
| `@Param('id')` | `@PathVariable Long id` |
| `@Query()` | `@RequestParam` |
| `ExceptionFilter` global (`@Catch()`) | `@RestControllerAdvice` + `@ExceptionHandler` |
| `ValidationPipe` (400 automático) | `MethodArgumentNotValidException` capturada en el advice |
| `@nestjs/swagger` (`DocumentBuilder`) | `springdoc-openapi-starter-webmvc-ui` |

## Endpoints

- `GET /api/productos?pagina=0&tamanio=10` — lista paginada
- `GET /api/productos/{id}` — detalle (404 si no existe)
- `POST /api/productos` — crea (400 si falla validación, 409 si el nombre está duplicado)
- `PUT /api/productos/{id}` — actualiza
- `DELETE /api/productos/{id}` — elimina (204)

## Tests

```bash
mvn test
```

- `ProductoServiceTest` — reglas de negocio (duplicados, no encontrado) sin contexto de Spring.
- `ProductoControllerTest` — capa web con `@WebMvcTest` + `MockMvc`, mockeando el service con `@MockitoBean` (equivalente a mockear el provider en un `Test.createTestingModule` de Nest).
