package com.alfredodev.miniproyectos9.security;

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

    public String generarAccessToken(String email, String rol) {
        // TODO Paso 2: el rol viaja como claim del JWT (no solo el email como subject) para
        // que el filtro pueda armar las GrantedAuthority sin volver a golpear la base de datos.
        // Jwts.builder().subject(email).claim("rol", rol).issuedAt(new Date())
        //     .expiration(new Date(System.currentTimeMillis() + accessTokenMinutes * 60_000))
        //     .signWith(signingKey).compact();
        throw new UnsupportedOperationException("TODO");
    }

    public String extraerEmail(String token) {
        // TODO Paso 2: extraerClaim(token, Claims::getSubject)
        throw new UnsupportedOperationException("TODO");
    }

    public String extraerRol(String token) {
        // TODO Paso 2: extraerClaim(token, claims -> claims.get("rol", String.class))
        throw new UnsupportedOperationException("TODO");
    }

    public boolean esValido(String token, String email) {
        // TODO Paso 2: extraerEmail(token).equals(email) && !estaExpirado(token)
        throw new UnsupportedOperationException("TODO");
    }

    private boolean estaExpirado(String token) {
        // TODO Paso 2: extraerClaim(token, Claims::getExpiration).before(new Date())
        throw new UnsupportedOperationException("TODO");
    }

    private <T> T extraerClaim(String token, Function<Claims, T> resolver) {
        // TODO Paso 2:
        // Claims claims = Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
        // return resolver.apply(claims);
        throw new UnsupportedOperationException("TODO");
    }
}
