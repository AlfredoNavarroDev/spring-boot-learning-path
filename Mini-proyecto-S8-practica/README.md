# Práctica — Semana 8 (Spring Security: autorización por roles / RBAC)

Mini-proyecto integrador de la semana 8: restringir los endpoints del inventario
según el rol del usuario autenticado, usando los **5 roles reales** del módulo de
inventario de la tesis:

```java
enum Rol { PROPIETARIO, ADMINISTRADOR, VENDEDOR, TECNICO, ABASTECEDOR }
```

Todos los archivos de lógica tienen comentarios `// TODO` en vez de la solución.
Si te trabás, mirá `../Mini-proyecto-S8` (mismo tema ya resuelto).

Después de cada paso corré:

```
mvn compile
```

Para correrlo:

```bash
mvn spring-boot:run
```

## Orden sugerido

### 1. `domain/Rol.java`, `domain/Usuario.java`, `domain/Producto.java`, `dto/*.java` — ya completos
**Propósito:** son entidades JPA y DTOs planos (sin lógica de negocio) — el punto
de partida sobre el que arma todo lo demás, no hay nada que practicar en un enum
de constantes o un getter/setter.
**Archivos:** `domain/Rol.java`, `domain/Usuario.java`, `domain/Producto.java`,
`dto/LoginRequest.java`, `dto/RegistroRequest.java`, `dto/ProductoRequest.java`,
`dto/ProductoResponse.java`, `dto/AjusteStockRequest.java`, `dto/TokenResponse.java`,
`dto/UsuarioResponse.java`
- [ ] Leé `Rol` — son los 5 roles reales del módulo de inventario, la base de toda
      la matriz de permisos que viene después

### 2. `repository/*.java` — ya completos
**Propósito:** interfaces `JpaRepository`, sin implementación que escribir (Spring
Data las genera solas) — igual que un `Repository<Entity>` de TypeORM inyectado
por decorador, acá se inyecta por constructor.
**Archivos:** `repository/UsuarioRepository.java`, `repository/ProductoRepository.java`
- [ ] Identificá qué método usa cada uno (`findByEmail`, `existsByEmail`, etc.)

### 3. `security/JwtService.java` — rol como claim del JWT
**Propósito:** esta semana el JWT deja de ser solo "quién sos" (el email/subject)
y pasa a llevar también "qué podés hacer" (el rol), como claim propio.
**Archivos:** `security/JwtService.java`, `security/JwtProperties.java` (ya completo)
- [ ] `generarAccessToken` firma el token con `Jwts.builder()...claim("rol", rol)...`
- [ ] `extraerEmail` / `extraerRol` leen de vuelta el `subject` y el claim `"rol"`
- [ ] `esValido` combina email + expiración
- [ ] Pista clave: el rol viaja DENTRO del token — no hace falta volver a la base
      de datos para saber qué rol tiene el usuario mientras el token sea válido

### 4. `security/JwtAuthenticationFilter.java` — authorities directo del claim
**Propósito:** a diferencia de la Semana 7, este filtro no vuelve a consultar
`UsuarioRepository` en cada request — arma las `GrantedAuthority` leyendo el
claim `"rol"` que el JWT ya trae adentro (equivalente a leer `req.user.roles` ya
decodificado por una `JwtStrategy` de Passport).
**Archivos:** `security/JwtAuthenticationFilter.java`, `security/JwtService.java`
- [ ] Si no hay header `Authorization: Bearer ...`, la cadena sigue sin autenticar
- [ ] Si el token es válido, el rol sale de `jwtService.extraerRol(token)` — NUNCA
      de una nueva consulta a `UsuarioRepository`
- [ ] `filterChain.doFilter(request, response)` corre siempre al final, pase lo
      que pase (éxito, token ausente, o excepción)

### 5. `security/SecurityConfig.java`, `security/UsuarioDetailsService.java`, `config/SecurityBeansConfig.java` — cadena de filtros y beans
**Propósito:** cablear el filtro JWT en la cadena de Spring Security y habilitar
`@PreAuthorize` a nivel de método, que es donde vive el RBAC de esta semana.
**Archivos:** `security/SecurityConfig.java`, `security/UsuarioDetailsService.java`,
`config/SecurityBeansConfig.java` (beans triviales, ya completos)
- [ ] `@EnableMethodSecurity` está declarado (habilita `@PreAuthorize`)
- [ ] `/api/auth/**` queda `permitAll()`, el resto exige autenticación
- [ ] `jwtAuthenticationFilter` se agrega ANTES de `UsernamePasswordAuthenticationFilter`
- [ ] `UsuarioDetailsService` solo se usa en el login (verificar password), no en
      requests posteriores — esos pasan por el filtro del paso 4

