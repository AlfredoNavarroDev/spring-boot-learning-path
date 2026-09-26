# Práctica — Semana 3 (Streams API y motor de reglas)

Motor de reglas de detección de fraude, **Java puro, sin Spring**. Dominio:
transacciones que pasan por reglas componibles en vez de un `if` gigante — el mismo
patrón que un sistema real de scoring/fraude.

Después de cada paso corré:

```
mvn compile
```

Para correr la demo:

```bash
mvn -q compile
java -cp target/classes com.alfredodev.miniproyectos3.demo.DemoMotor
```

## Orden sugerido

### 1. `modelo/Transaccion.java` y `modelo/Cliente.java` — records de dominio (ya completo)
- [ ] `Transaccion` y `Cliente` como records inmutables

### 2. `repositorio/CatalogoClientes.java` — `Optional<T>` como valor de retorno
- [ ] Búsqueda de cliente que devuelve `Optional<Cliente>` en vez de `null` —
      equivalente a `T | null` + `?.`/`??` en TS

### 3. `funcional/ComposicionPredicados.java` — interfaces funcionales
- [ ] Composición de `Predicate<T>` con `.and()/.or()/.negate()` — equivalente a
      combinar funciones `boolean` con `&&`/`||`

### 4. `motor/RegistroRegla.java` y `motor/MotorValidacion.java` — reglas de negocio componibles
- [ ] `RegistroRegla` — envuelve nombre + `Predicate<Transaccion>`
- [ ] `MotorValidacion` — corre todas las reglas y devuelve la lista de motivos de
      rechazo (lista vacía = aprobada)

### 5. `reglas/ReglasFraude.java` — las 4 reglas reales
- [ ] `MONTO_SOBRE_LIMITE` — monto > límite diario
- [ ] `PAIS_BLOQUEADO` — país en lista negra
- [ ] `VELOCIDAD_EXCESIVA` — más de 3 transacciones en 1 minuto del mismo cliente
- [ ] `HORARIO_INUSUAL` — madrugada (00:00–05:00)

### 6. `streams/ReporteTransacciones.java` — Stream API de agregación
- [ ] `groupingBy` + `summingDouble` sobre transacciones — equivalente a
      `.filter().map().reduce()` de Array en TS

### 7. `demo/DemoMotor.java` — demo end-to-end
- [ ] `main` que corre 10 transacciones contra el motor completo

### 8. Tests (`MotorValidacionTest`, `ReglasFraudeTest`, `CatalogoClientesTest`, `ReporteTransaccionesTest`)
- [ ] Cubren motor, las 4 reglas, el catálogo con `Optional` y el reporte agregado

## Checklist de cierre

- [ ] Entiendo `Optional<T>` como alternativa explícita a devolver `null`
- [ ] Puedo componer `Predicate<T>` en vez de escribir condicionales anidados
- [ ] Puedo modelar reglas de negocio como datos (lista de `{nombre, check}`) en vez
      de un método gigante
- [ ] Domino `groupingBy`/`summingDouble` para reportes agregados
