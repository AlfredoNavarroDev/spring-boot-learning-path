package com.alfredodev.miniproyectos9.security;

import java.io.IOException;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        // TODO Paso 2:
        // 1. leer el header "Authorization"; si no viene o no empieza con "Bearer ", seguir
        //    la cadena de filtros sin autenticar (filterChain.doFilter(request, response); return;)
        // 2. extraer el token (header.substring(7)) y, dentro de un try/catch, sacar el email
        //    con jwtService.extraerEmail(token)
        // 3. si el email no es null, no hay autenticacion previa en el SecurityContext y
        //    jwtService.esValido(token, email), armar las GrantedAuthority con el rol del claim
        //    (jwtService.extraerRol(token)) como new SimpleGrantedAuthority("ROLE_" + rol) y
        //    setear un UsernamePasswordAuthenticationToken en el SecurityContextHolder
        // 4. si algo falla, SecurityContextHolder.clearContext() en el catch
        // Sea cual sea el resultado, la cadena de filtros SIEMPRE tiene que continuar:
        filterChain.doFilter(request, response);
    }
}
