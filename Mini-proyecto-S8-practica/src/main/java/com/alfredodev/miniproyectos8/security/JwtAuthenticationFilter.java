package com.alfredodev.miniproyectos8.security;

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

/**
 * A diferencia de la Semana 7, las authorities se arman directo desde el claim "rol"
 * del JWT, sin volver a consultar UsuarioRepository en cada request - el token ya es
 * la fuente de verdad del rol dentro de su ventana de expiracion (equivalente a leer
 * req.user.roles ya decodificado por la JwtStrategy de Passport).
 */
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
        // 1. Leer el header "Authorization"; si no viene o no empieza con "Bearer ", saltear
        //    directo al filterChain.doFilter(request, response) de abajo (no hay token que procesar).
        // 2. Sacar el token (header.substring(7)) y, dentro de un try/catch, si hay email
        //    (jwtService.extraerEmail(token)), no hay autenticacion previa en el
        //    SecurityContextHolder, y el token es valido (jwtService.esValido(token, email)):
        //    - PISTA CLAVE: leer el rol DIRECTO del claim del JWT con jwtService.extraerRol(token) -
        //      NO volver a consultar UsuarioRepository aca. El token ya trae el rol adentro.
        //    - Armar List.of(new SimpleGrantedAuthority("ROLE_" + rol))
        //    - Crear un UsernamePasswordAuthenticationToken(email, null, authorities),
        //      setearle los details con new WebAuthenticationDetailsSource().buildDetails(request)
        //      y guardarlo en SecurityContextHolder.getContext().setAuthentication(authToken)
        //    - Si algo falla, SecurityContextHolder.clearContext() en el catch.
        // 3. Sea cual sea el camino, la cadena SIEMPRE sigue (linea de abajo, ya provista).

        filterChain.doFilter(request, response);
    }
}
