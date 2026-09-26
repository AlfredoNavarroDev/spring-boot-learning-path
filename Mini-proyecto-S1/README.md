# Práctica — Semana 1 (Java / Spring Boot path)

Fundamentos de Java puro sobre un mini-inventario: records inmutables, excepciones
personalizadas y Stream API. Mismo dominio que el resto del learning path
(productos + inventario), sin Spring todavía — eso arranca en la Semana 2.

Después de cada paso corré:

```
mvn compile
```

Para ver la demo completa:

```bash
mvn compile
java -cp target/classes org.example.Main
```

## Orden sugerido

### 1. `Producto.java` — record inmutable con validación
- [ ] Record `Producto(String id, String nombre, double precio, int stock)`
- [ ] Bloque de validación en el constructor compacto: lanza `StockNegativoException`
      si `stock < 0`

### 2. `StockNegativoException.java` / `ProductoDuplicadoException.java` — excepciones de dominio
- [ ] `StockNegativoException` — `RuntimeException` con mensaje que incluye el stock inválido
- [ ] `ProductoDuplicadoException` — `RuntimeException` con mensaje que incluye el id duplicado

### 3. `Inventario.java` — Stream API sobre `List<Producto>`
- [ ] `agregar(Producto)` — rechaza duplicados por `id` con `anyMatch` + lanza `ProductoDuplicadoException`
- [ ] `buscarPorNombre(String)` — `filter` + `contains` case-insensitive
- [ ] `productosConStockBajo(int umbral)` — `filter` por `stock() < umbral`
- [ ] `agruparPorRangoDePrecio()` — `Collectors.groupingBy` en 4 rangos (`0-50`, `50-100`, `100-200`, `200+`)
- [ ] `valorTotalInventario()` — `mapToDouble` + `sum` de `precio * stock`

### 4. `Main.java` — demo end-to-end
- [ ] Carga 4 productos, corre búsqueda, valor total, stock bajo y agrupación por rango
- [ ] Dispara y captura `ProductoDuplicadoException` y `StockNegativoException` a propósito,
      para mostrar el mensaje de error esperado

### 5. Tests (`InventarioTest`, `ProductoTest`)
- [ ] `ProductoTest` — creación válida + rechazo de stock negativo
- [ ] `InventarioTest` — alta, duplicado, búsqueda parcial, stock bajo, agrupación por rango

## Checklist de cierre

- [ ] Puedo escribir un record inmutable con validación en el constructor compacto
- [ ] Entiendo cuándo una regla de negocio lanza una excepción personalizada en vez de
      devolver null/false
- [ ] Puedo resolver filtrado, agrupamiento y agregación con Stream API sin mirar referencia
- [ ] Los tests cubren el camino feliz y los casos de error de `Inventario`
