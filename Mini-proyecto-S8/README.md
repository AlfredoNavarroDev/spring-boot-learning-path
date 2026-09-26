# Práctica — Semana 8 (Spring Security: autorización por roles / RBAC)

Restringe los endpoints del inventario según el rol del usuario autenticado,
usando los **5 roles reales** del módulo de inventario de la tesis:

```java
enum Rol { PROPIETARIO, ADMINISTRADOR, VENDEDOR, TECNICO, ABASTECEDOR }
```

Después de cada paso corré:

```
mvn compile
```

Para correrlo:

```bash
mvn spring-boot:run
```

## Orden sugerido

### 1. `domain/Rol.java` + columna `rol` en `domain/Usuario.java`
- [ ] `enum Rol { PROPIETARIO, ADMINISTRADOR, VENDEDOR, TECNICO, ABASTECEDOR }` +
      `@Enumerated(EnumType.STRING)`

### 2. `security/JwtService.java` — rol como claim del JWT
- [ ] `Jwts.builder().claim("rol", rol)` — el rol viaja dentro del propio token
- [ ] Diferencia clave vs. Semana 7: `JwtAuthenticationFilter` arma las
      `GrantedAuthority` directo del claim, sin volver a consultar
      `UsuarioRepository` en cada request (trade-off: un cambio de rol no aplica
      hasta que ese token expire y se emita uno nuevo)

### 3. `controller/ProductoController.java` — `@PreAuthorize` por endpoint
- [ ] `GET /api/productos` — cualquier rol autenticado (consultar inventario no es
      operación sensible)
- [ ] `POST /api/productos` — `PROPIETARIO`, `ADMINISTRADOR`, `ABASTECEDOR` (alta
      de producto = decisión de compras/administración)
- [ ] `PATCH /api/productos/{id}/stock` — `PROPIETARIO`, `ADMINISTRADOR`,
      `TECNICO`, `ABASTECEDOR` (`VENDEDOR` solo consulta, no toca inventario)
- [ ] `DELETE /api/productos/{id}` — `PROPIETARIO`, `ADMINISTRADOR` (decisión de
      alto impacto, reservada a los dos roles de mayor jerarquía)

### 4. `exception/GlobalExceptionHandler.java` — `AccessDeniedException` → 403

### 5. Tests (`ProductoRbacIT`, `AuthFlujoJwtIT`)
- [ ] `ProductoRbacIT` — matriz de RBAC completa: cada regla `@PreAuthorize`
      probada con un rol permitido y con uno explícitamente denegado (`403`),
      usando `SecurityMockMvcRequestPostProcessors.user(...).roles(...)`
- [ ] `AuthFlujoJwtIT` — e2e real (sin mocks): registra un `VENDEDOR`, hace login,
      y confirma que el rol viaja correctamente del registro → JWT → decisión de
      `@PreAuthorize` (puede listar, no puede crear)

## Checklist de cierre

- [ ] Puedo diseñar una matriz de permisos por rol y justificar cada fila (no
      solo "porque sí")
- [ ] Entiendo el trade-off de meter el rol en el JWT vs. consultarlo en cada request
- [ ] Sé escribir un test que simule un rol específico sin pasar por login real
- [ ] Probé tanto el camino permitido como el denegado para cada regla