### 6. `service/AuthService.java` — registro y login (constructor ya armado)
**Propósito:** emitir el JWT con el rol correcto una vez que Spring Security
confirmó la contraseña.
**Archivos:** `service/AuthService.java`
- [ ] `registrar` valida email duplicado, hashea el password y guarda el `Usuario`
      con su `rol`
- [ ] `login` autentica con `AuthenticationManager` y arma el token con
      `jwtService.generarAccessToken(email, usuario.getRol().name())`

### 7. `service/ProductoService.java` — CRUD sin lógica de roles (constructor ya armado)
**Propósito:** el service NO sabe que existen roles — quien decide "puede este rol
llamar a este método" es `@PreAuthorize` en el controller (paso 8), no el service.
**Archivos:** `service/ProductoService.java`
- [ ] `listar`, `crear`, `ajustarStock`, `eliminar` son CRUD plano contra
      `ProductoRepository`, sin ningún `if (rol == ...)` metido adentro

### 8. `controller/ProductoController.java` — matriz de roles con `@PreAuthorize`
**Propósito:** esta es LA lección de la semana — el RBAC vive junto a cada
endpoint con `@PreAuthorize("hasAnyRole(...)")` (equivalente a `@Roles(...)` +
`RolesGuard` en Nest), no en una tabla central separada del código. Las
anotaciones ya están puestas en el archivo — no las borres ni las cambies; sólo
el cuerpo de cada método (la llamada al service) queda en TODO.
**Archivos:** `controller/ProductoController.java`, `service/ProductoService.java`
- [ ] `GET /api/productos` — sin restricción, cualquier rol autenticado
- [ ] `POST /api/productos` — `PROPIETARIO`, `ADMINISTRADOR`, `ABASTECEDOR`
- [ ] `PATCH /api/productos/{id}/stock` — `PROPIETARIO`, `ADMINISTRADOR`,
      `TECNICO`, `ABASTECEDOR`
- [ ] `DELETE /api/productos/{id}` — `PROPIETARIO`, `ADMINISTRADOR`
- [ ] Entendés POR QUÉ cada fila de esa matriz es así (no memorizarla, justificarla)

### 9. `controller/AuthController.java` — registro y login
**Propósito:** exponer `AuthService` como endpoints REST, sin lógica propia.
**Archivos:** `controller/AuthController.java`, `service/AuthService.java`
- [ ] `POST /api/auth/registro` devuelve 201 con el `UsuarioResponse`
- [ ] `POST /api/auth/login` devuelve 200 con el `TokenResponse`

### 10. `exception/*.java` — manejo global de errores
**Propósito:** mapear las excepciones de negocio y de seguridad a códigos HTTP
consistentes, incluyendo la que dispara `@PreAuthorize` cuando el rol no alcanza.
**Archivos:** `exception/EmailDuplicadoException.java`,
`exception/ProductoNoEncontradoException.java`,
`exception/GlobalExceptionHandler.java`, `exception/ApiError.java` (ya completo)
- [ ] `EmailDuplicadoException` / `ProductoNoEncontradoException` → 409 / 404
- [ ] `BadCredentialsException` → 401
- [ ] `AccessDeniedException` (la lanza `@PreAuthorize` cuando el rol no alcanza)
      → 403, equivalente a un `RolesGuard` devolviendo `Forbidden` en Nest
- [ ] `MethodArgumentNotValidException` → 400 con el detalle de campos
- [ ] `Exception` genérica → 500

### 11. Tests (`ProductoRbacIT`, `AuthFlujoJwtIT`) — sin cambios, son el spec
**Propósito:** ya están copiados completos desde la versión resuelta — son la
matriz de RBAC como test ejecutable. Corré `mvn test` contra tu propia
implementación para saber si de verdad quedó bien.
**Archivos:** `src/test/java/.../controller/ProductoRbacIT.java`,
`src/test/java/.../controller/AuthFlujoJwtIT.java`
- [ ] `ProductoRbacIT` prueba cada regla `@PreAuthorize` con un rol permitido Y con
      uno explícitamente denegado (403), usando
      `SecurityMockMvcRequestPostProcessors.user(...).roles(...)` (sin login real)
- [ ] `AuthFlujoJwtIT` es e2e real: registra un `VENDEDOR`, hace login, y confirma
      que el rol viaja correctamente registro → JWT → decisión de `@PreAuthorize`

## Checklist de cierre

- [ ] Puedo diseñar una matriz de permisos por rol y justificar cada fila (no
      solo "porque sí")
- [ ] Entiendo el trade-off de meter el rol en el JWT vs. consultarlo en cada
      request (un cambio de rol no aplica hasta que ese token expire)
- [ ] Sé escribir un test que simule un rol específico sin pasar por login real
- [ ] Probé tanto el camino permitido como el denegado para cada regla
