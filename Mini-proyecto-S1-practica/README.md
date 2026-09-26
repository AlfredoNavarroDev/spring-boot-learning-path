# Práctica — Semana 1 (Java / Spring Boot path)

Ejercicio para completar a mano: fundamentos de Java puro (records, excepciones,
Stream API) sobre un mini-inventario. Todos los archivos tienen comentarios
`TODO` en vez de la solución. Si te trabás, mirá `../Mini-proyecto-S1` (mismo
tema ya resuelto).

Después de cada paso corré:
```
mvn compile
```
Cuando termines, corré `mvn test` para validar contra los tests reales.

## Orden sugerido

### 1. `Producto.java` — record inmutable con validación
**Propósito:** modelar el dato base del inventario, con la regla de negocio más
simple posible (stock no negativo) para practicar validación en constructor compacto.
**Archivos:** `Producto.java`, `StockNegativoException.java`
- [ ] Bloque de validación en el constructor compacto: lanza `StockNegativoException` si `stock < 0`

### 2. `StockNegativoException.java` / `ProductoDuplicadoException.java` — excepciones de dominio
**Propósito:** practicar excepciones personalizadas con mensaje descriptivo, en vez de devolver null/false.
**Archivos:** `StockNegativoException.java`, `ProductoDuplicadoException.java`
- [ ] `StockNegativoException` — mensaje que incluya el stock inválido
- [ ] `ProductoDuplicadoException` — mensaje que incluya el id duplicado

### 3. `Inventario.java` — Stream API sobre `List<Producto>`
**Propósito:** practicar filtrado, agrupamiento y agregación con Stream API.
**Archivos:** `Inventario.java`
- [ ] `agregar(Producto)` — rechaza duplicados por `id` con `anyMatch` + lanza `ProductoDuplicadoException`
- [ ] `buscarPorNombre(String)` — `filter` + `contains` case-insensitive
- [ ] `productosConStockBajo(int umbral)` — `filter` por `stock() < umbral`
- [ ] `agruparPorRangoDePrecio()` — `Collectors.groupingBy` en 4 rangos (`0-50`, `50-100`, `100-200`, `200+`)
- [ ] `valorTotalInventario()` — `mapToDouble` + `sum` de `precio * stock`

## Checklist de cierre

- [ ] `mvn test` pasa completo (`InventarioTest`, `ProductoTest`)
- [ ] Puedo escribir un record inmutable con validación en el constructor compacto sin mirar referencia
- [ ] Puedo resolver filtrado, agrupamiento y agregación con Stream API sin mirar referencia
