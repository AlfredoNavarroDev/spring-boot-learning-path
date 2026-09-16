package com.alfredodev.semana02practica.service;

import org.springframework.stereotype.Service;

// Envía alertas — por ahora solo imprime en consola.
// (En una versión real esto mandaría email/push; esa variante es para
//  aplicar Open/Closed en otra semana, no ahora.)
@Service
public class NotificacionService {

    // TODO (Paso 3): enviarAlerta(String mensaje)
    //   -> imprime "[NOTIFICACIÓN] " + mensaje
    public void enviarAlerta(String mensaje) {
        System.out.println("[NOTIFICACIÓN]: " + mensaje);
    }
}
