package com.alfredodev.miniproyectos7.security;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Responsabilidad unica: emitir y validar tokens JWT. No sabe nada de usuarios de
 * base de datos ni de HTTP (equivalente a una JwtStrategy/JwtService de Nest, pero
 * sin decorators - se invoca explicitamente desde el filtro y el AuthService).
 */
@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long accessTokenMinutes;

    public JwtService(JwtProperties properties) {
        this.signingKey = Keys.hmacShaKeyFor(properties.secret().getBytes());
        this.accessTokenMinutes = properties.accessTokenMinutes();
    }

    public String generarAccessToken(String email, String rol) {
        return Jwts.builder().subject(email).claim("rol", rol).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenMinutes * 60_000))
                .signWith(signingKey).compact();
    }

    public String extraerEmail(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    public boolean esValido(String token, String email) {
        return extraerEmail(token).equals(email) && !estaExpirado(token);
    }

    private boolean estaExpirado(String token) {
        return extraerClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extraerClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
        return resolver.apply(claims);
    }
}
