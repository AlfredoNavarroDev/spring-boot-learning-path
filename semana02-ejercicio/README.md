# Práctica — Semana 2 (Spring Core: IoC, DI, beans)

Ejercicio para completar a mano: `@Component`/`@Service`/`@Repository`, inyección
por constructor, `@Configuration` + `@Bean` para clases externas, `@Value` para
config externa, `@Profile` y el ciclo de vida de un bean singleton
(`@PostConstruct`/`@PreDestroy`). Todos los archivos tienen comentarios `TODO`
en vez de la solución. Si te trabás, mirá `../semana02-practica` (mismo tema ya
resuelto).

Después de cada paso corré:

```
mvn compile
```

Cuando termines, corré `mvn spring-boot:run` para ver el flujo completo.

## Orden sugerido

### 1. `entity/Producto.java` y `dto/ProductoStockDto.java` — records planos (ya completo)
**Propósito:** el dato base, sin lógica de Spring — para no distraerte del objetivo real de la semana (DI).
**Archivos:** `entity/Producto.java`, `dto/ProductoStockDto.java`, `config/EntornoConfig.java`
- [ ] (nada que hacer acá, ya completo)

### 2. `repository/ProductoRepository.java` — `@Repository`
**Propósito:** practicar el estereotipo de capa de datos y devolver una colección poblada.
**Archivos:** `repository/ProductoRepository.java`
- [ ] Lista en memoria con 5 productos (`Laptop HP` 3/3200, `Monitor Dell` 12/890, `Teclado Mecánico` 2/250, `Mouse Inalámbrico` 0/45, `Hub USB-C` 7/120)
- [ ] `buscarPorNombre(String)` — stream + `equalsIgnoreCase`, `null` si no existe
- [ ] `listarTodos()`

### 3. `service/NotificacionService.java` y `service/ProductoService.java` — `@Service` + inyección por constructor
**Propósito:** practicar inyección por constructor (sin `@Autowired`) y delegación a la capa de datos.
**Archivos:** `service/NotificacionService.java`, `service/ProductoService.java`
- [ ] `NotificacionService.enviarAlerta(String)` — imprime `[NOTIFICACIÓN]`
- [ ] `ProductoService.buscarPorNombre`/`listarTodos` — delegan al repository (el constructor ya está armado)

### 4. `component/StockValidator.java` — `@Component` genérico
**Propósito:** practicar cuándo usar `@Component` en vez de `@Service`/`@Repository`.
**Archivos:** `component/StockValidator.java`
- [ ] `estaBajo(Producto, int umbral)` → `stock() <= umbral`
- [ ] `noExiste(Producto)` → `producto == null`

### 5. `service/InventarioService.java` — orquestador con 3 dependencias inyectadas
**Propósito:** practicar inyección de múltiples dependencias + `@Value` en el mismo constructor.
**Archivos:** `service/InventarioService.java`
- [ ] `revisarStock(nombre, umbral)` — busca, valida y devuelve `ProductoStockDto` con estado `NO ENCONTRADO`/`ALERTA`/`OK`, disparando alerta en los dos primeros casos
- [ ] Sobrecarga `revisarStock(nombre)` — usa el umbral por defecto inyectado

### 6. `config/AppConfig.java` y `util/PrecioFormatter.java` — `@Configuration` + `@Bean`
**Propósito:** practicar cuándo usar `@Bean` en vez de `@Component` (clases externas o que necesitan parámetros de construcción).
**Archivos:** `config/AppConfig.java`, `util/PrecioFormatter.java`
- [ ] `@Bean clock()` → `Clock.systemDefaultZone()`
- [ ] `@Bean precioFormatter(...)` — recibe `inventario.moneda.language`/`.country` por `@Value` y construye el `Locale`
- [ ] `PrecioFormatter.formatear(double)` — formato moneda con el `Locale` recibido

### 7. `component/CicloVidaDemo.java` — ciclo de vida del bean singleton
**Propósito:** practicar el orden constructor → `@PostConstruct` → uso → `@PreDestroy`.
**Archivos:** `component/CicloVidaDemo.java`
- [ ] Loguear en el constructor, en `@PostConstruct init()` y en `@PreDestroy destroy()` (la inyección del constructor ya está armada, solo falta el log)

### 8. `config/PerfilConfig.java` — `@Profile`
**Propósito:** practicar beans condicionados al perfil activo.
**Archivos:** `config/PerfilConfig.java`
- [ ] `@Bean @Profile("dev") entornoDev()` → `new EntornoConfig("dev", "umbral estricto + datos de prueba")`
- [ ] `@Bean @Profile("prod") entornoProd()` → `new EntornoConfig("prod", "umbral relajado + datos reales")`

## Checklist de cierre

- [ ] `mvn compile` compila limpio
- [ ] Entiendo la diferencia entre `@Component`, `@Service` y `@Repository`
- [ ] Sé cuándo usar `@Bean` en vez de `@Component`
- [ ] Puedo inyectar configuración externa con `@Value` y activar un perfil con `@Profile`
- [ ] Entiendo el ciclo de vida de un bean singleton: constructor → `@PostConstruct` → uso → `@PreDestroy`
