# Práctica — Semana 2 (Spring Core / DI)

Mini-proyecto integrador de la semana 2: rearmar la capa de servicios del
inventario usando **solo** el contenedor IoC de Spring (sin web, sin BD).
Todos los archivos tienen comentarios `TODO` en vez de la solución. Si te
trabás, mirá `../semana02` (ahí está el mismo tema ya resuelto, día a día).

Después de cada paso corré:

```
mvn compile
```

para verificar que compila. Cuando termines una parte, descomentá el bloque
correspondiente en `Semana02PracticaApplication.java` y corré:

```
mvn spring-boot:run
```

## Orden sugerido

### 1. `entity/Producto.java` — record inmutable (ya completo)
- [x] Record con `nombre`, `stock`, `precio`. Sin lógica de negocio.

### 2. `repository/ProductoRepository.java` — `@Repository`
- [x] `List<Producto>` en memoria con 5 productos
- [x] `buscarPorNombre(String)` → `null` si no existe (con stream + `equalsIgnoreCase`)
- [x] `listarTodos()`

### 3. `service/` — `@Service` + constructor injection
- [x] `ProductoService` inyecta `ProductoRepository` por **constructor** (`private final`)
- [x] `NotificacionService.enviarAlerta(String)` imprime `[NOTIFICACIÓN] ...`

### 4. `component/StockValidator.java` — `@Component`
- [x] `estaBajo(Producto, int umbral)` → `stock <= umbral`
- [x] `noExiste(Producto)` → `producto == null`
- [x] `InventarioService` cablea `ProductoService` + `NotificacionService` + `StockValidator`

### 5. `dto/ProductoStockDto.java` — no devolvás la entity
- [x] `revisarStock(...)` devuelve el DTO con `estado` = `"OK"` / `"ALERTA"` / `"NO ENCONTRADO"`

### 6. `config/AppConfig.java` — `@Configuration` + `@Bean`
- [x] `@Bean Clock clock()` → `Clock.systemDefaultZone()`
- [x] `@Bean PrecioFormatter` con `@Value` de `inventario.moneda.*`

### 7. `@Value` + ciclo de vida
- [x] `InventarioService` inyecta `@Value("${inventario.umbral-stock:5}")`
- [x] `CicloVidaDemo` con `@PostConstruct` / `@PreDestroy`
- [x] el `main` cierra con `ctx.close()` → dispara `@PreDestroy`

### 8. `config/PerfilConfig.java` — `@Profile`
- [x] `@Bean @Profile("dev")` y `@Bean @Profile("prod")` → `EntornoConfig`
- [x] Probá los 3 escenarios:
  - `mvn spring-boot:run` → umbral 5, sin bean de entorno
  - `mvn spring-boot:run "-Dspring-boot.run.profiles=dev"` → umbral 3, `entornoDev`
  - `mvn spring-boot:run "-Dspring-boot.run.profiles=prod"` → umbral 10, `entornoProd`

## Checklist de cierre

- [x] Arranco un `ApplicationContext` y saco beans con `getBean` sin mirar
- [x] Sé cuándo usar `@Bean` vs `@Component`/`@Service`/`@Repository`
- [x] Constructor injection siempre, y sé por qué no field injection
- [x] Devuelvo DTO, no entity
- [x] `@Value` + `@Profile` + `application-{perfil}.properties` funcionan
- [x] Terminé el mini-proyecto de inventario con DI
