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
        // TODO (Paso 2): construir el JWT con Jwts.builder():
        // 1. .subject(email) - el subject es el identificador del usuario
        // 2. .claim("rol", rol) - claim custom para llevar el rol dentro del token
        // 3. .issuedAt(new Date()) - fecha de emision
        // 4. .expiration(new Date(System.currentTimeMillis() + accessTokenMinutes * 60_000))
        // 5. .signWith(signingKey) - firma HMAC con la clave armada en el constructor
        // 6. .compact() - serializa a String
        throw new UnsupportedOperationException("TODO");
    }

    public String extraerEmail(String token) {
        // TODO (Paso 2): devolver extraerClaim(token, Claims::getSubject)
        throw new UnsupportedOperationException("TODO");
    }

    public boolean esValido(String token, String email) {
        // TODO (Paso 2): valido si extraerEmail(token) coincide con email Y el token no esta expirado
        // (extraerEmail(token).equals(email) && !estaExpirado(token))
        throw new UnsupportedOperationException("TODO");
    }

    private boolean estaExpirado(String token) {
        // TODO (Paso 2): devolver extraerClaim(token, Claims::getExpiration).before(new Date())
        throw new UnsupportedOperationException("TODO");
    }

    private <T> T extraerClaim(String token, Function<Claims, T> resolver) {
        // TODO (Paso 2): parsear y verificar la firma con
        // Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload(),
        // y aplicar resolver.apply(claims) sobre el resultado
        throw new UnsupportedOperationException("TODO");
    }
}
