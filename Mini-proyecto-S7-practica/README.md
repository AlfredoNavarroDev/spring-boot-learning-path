# Práctica — Semana 7 (Spring Security + JWT)

Reimplementá registro + login con JWT sobre una base de usuarios propia (H2
embebido, sin infra externa esta semana). Autenticación **stateless**: cada
request se valida solo con el token que trae, sin sesión de servidor. El código
real fue reemplazado por `TODO`/`throw new UnsupportedOperationException(...)`;
la versión resuelta está en [`../Mini-proyecto-S7`](../Mini-proyecto-S7) por si
te trabás.

Después de cada paso corré:

```
mvn compile
```

Para correrlo:

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

## Orden sugerido

### 1. `domain/Usuario.java` + `dto/*.java` + `repository/UsuarioRepository.java` — ya completo
**Propósito:** entity JPA y DTOs planos de entrada/salida, sin lógica que completar.
**Archivos:** `domain/Usuario.java`, `dto/LoginRequest.java`, `dto/RegistroRequest.java`,
`dto/TokenResponse.java`, `dto/UsuarioResponse.java`, `repository/UsuarioRepository.java`
- [ ] Entendés por qué `UsuarioResponse` nunca expone `passwordHash`

### 2. `security/JwtProperties.java` + `security/JwtService.java` — emisión/validación de tokens
**Propósito:** responsabilidad única — emitir y validar JWT. No sabe nada de usuarios de
base de datos ni de HTTP (equivalente a una `JwtStrategy`/`JwtService` de Nest, pero
invocado explícitamente en vez de por decorators).
**Archivos:** `security/JwtProperties.java` (completo), `security/JwtService.java` (TODO)
- [ ] `generarAccessToken` — `subject` = email, claim custom `"rol"`, `issuedAt`,
      `expiration` a partir de `accessTokenMinutes`, firmado con `signWith(signingKey)`
- [ ] `extraerEmail`/`esValido`/`estaExpirado` — parseo y verificación de firma con
      `Jwts.parser().verifyWith(signingKey)...`
- [ ] Secreto de firma (`app.jwt.secret`) viene de la variable de entorno `JWT_SECRET`;
      el valor en `application.yml` es solo el default de desarrollo local

### 3. `security/UsuarioDetailsService.java` + `security/SecurityConfig.java`
**Propósito:** puente entre `UsuarioRepository` y el `UserDetails` que Spring Security
espera, más las reglas globales de la cadena de filtros HTTP.
**Archivos:** `security/UsuarioDetailsService.java` (TODO), `security/SecurityConfig.java` (TODO),
`config/SecurityBeansConfig.java` (ya completo — beans triviales, sin lógica real)
- [ ] `loadUserByUsername` — busca por email, `orElseThrow` a `UsernameNotFoundException`,
      mapea a `User` con authority `"ROLE_" + rol`
- [ ] `securityFilterChain` — CSRF disabled, sesión `STATELESS`, `/api/auth/**` público,
      el resto autenticado, filtro JWT antes de `UsernamePasswordAuthenticationFilter`
- [ ] `PasswordEncoder` (`BCryptPasswordEncoder`) — equivalente a `bcrypt.hash()`/`bcrypt.compare()`

### 4. `security/JwtAuthenticationFilter.java` — corre una vez por request
**Propósito:** equivalente a una `JwtStrategy` de Passport corriendo como filtro de
servlet: lee el header, valida el token y puebla el `SecurityContext` antes de que
Spring evalúe `authorizeHttpRequests`/`@PreAuthorize`.
**Archivos:** `security/JwtAuthenticationFilter.java` (TODO)
- [ ] Sin header `Bearer` → deja pasar la request tal cual
- [ ] Con header → extrae email, carga `UserDetails`, valida el token, puebla el
      `SecurityContext`; cualquier excepción se traga (no se propaga) y limpia el contexto
- [ ] `filterChain.doFilter(request, response)` al final SIEMPRE se ejecuta — no rompe
      la cadena de filtros aunque el resto del método no haga nada

### 5. `service/AuthService.java` — registro y login
**Propósito:** lógica de negocio de auth, sin acoplarse a HTTP.
**Archivos:** `service/AuthService.java` (TODO; constructor de inyección ya completo)
- [ ] `registrar` — chequea email duplicado, hashea el password con `PasswordEncoder`,
      guarda y mapea a `UsuarioResponse`
- [ ] `login` — delega la verificación de password al `AuthenticationManager`, nunca
      compara hashes a mano

### 6. `controller/AuthController.java` + `controller/PerfilController.java` + `exception/*.java`
**Propósito:** capa HTTP (solo orquesta, sin lógica) y manejo centralizado de errores,
equivalente a un `ExceptionFilter` global de Nest (`@Catch()` + `APP_FILTER`).
**Archivos:** `controller/AuthController.java` (TODO), `controller/PerfilController.java` (TODO),
`exception/EmailDuplicadoException.java` (TODO), `exception/GlobalExceptionHandler.java` (TODO),
`exception/ApiError.java` (ya completo — DTO plano)
- [ ] `POST /api/auth/registro` → 201 + `UsuarioResponse`
- [ ] `POST /api/auth/login` → 200 + `TokenResponse { accessToken, tokenType }`
- [ ] `GET /api/perfil` — endpoint protegido, responde con `principal.getName()`
- [ ] `EmailDuplicadoException` → 409, `BadCredentialsException` → 401 con mensaje
      genérico (nunca revela si el email existe), validación → 400, resto → 500 genérico

### 7. Tests (`JwtServiceTest`, `AuthServiceTest`, `AuthFlujoCompletoIT`) — ya completo
**Propósito:** verificar tu implementación sin tocarlos (definen el contrato esperado).
- [ ] `JwtServiceTest` — unitario puro: emisión/validación de tokens
- [ ] `AuthServiceTest` — unitario con Mockito: registro, email duplicado, login
- [ ] `AuthFlujoCompletoIT` — **e2e real** (`@SpringBootTest` + `MockMvc`, sin mocks):
      registro → login → acceso a `/api/perfil` con el token recibido. Incluye los
      casos negativos explícitos (sin token, token inválido, credenciales inválidas → 401)

## Checklist de cierre

- [ ] Entiendo por qué la autenticación es stateless (nada de sesión de servidor)
- [ ] Sé qué hace cada pieza: `JwtService` (tokens) vs. `JwtAuthenticationFilter`
      (por request) vs. `SecurityConfig` (reglas globales)
- [ ] Nunca comparo hashes de password a mano — siempre vía `AuthenticationManager`
- [ ] Probé el flujo completo (no solo el camino feliz) con un test e2e real, incluyendo
      los casos negativos (sin token, token inválido, credenciales inválidas)
