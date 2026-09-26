package com.alfredodev.miniproyectos7.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Equivalente a una JwtStrategy de Passport en NestJS, pero corriendo como filtro
 * de servlet una vez por request: lee el header, valida el token y puebla el
 * SecurityContext para que authorizeHttpRequests/@PreAuthorize lo puedan usar.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        // TODO (Paso 4):
        // 1. leer el header "Authorization"; si es null o no empieza con "Bearer ", no hay nada que
        //    hacer aca - dejar pasar la request tal cual (no es un endpoint que requiera JWT, o lo
        //    validara igual authorizeHttpRequests mas adelante en la cadena)
        // 2. si hay token (header.substring(7)), en un try/catch:
        //    a. extraer el email con jwtService.extraerEmail(token)
        //    b. si el email no es null y SecurityContextHolder.getContext().getAuthentication() == null,
        //       cargar el UserDetails con userDetailsService.loadUserByUsername(email)
        //    c. si jwtService.esValido(token, userDetails.getUsername()), armar un
        //       UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()),
        //       setearle los details con new WebAuthenticationDetailsSource().buildDetails(request)
        //       y ponerlo en SecurityContextHolder.getContext().setAuthentication(authToken)
        // 3. catch (Exception ex): token malformado/expirado/firma invalida - NO propagar la excepcion,
        //    solo SecurityContextHolder.clearContext() (el endpoint protegido respondera 401/403 igual)
        //
        // OJO: no borres la siguiente linea ni la muevas - sin ella la cadena de filtros de Spring
        // se corta y ninguna request llega a su controller, incluso las que no requieren JWT.
        filterChain.doFilter(request, response);
    }
}
