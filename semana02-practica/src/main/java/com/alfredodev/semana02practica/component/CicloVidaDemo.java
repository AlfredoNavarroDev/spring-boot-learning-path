package com.alfredodev.semana02practica.component;

import com.alfredodev.semana02practica.service.NotificacionService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

// Ciclo de vida de un bean singleton:
//   1. Constructor     -> Spring instancia y cablea dependencias.
//   2. @PostConstruct  -> corre DESPUÉS de inyectar todo (validar init, abrir recursos).
//   3. Uso normal.
//   4. @PreDestroy     -> corre al cerrar el ApplicationContext (liberar recursos).
// Equivalente a onModuleInit() / onModuleDestroy() de NestJS.
@Component
public class CicloVidaDemo {

    // TODO (Paso 7): inyectá NotificacionService por constructor (private final)
    //   y agregá 3 métodos con sus logs:
    //   1. Constructor que loguea  "[CICLO DE VIDA] 1) Constructor: dependencias cableadas"
    //   2. @PostConstruct que loguea "[CICLO DE VIDA] 2) @PostConstruct: bean listo"
    //   3. @PreDestroy que loguea   "[CICLO DE VIDA] 4) @PreDestroy: liberando recursos"
    private final NotificacionService notificacionService;

    public CicloVidaDemo(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
        System.out.println("[CICLO DE VIDA] 1) Constructor: dependencias cableadas");
    }

    @PostConstruct
    public void init() {
        System.out.println("[CICLO DE VIDA] 2) @PostConstruct: bean listo");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("[CICLO DE VIDA] 4) @PreDestroy: liberando recursos");
    }
}
