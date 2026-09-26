package com.alfredodev.miniproyectos6.motor;

import java.util.List;

import com.alfredodev.miniproyectos6.modelo.Transaccion;

/** Portado de la Semana 3: ejecuta reglas, no sabe nada del dominio de fraude. */
public class MotorValidacion {

    private final List<RegistroRegla> reglas;

    public MotorValidacion(List<RegistroRegla> reglas) {
        this.reglas = List.copyOf(reglas);
    }

    public List<String> evaluar(Transaccion tx) {
        // TODO (Paso 2): recorré `reglas` con un stream, quedate solo con las
        // que fallan (r.condicion().test(tx) == true, o sea "dispara alerta")
        // y mapeá a su nombre (RegistroRegla::nombre) con .toList().
        throw new UnsupportedOperationException("TODO Paso 2: MotorValidacion.evaluar");
    }

    public boolean esAprobada(Transaccion tx) {
        return evaluar(tx).isEmpty();
    }
}
