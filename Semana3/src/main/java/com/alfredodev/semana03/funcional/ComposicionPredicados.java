package com.alfredodev.semana03.funcional;

import com.alfredodev.semana03.modelo.Cliente;
import com.alfredodev.semana03.modelo.Transaccion;
import com.alfredodev.semana03.reglas.ReglasFraude;
import com.alfredodev.semana03.repositorio.CatalogoClientes;

import java.time.LocalTime;
import java.util.function.Predicate;

/**
 * Composición de predicados: mismo concepto que combinar booleanos con
 * {@code &&}/{@code ||}/{@code !} en TS, pero como objetos reusables.
 *
 * Arriba: 5 predicados simples (uno por campo de {@link Transaccion}).
 * Abajo: 3 combinaciones distintas con {@code and}/{@code or}/{@code negate}.
 */
public final class ComposicionPredicados {

    private ComposicionPredicados() {
    }

    // ---- 5 predicados simples (uno por campo de Transaccion) ----

    /** Monto por encima del umbral de demostración. */
    public static Predicate<Transaccion> montoAlto() {
        return tx -> tx.monto() > 5_000;
    }

    /** País en la lista negra (reutiliza la constante de {@link ReglasFraude}). */
    public static Predicate<Transaccion> paisBloqueado() {
        return tx -> ReglasFraude.PAISES_BLOQUEADOS.contains(tx.pais());
    }

    /** Hora en la madrugada (00:00–05:00). */
    public static Predicate<Transaccion> horarioInusual() {
        return tx -> {
            LocalTime hora = tx.hora().toLocalTime();
            return !hora.isBefore(ReglasFraude.INICIO_MADRUGADA)
                    && hora.isBefore(ReglasFraude.FIN_MADRUGADA);
        };
    }

    /** Cliente con el nombre dado. */
    public static Predicate<Transaccion> clienteEs(String nombre) {
        return tx -> tx.cliente().equals(nombre);
    }

    /** Canal de origen con el valor dado. */
    public static Predicate<Transaccion> canalEs(String canal) {
        return tx -> tx.canal().equals(canal);
    }

    // ---- 3 combinaciones ----

    /** 1. {@code and}: monto alto Y canal web. */
    public static Predicate<Transaccion> montoAltoEnWeb() {
        return montoAlto().and(canalEs("web"));
    }

    /** 2. {@code or}: país bloqueado O horario inusual. */
    public static Predicate<Transaccion> riesgoOperativo() {
        return paisBloqueado().or(horarioInusual());
    }

    /** 3. {@code or + negate}: monto alto O país bloqueado, PERO no para VIPs. */
    public static Predicate<Transaccion> alertaManual(CatalogoClientes catalogo) {
        Predicate<Transaccion> clienteVip = tx -> catalogo.buscar(tx.cliente())
                .map(Cliente::esVip)
                .orElse(false);

        return montoAlto().or(paisBloqueado()).and(clienteVip.negate());
    }
}
