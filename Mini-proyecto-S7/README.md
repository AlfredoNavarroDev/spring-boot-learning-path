# Práctica — Semana 7 (Spring Security + JWT)

Registro + login con JWT sobre una base de usuarios propia (H2 embebido, sin infra
externa esta semana). Autenticación **stateless**: cada request se valida solo con
el token que trae, sin sesión de servidor.

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

### 1. `domain/Usuario.java` + `repository/UsuarioRepository.java`

### 2. `security/JwtProperties.java` + `security/JwtService.java` — emisión/validación de tokens
- [ ] Responsabilidad única: no sabe nada de usuarios ni de HTTP
- [ ] Secreto de firma (`app.jwt.secret`) viene de la variable de entorno
      `JWT_SECRET`; el valor en `application.yml` es solo el default de desarrollo local

### 3. `security/UsuarioDetailsService.java` + `security/SecurityConfig.java`
- [ ] `PasswordEncoder` (`BCryptPasswordEncoder`) — equivalente a
      `bcrypt.hash()`/`bcrypt.compare()`
- [ ] Sesión `STATELESS`, CSRF deshabilitado (API sin cookies de sesión),
      `/api/auth/**` público, el resto autenticado

### 4. `security/JwtAuthenticationFilter.java` — corre una vez por request
- [ ] Lee el header `Authorization`, y si el token es válido puebla el
      `SecurityContext` antes de que Spring evalúe `authorizeHttpRequests` —
      equivalente a que Passport puebla `req.user`

### 5. `service/AuthService.java` — registro y login
- [ ] `login()` delega la verificación de password al
      `AuthenticationManager`/`DaoAuthenticationProvider`, nunca compara hashes a mano

### 6. `controller/AuthController.java` + `controller/PerfilController.java`
- [ ] `POST /api/auth/registro`, `POST /api/auth/login` → `{ accessToken, tokenType }`
- [ ] `GET /api/perfil` — endpoint protegido

### 7. Tests (`JwtServiceTest`, `AuthServiceTest`, `AuthFlujoCompletoIT`)
- [ ] `JwtServiceTest` — unitario puro: emisión/validación de tokens
- [ ] `AuthServiceTest` — unitario con Mockito: registro, email duplicado, login
- [ ] `AuthFlujoCompletoIT` — **e2e real** (`@SpringBootTest` + `MockMvc`, sin
      mocks): registro → login → acceso a `/api/perfil` con el token recibido.
      Incluye los casos negativos explícitos (sin token, token inválido,
      credenciales inválidas → 401)

## Checklist de cierre

- [ ] Entiendo por qué la autenticación es stateless (nada de sesión de servidor)
- [ ] Sé qué hace cada pieza: `JwtService` (tokens) vs. `JwtAuthenticationFilter`
      (por request) vs. `SecurityConfig` (reglas globales)
- [ ] Nunca comparo hashes de password a mano — siempre vía `AuthenticationManager`
- [ ] Probé el flujo completo (no solo el camino feliz) con un test e2e real
