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
        return reglas.stream().filter(r -> r.condicion().test(tx)).map(RegistroRegla::nombre).toList();
    }

    public boolean esAprobada(Transaccion tx) {
        return evaluar(tx).isEmpty();
    }
}
