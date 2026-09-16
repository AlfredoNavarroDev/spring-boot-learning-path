package com.alfredodev.semana02practica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// @Profile condiciona la creación de un bean al perfil activo.
// Con "dev" solo se crea entornoDev(); con "prod", solo entornoProd().
// Sin ningún profile activo, no se crea ninguno de los dos.
@Configuration
public class PerfilConfig {

    // TODO (Paso 8): agregá dos @Bean, cada uno con su @Profile:
    //   - @Bean @Profile("dev")  EntornoConfig entornoDev()  -> new EntornoConfig("dev",  "umbral estricto + datos de prueba")
    //   - @Bean @Profile("prod") EntornoConfig entornoProd() -> new EntornoConfig("prod", "umbral relajado + datos reales")
    @Bean
    @Profile("dev")
    public EntornoConfig entornoDev() {
        return new EntornoConfig("dev",  "umbral estricto + datos de prueba");
    }

    @Bean
    @Profile("prod")
    public EntornoConfig entornoProd() {
        return new EntornoConfig("prod", "umbral relajado + datos reales");
    }
}
