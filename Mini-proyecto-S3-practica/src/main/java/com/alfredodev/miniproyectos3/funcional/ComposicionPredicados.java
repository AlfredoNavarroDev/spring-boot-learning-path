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
        // TODO (Paso 3): construí y devolvé un único Predicate<Transaccion>
        // combinando tres predicados con .and()/.or()/.negate():
        //   1. montoAlto      = ReglasFraude.montoSobreLimite().condicion()
        //   2. paisBloqueado  = ReglasFraude.paisBloqueado().condicion()
        //   3. clienteVip     = catalogo.buscar(tx.cliente()).map(Cliente::esVip).orElse(false)
        // Regla de negocio: "monto alto O país bloqueado, PERO no aplica a VIPs"
        // => (montoAlto.or(paisBloqueado)).and(clienteVip.negate())
        throw new UnsupportedOperationException("TODO Paso 3: ComposicionPredicados.alertaManual");
    }
}
