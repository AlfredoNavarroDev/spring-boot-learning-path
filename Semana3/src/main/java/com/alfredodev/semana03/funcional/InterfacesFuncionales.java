package com.alfredodev.semana03.funcional;

import com.alfredodev.semana03.modelo.Cliente;
import com.alfredodev.semana03.modelo.Transaccion;

import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Las otras interfaces funcionales de {@code java.util.function}, con su
 * equivalente en TS. {@code Predicate} ya está cubierto en
 * {@link ComposicionPredicados} y en las reglas de fraude; acá van las que
 * transforman, producen y consumen valores.
 */
public final class InterfacesFuncionales {

    private InterfacesFuncionales() {
    }

    /** {@code Function<T,R>}: recibe {@code T} y devuelve {@code R} — una función tipada de TS. */
    public static final Function<Transaccion, String> categoriaPorMonto =
            tx -> tx.monto() > 5_000 ? "ALTO" : "BAJO";

    /** {@code BiFunction<T,U,R>}: recibe dos argumentos y devuelve uno. */
    public static final BiFunction<Transaccion, Double, Double> comision =
            (tx, tasa) -> tx.monto() * tasa;

    /** {@code Supplier<T>}: no recibe nada y produce un valor — evaluación lazy. */
    public static final Supplier<Transaccion> transaccionDePrueba =
            () -> new Transaccion(1_000, "PE", LocalDateTime.now(), "Ana", "web");

    /** {@code Consumer<T>}: recibe {@code T} y no devuelve nada — efecto lateral, como un callback. */
    public static final Consumer<Cliente> imprimirResumen =
            c -> System.out.printf("Cliente %s (VIP=%s)%n", c.nombre(), c.esVip());
}
