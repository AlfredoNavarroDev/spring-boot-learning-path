package com.alfredodev.miniproyectos8.security;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long accessTokenMinutes;

    public JwtService(JwtProperties properties) {
        this.signingKey = Keys.hmacShaKeyFor(properties.secret().getBytes());
        this.accessTokenMinutes = properties.accessTokenMinutes();
    }

    // TODO Paso 2: Jwts.builder().subject(email).claim("rol", rol).issuedAt(new Date())
    // .expiration(new Date(System.currentTimeMillis() + accessTokenMinutes * 60_000))
    // .signWith(signingKey).compact()
    // Pista clave de esta semana: el rol viaja DENTRO del token como claim("rol", rol) -
    // asi el filtro (JwtAuthenticationFilter) arma las authorities sin volver a golpear la DB.
    public String generarAccessToken(String email, String rol) {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO Paso 2: extraerClaim(token, Claims::getSubject)
    public String extraerEmail(String token) {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO Paso 2: extraerClaim(token, claims -> claims.get("rol", String.class))
    // El rol NO se vuelve a consultar en UsuarioRepository: se lee de vuelta del mismo
    // claim "rol" que generarAccessToken escribio.
    public String extraerRol(String token) {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO Paso 2: extraerEmail(token).equals(email) && !estaExpirado(token)
    public boolean esValido(String token, String email) {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO Paso 2: extraerClaim(token, Claims::getExpiration).before(new Date())
    private boolean estaExpirado(String token) {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO Paso 2: parsear con Jwts.parser().verifyWith(signingKey).build()
    // .parseSignedClaims(token).getPayload() y aplicarle el resolver
    private <T> T extraerClaim(String token, Function<Claims, T> resolver) {
        throw new UnsupportedOperationException("TODO");
    }
}
