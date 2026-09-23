# Semana 7 — Spring Security + JWT

Registro + login con JWT sobre una base de usuarios propia (H2 embebido, sin infra externa esta semana). Autenticación **stateless**: cada request se valida solo con el token que trae, sin sesión de servidor.

## Cómo correrlo

```bash
mvn spring-boot:run
```

```bash
# Registro
curl -X POST http://localhost:8087/api/auth/registro -H "Content-Type: application/json" \
  -d '{"email":"ana@correo.com","password":"clave12345"}'

# Login -> devuelve { "accessToken": "...", "tokenType": "Bearer" }
curl -X POST http://localhost:8087/api/auth/login -H "Content-Type: application/json" \
  -d '{"email":"ana@correo.com","password":"clave12345"}'

# Endpoint protegido
curl http://localhost:8087/api/perfil -H "Authorization: Bearer <token>"
```

## Equivalencias NestJS/Passport → Spring Security

| NestJS (Passport) | Spring Security |
|---|---|
| `JwtStrategy` (`validate()`) | `JwtAuthenticationFilter` (`OncePerRequestFilter`) |
| `AuthGuard('jwt')` en el controller | `SecurityFilterChain.authorizeHttpRequests()` (global) |
| `@UseGuards(JwtAuthGuard)` | `.anyRequest().authenticated()` (o `@PreAuthorize` puntual, Semana 8) |
| `bcrypt.hash()` / `bcrypt.compare()` | `PasswordEncoder` (`BCryptPasswordEncoder`) |
| `JwtService.sign()/verify()` (`@nestjs/jwt`) | `JwtService` propio con `jjwt` |
| `LocalStrategy` + `AuthService.validateUser()` | `AuthenticationManager` + `DaoAuthenticationProvider` |
| Passport popula `req.user` | `SecurityContextHolder` popula el `Authentication` |

## Piezas clave

- **`JwtService`** — responsabilidad única: emitir/validar tokens. No sabe nada de usuarios ni de HTTP.
- **`JwtAuthenticationFilter`** — corre una vez por request, lee el header `Authorization`, y si el token es válido puebla el `SecurityContext` antes de que Spring evalúe `authorizeHttpRequests`.
- **`SecurityConfig`** — sesión `STATELESS`, CSRF deshabilitado (API sin cookies de sesión), `/api/auth/**` público, el resto autenticado.
- **`AuthService.login()`** — delega la verificación de password al `AuthenticationManager`/`DaoAuthenticationProvider` (que ya usa el `PasswordEncoder`); nunca compara hashes a mano.
- El secreto de firma (`app.jwt.secret`) viene de la variable de entorno `JWT_SECRET`; el valor en `application.yml` es solo el default de desarrollo local.

## Tests

```bash
mvn test
```

- `JwtServiceTest` — unitario puro: emisión/validación de tokens.
- `AuthServiceTest` — unitario con Mockito: registro, email duplicado, login.
- `AuthFlujoCompletoIT` — **e2e real** (`@SpringBootTest` + `MockMvc`, sin mocks): registro → login → acceso a `/api/perfil` con el token recibido. Incluye los casos negativos explícitos (sin token, token inválido, credenciales inválidas → 401), no solo el camino feliz.
