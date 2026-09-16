# Mini-proyecto S3 — Motor de reglas de detección de fraude

Mini-proyecto integrador de la **Semana 3** (Streams API y motor de reglas).
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
src/main/java/com/alfredodev/miniproyectos3/
├── modelo/       Transaccion (record), Cliente (record)
├── repositorio/  CatalogoClientes — Optional como valor de retorno
├── funcional/    ComposicionPredicados — Predicate.and/or/negate
├── motor/        RegistroRegla, MotorValidacion
├── reglas/       ReglasFraude — las 4 reglas reales
├── streams/      ReporteTransacciones — groupingBy + summingDouble
└── demo/         DemoMotor — main que corre 10 transacciones contra el motor
```

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
java -cp target/classes com.alfredodev.miniproyectos3.demo.DemoMotor
```

## Por qué es una pieza de portafolio

Es el mismo patrón que un sistema real de scoring/fraude: **reglas chicas y
componibles en vez de un `if` gigante**. En la Semana 4 (Spring MVC) este motor
se expone como endpoint real, y queda como librería reutilizable sin acoplarse a
ningún framework.
