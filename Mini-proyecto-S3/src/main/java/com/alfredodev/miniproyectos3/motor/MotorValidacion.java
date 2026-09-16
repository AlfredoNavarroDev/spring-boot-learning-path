package com.alfredodev.miniproyectos3.motor;

import com.alfredodev.miniproyectos3.modelo.Transaccion;

import java.util.List;

/**
 * Motor de reglas: evalúa una lista de {@link RegistroRegla} y devuelve los
 * motivos de rechazo. Lista vacía = transacción aprobada.
 *
 * No sabe nada del dominio de fraude: recibe reglas ya construidas y solo las
 * ejecuta. Es la S de SOLID — una sola razón para cambiar.
 */
public class MotorValidacion {

    private final List<RegistroRegla> reglas;

    public MotorValidacion(List<RegistroRegla> reglas) {
        this.reglas = List.copyOf(reglas);
    }

    public MotorValidacion(RegistroRegla... reglas) {
        this(List.of(reglas));
    }

    public List<String> evaluar(Transaccion tx) {
        return reglas.stream()
                .filter(r -> r.condicion().test(tx))
                .map(RegistroRegla::nombre)
                .toList();
    }

    public boolean esAprobada(Transaccion tx) {
        return evaluar(tx).isEmpty();
    }
}
