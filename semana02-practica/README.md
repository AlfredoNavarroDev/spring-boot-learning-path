# Práctica — Semana 2 (Spring Core: IoC, DI, beans)

Primer contacto con el contenedor de Spring: `@Component`/`@Service`/`@Repository`,
inyección por constructor, `@Configuration` + `@Bean` para clases externas, `@Value`
para config externa, `@Profile` y el ciclo de vida de un bean singleton
(`@PostConstruct`/`@PreDestroy`). Dominio: mismo inventario de la Semana 1, ahora
manejado por Spring.

Después de cada paso corré:

```
mvn compile
```

Para ver el flujo completo (los 8 pasos corren en orden dentro de `main`):

```bash
mvn spring-boot:run
```

## Orden sugerido

### 1. `entity/Producto.java` y `dto/ProductoStockDto.java` — records planos (ya completo)
- [ ] `Producto(nombre, stock, precio)` — objeto de dominio, no es bean de Spring
- [ ] `ProductoStockDto(nombre, stock, estado)` — DTO con campo `estado` calculado
      por el service, no por la entity

### 2. `repository/ProductoRepository.java` — `@Repository`
- [ ] Lista en memoria con 5 productos (`Laptop HP`, `Monitor Dell`, `Teclado Mecánico`,
      `Mouse Inalámbrico`, `Hub USB-C`)
- [ ] `buscarPorNombre(String)` — stream + `equalsIgnoreCase`, `null` si no existe
- [ ] `listarTodos()`

### 3. `service/NotificacionService.java` y `service/ProductoService.java` — `@Service` + inyección por constructor
- [ ] `NotificacionService.enviarAlerta(String)` — imprime `[NOTIFICACIÓN]`
- [ ] `ProductoService` inyecta `ProductoRepository` por constructor (`private final`,
      sin `@Autowired`) y delega `buscarPorNombre`/`listarTodos`

### 4. `component/StockValidator.java` — `@Component` genérico
- [ ] `estaBajo(Producto, int umbral)` → `stock() <= umbral`
- [ ] `noExiste(Producto)` → `producto == null`

### 5. `service/InventarioService.java` — orquestador con 3 dependencias inyectadas
- [ ] Constructor cablea `ProductoService`, `NotificacionService`, `StockValidator`
      + `@Value("${inventario.umbral-stock:5}")`
- [ ] `revisarStock(nombre, umbral)` — busca, valida y devuelve `ProductoStockDto`
      con estado `NO ENCONTRADO`/`ALERTA`/`OK`, disparando alerta en los dos primeros casos
- [ ] Sobrecarga `revisarStock(nombre)` — usa el umbral por defecto inyectado

### 6. `config/AppConfig.java` y `util/PrecioFormatter.java` — `@Configuration` + `@Bean`
- [ ] `PrecioFormatter` es un POJO sin anotación (no se le puede poner `@Component`
      porque necesita un `Locale` como parámetro)
- [ ] `@Bean clock()` → `Clock.systemDefaultZone()` (clase del JDK, tampoco anotable)
- [ ] `@Bean precioFormatter(...)` — recibe `inventario.moneda.language`/`.country`
      por `@Value` y construye el `Locale`

### 7. `component/CicloVidaDemo.java` — ciclo de vida del bean singleton
- [ ] Constructor inyecta `NotificacionService` y loguea `1) Constructor`
- [ ] `@PostConstruct init()` loguea `2) @PostConstruct`
- [ ] `@PreDestroy destroy()` loguea `4) @PreDestroy` (se dispara con `ctx.close()` en `Main`)

### 8. `config/PerfilConfig.java` + `config/EntornoConfig.java` — `@Profile`
- [ ] `EntornoConfig` — record value object (no es bean, lo crea `PerfilConfig`)
- [ ] `@Bean @Profile("dev") entornoDev()` y `@Bean @Profile("prod") entornoProd()` —
      solo se crea el que coincide con el perfil activo
- [ ] `application-dev.properties` (`umbral-stock=3`) y `application-prod.properties`
      (`umbral-stock=10`) sobrescriben `application.properties` (`umbral-stock=5`)

## Checklist de cierre

- [ ] Entiendo la diferencia entre `@Component`, `@Service` y `@Repository` (misma
      mecánica, distinta semántica)
- [ ] Sé cuándo usar `@Bean` en vez de `@Component` (clases externas o que necesitan
      parámetros de construcción)
- [ ] Puedo inyectar configuración externa con `@Value` y activar un perfil con `@Profile`
- [ ] Entiendo el ciclo de vida de un bean singleton: constructor → `@PostConstruct`
      → uso → `@PreDestroy`
