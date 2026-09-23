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

    // El rol viaja como claim del JWT: el filtro lo usa para construir las
    // GrantedAuthority sin tener que volver a golpear la base de datos en cada request.
    public String generarAccessToken(String email, String rol) {
        return Jwts.builder().subject(email).claim("rol", rol).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenMinutes * 60_000))
                .signWith(signingKey).compact();
    }

    public String extraerEmail(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    public String extraerRol(String token) {
        return extraerClaim(token, claims -> claims.get("rol", String.class));
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
