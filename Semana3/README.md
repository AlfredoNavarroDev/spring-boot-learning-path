# Semana 3 — Motor de reglas de detección de fraude

Proyecto integrador de la **Semana 3** (Streams API y motor de reglas).
**Java puro, sin Spring.** Dominio: detección de fraude sobre `Transaccion`.

## Qué demuestra

Cada concepto de la semana mapeado a su equivalente en NestJS/TS:

| Concepto | Java acá | En NestJS/TS |
|---|---|---|
| Interfaces funcionales | `Predicate<T>`, composición `.and/.or/.negate` | funciones que devuelven `boolean` + `&&`/`\|\|` |
| Manejo de nulos | `Optional<T>` como valor de retorno | `T \| null` + `?.` / `??` |
| Procesamiento de colecciones | Stream API (`filter`, `map`, `collect(groupingBy/summingDouble)`) | `.filter().map().reduce()` de Array |
| Reglas de negocio componibles | `MotorValidacion` + `RegistroRegla` | lista de `{ nombre, check }` filtrada |

## Estructura

```
src/main/java/com/alfredodev/semana03/
├── modelo/       Transaccion (record), Cliente (record)
├── repositorio/  CatalogoClientes — Optional como valor de retorno
├── funcional/    ComposicionPredicados (Predicate), InterfacesFuncionales (Function/Supplier/Consumer)
├── motor/        RegistroRegla, MotorValidacion
├── reglas/       ReglasFraude — las 4 reglas reales
├── streams/      ReporteTransacciones — groupingBy + summingDouble
└── demo/         DemoMotor — main que corre 10 transacciones contra el motor
```

## Cómo entender el proyecto (orden recomendado)

Lee en este orden, de lo más básico a lo que lo orquesta todo:

1. **`modelo/`** — `Transaccion.java` y `Cliente.java` — *el dato*.
   `Transaccion` es el objeto de dominio sobre el que corren las reglas
   (monto, país, hora, cliente, canal). `Cliente` es el registro que se busca
   en el catálogo. Ambos son `record`: inmutables, solo datos, sin lógica.

2. **`reglas/ReglasFraude.java`** — *las 4 reglas de negocio*.
   Cada regla es un `RegistroRegla`: un nombre legible (`"MONTO_SOBRE_LIMITE"`)
   + un `Predicate<Transaccion>` que decide si la transacción la dispara. Acá
   vive TODO el criterio de fraude; es lo primero que cambias si la política
   de negocio cambia.

3. **`motor/RegistroRegla.java` → `motor/MotorValidacion.java`** — *el motor*.
   `RegistroRegla` es la pieza mínima (nombre + condición). `MotorValidacion`
   recibe la lista de reglas y, para cada transacción, devuelve los motivos
   que se dispararon. No sabe nada de fraude: solo ejecuta reglas ya
   construidas (S de SOLID). Lista vacía = aprobada.

4. **`repositorio/CatalogoClientes.java`** — *Optional*.
   `buscar(nombre)` devuelve `Optional<Cliente>` en vez de `null`. Regla de
   oro: Optional solo como valor de retorno, nunca como campo.

5. **`funcional/`** — `ComposicionPredicados.java` e `InterfacesFuncionales.java` —
   *interfaces funcionales y composición*. `ComposicionPredicados` arma 5
   predicados simples (uno por campo de `Transaccion`) y los combina de 3
   formas con `.and()/.or()/.negate()`. `InterfacesFuncionales` muestra
   `Function`, `Supplier`, `Consumer` y `BiFunction` con su equivalente en TS.

6. **`streams/ReporteTransacciones.java`** — *Stream API*.
   Agregaciones sobre la lista de transacciones: total por cliente
   (`groupingBy` + `summingDouble`), filtro por monto, suma total.

7. **`demo/DemoMotor.java`** — *punto de entrada*.
   Arma el catálogo, el historial de 10 transacciones y el motor, y muestra en
   consola los tres conceptos: motor de validación, Stream API y Optional.

> Tip: si quieres ver el resultado antes que el mecanismo, lee primero
> `demo/DemoMotor.java` y después vuelve al paso 1.

## Las 4 reglas

1. `MONTO_SOBRE_LIMITE` — monto > límite diario
2. `PAIS_BLOQUEADO` — país en lista negra
3. `VELOCIDAD_EXCESIVA` — más de 3 transacciones en 1 minuto del mismo cliente
4. `HORARIO_INUSUAL` — madrugada (00:00–05:00)

El motor devuelve la lista de motivos de rechazo; **lista vacía = aprobada**.

## Cómo correr

```bash
# tests
mvn test

# demo
mvn -q compile
java -cp target/classes com.alfredodev.semana03.demo.DemoMotor
```

## Por qué es una pieza de portafolio

Es el mismo patrón que un sistema real de scoring/fraude: **reglas chicas y
componibles en vez de un `if` gigante**. En la Semana 4 (Spring MVC) este motor
se expone como endpoint real, y queda como librería reutilizable sin acoplarse a
ningún framework.
