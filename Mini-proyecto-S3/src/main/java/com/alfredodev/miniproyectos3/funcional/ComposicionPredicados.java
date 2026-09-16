package com.alfredodev.miniproyectos3.funcional;

import com.alfredodev.miniproyectos3.modelo.Cliente;
import com.alfredodev.miniproyectos3.modelo.Transaccion;
import com.alfredodev.miniproyectos3.reglas.ReglasFraude;
import com.alfredodev.miniproyectos3.repositorio.CatalogoClientes;

import java.util.function.Predicate;

/**
 * Composición de predicados: mismo concepto que combinar booleanos con
 * {@code &&}/{@code ||}/{@code !} en TS, pero como objetos reusables.
 */
public final class ComposicionPredicados {

    private ComposicionPredicados() {
    }

    /**
     * Regla compuesta: "monto alto O país bloqueado, PERO no aplica a VIPs".
     */
    public static Predicate<Transaccion> alertaManual(CatalogoClientes catalogo) {
        Predicate<Transaccion> montoAlto = ReglasFraude.montoSobreLimite().condicion();
        Predicate<Transaccion> paisBloqueado = ReglasFraude.paisBloqueado().condicion();
        Predicate<Transaccion> clienteVip = tx -> catalogo.buscar(tx.cliente())
                .map(Cliente::esVip)
                .orElse(false);

        return montoAlto.or(paisBloqueado).and(clienteVip.negate());
    }
}
