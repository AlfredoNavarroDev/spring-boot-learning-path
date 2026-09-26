package com.alfredodev.miniproyectos7.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // TODO (Paso 3): armar la cadena de filtros:
        // 1. http.csrf(csrf -> csrf.disable()) - API stateless con JWT, no usa cookies de sesion
        // 2. .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // 3. .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**", "/h2-console/**").permitAll()
        //    .anyRequest().authenticated())
        // 4. .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) - el
        //    filtro JWT corre antes del filtro estandar de user/password
        // 5. devolver http.build()
        throw new UnsupportedOperationException("TODO");
    }
}
