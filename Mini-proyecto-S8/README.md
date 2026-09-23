# Semana 8 — Spring Security: autorización por roles (RBAC)

Restringe los endpoints del inventario según el rol del usuario autenticado, usando los **5 roles reales** del módulo de inventario de la tesis.

## Los 5 roles

```java
enum Rol { PROPIETARIO, ADMINISTRADOR, VENDEDOR, TECNICO, ABASTECEDOR }
```

| Endpoint | Roles permitidos | Por qué |
|---|---|---|
| `GET /api/productos` | Cualquier rol autenticado | Consultar inventario no es una operación sensible. |
| `POST /api/productos` | `PROPIETARIO`, `ADMINISTRADOR`, `ABASTECEDOR` | Dar de alta un producto es una decisión de compras/administración. |
| `PATCH /api/productos/{id}/stock` | `PROPIETARIO`, `ADMINISTRADOR`, `TECNICO`, `ABASTECEDOR` | Ajustar stock es tarea operativa diaria; `VENDEDOR` solo consulta, no toca inventario. |
| `DELETE /api/productos/{id}` | `PROPIETARIO`, `ADMINISTRADOR` | Decisión de alto impacto, reservada a los dos roles de mayor jerarquía. |

## Equivalencias NestJS → Spring Security RBAC

| NestJS | Spring Security |
|---|---|
| `enum Rol { ... }` + columna `rol` en la entity | `enum Rol` + `@Enumerated(EnumType.STRING)` |
| `@Roles(Rol.ADMIN, Rol.PROPIETARIO)` + `@UseGuards(RolesGuard)` | `@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROPIETARIO')")` |
| `RolesGuard` lee `req.user.roles` (ya decodificado por la JwtStrategy) | `JwtAuthenticationFilter` arma las `GrantedAuthority` desde el claim `rol` del JWT, sin volver a consultar la base |
| `ForbiddenException` (403) | `AccessDeniedException` → capturada en el `GlobalExceptionHandler` → 403 |
| Rol como campo del JWT payload | Rol como *claim* (`Jwts.builder().claim("rol", rol)`) |

## Diferencia clave vs. la Semana 7

En la Semana 7 el filtro JWT volvía a consultar `UsuarioRepository` en cada request para armar las authorities. Acá el rol viaja **dentro del propio JWT** como claim — el filtro arma las `GrantedAuthority` directo del token, sin round-trip a la base de datos en cada llamada (el trade-off: si cambiás el rol de un usuario, el cambio no aplica hasta que ese token expire y se emita uno nuevo).

## Tests

```bash
mvn test
```

- **`ProductoRbacIT`** — matriz de RBAC completa: cada regla `@PreAuthorize` se prueba con un rol permitido y con uno explícitamente denegado (`403`), usando `SecurityMockMvcRequestPostProcessors.user(...).roles(...)` para simular cada rol sin pasar por login real.
- **`AuthFlujoJwtIT`** — e2e real (sin mocks): registra un `VENDEDOR`, hace login, y confirma que el rol viaja correctamente del registro → JWT → decisión de `@PreAuthorize` (puede listar, no puede crear).
