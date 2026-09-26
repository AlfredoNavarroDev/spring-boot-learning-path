# Práctica — Semana 3 (Streams API y motor de reglas)

Java puro (sin Spring). Completá a mano el motor de reglas de detección de
fraude: cada pieza marcada con `// TODO` tiene el cuerpo reemplazado por un
`throw new UnsupportedOperationException(...)` — el proyecto compila tal cual
está, pero los tests fallan hasta que implementes cada método. Los tests en
`src/test/java` son el spec ejecutable: no los toques, hacelos pasar.

```bash
mvn compile
```

Debe compilar limpio desde el principio (los `throw` satisfacen el tipo de
retorno). Corré `mvn test` para ver cuántos TODOs te faltan.

## Orden sugerido

### 1. modelo/ — Transaccion y Cliente

**Propósito:** familiarizarte con los `record` planos que sirven de objeto de
dominio (equivalente a un DTO readonly en TS). No requieren trabajo: ya están
completos.

**Archivos:** `modelo/Transaccion.java`, `modelo/Cliente.java`

- [ ] Leer ambos records y entender los campos (sin editar nada)

### 2. repositorio/CatalogoClientes.java — Optional

**Propósito:** aplicar la regla de oro de `Optional`: se usa solo como valor
de retorno (nunca como campo), en lugar de `null` + `if (x != null)`.

**Archivos:** `repositorio/CatalogoClientes.java`

- [ ] Implementar `buscar(String nombre)` devolviendo `Optional.ofNullable(...)`
      sobre `porNombre.get(nombre)` (o `Optional.empty()` en el caso vacío)

### 3. funcional/ComposicionPredicados.java — composición de Predicate

**Propósito:** combinar `Predicate<Transaccion>` con `.and()/.or()/.negate()`,
el equivalente a combinar booleanos con `&&`/`||`/`!` pero como objetos
reusables.

**Archivos:** `funcional/ComposicionPredicados.java`

- [ ] Implementar `alertaManual(CatalogoClientes catalogo)`: "monto alto O país
      bloqueado, PERO no aplica a VIPs" — `(montoAlto.or(paisBloqueado)).and(clienteVip.negate())`

### 4. motor/RegistroRegla.java y motor/MotorValidacion.java — el motor

**Propósito:** `RegistroRegla` envuelve nombre + `Predicate` (ya está
completo, es un record trivial). `MotorValidacion` corre la lista de reglas
contra una transacción y devuelve los motivos de rechazo.

**Archivos:** `motor/RegistroRegla.java`, `motor/MotorValidacion.java`

- [ ] Leer `RegistroRegla` (sin editar, ya completo)
- [ ] Implementar `MotorValidacion.evaluar(Transaccion tx)`: filtrar las
      reglas cuya condición se cumple y mapear a su nombre con `.toList()`

### 5. reglas/ReglasFraude.java — las 4 reglas de negocio

**Propósito:** cada regla real de fraude como dato (`RegistroRegla` = nombre +
`Predicate`), no como `if` disperso.

**Archivos:** `reglas/ReglasFraude.java`

- [ ] `montoSobreLimite()`: monto > límite diario (`LIMITE_DIARIO`)
- [ ] `paisBloqueado()`: país en lista negra (`PAISES_BLOQUEADOS`)
- [ ] `horarioInusual()`: madrugada, 00:00–05:00
- [ ] `velocityCheck(historial)`: más de 3 transacciones en 1 minuto del
      mismo cliente
- [ ] `enVentanaDeUnMinuto(a, b)`: diferencia absoluta en segundos <= 60

### 6. streams/ReporteTransacciones.java — agregación con Stream API

**Propósito:** operaciones de negocio con streams (`groupingBy`,
`summingDouble`, `mapToDouble`), lazy y de un solo uso (a diferencia de los
arrays de JS).

**Archivos:** `streams/ReporteTransacciones.java`

- [ ] `totalPorCliente(transacciones)`: `groupingBy(Transaccion::cliente,
      summingDouble(Transaccion::monto))`
- [ ] `sobreMonto(transacciones, montoMinimo)`: filtrar por `monto() >
      montoMinimo`
- [ ] `montoTotal(transacciones)`: `mapToDouble(Transaccion::monto).sum()`

No toques `demo/DemoMotor.java` — es la demo lista para correr y verificar tu
propia implementación una vez que completes los pasos de arriba.

## Checklist de cierre

- [ ] `Optional` se usa solo como valor de retorno, nunca `null` ni como campo
- [ ] Las reglas compuestas se arman combinando `Predicate` con `.and()/.or()/.negate()`, no con `if` anidados
- [ ] Las reglas de fraude son datos (`RegistroRegla`: nombre + `Predicate`) que el motor solo ejecuta, no lógica hardcodeada en `MotorValidacion`
- [ ] Los reportes usan `groupingBy`/`summingDouble` en vez de loops manuales con acumuladores

¿Te trabaste? La versión resuelta está en [`../Mini-proyecto-S3`](../Mini-proyecto-S3) — mismo código, sin TODOs.
