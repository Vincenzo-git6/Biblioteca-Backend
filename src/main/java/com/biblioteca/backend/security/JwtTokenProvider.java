package com.biblioteca.backend.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;


// Classe che gestisce la generazione e la validazione dei token JWT
@Component
public class JwtTokenProvider {

    private String jwtSecret = "T7n3WcR9Q3ZjLwV5FfN2nPr3MqXwBv1Xh8ZmYk9rI4UzWvGp3Xl7J5Gb1MvLsFz0";
    private long jwtExpirationInMs = 86400 * 365; // impostiamo durata token a 365 giorni

    // Crea una chiave segreta a partire dalla stringa jwtSecret per firmare i token JWT
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // Genera un token JWT per l'utente autenticato, includendo il nome utente e i ruoli
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username) // chiave principale (solitamente lo username)
                .claim("roles", authorities) // aggiunge i ruoli come claim
                .setIssuedAt(currentDate) // data di emissione
                .setExpiration(expiryDate) // data di scadenza
                .signWith(getSigningKey()) // firma con la chiave segreta
                .compact(); // compatta il tutto in una stringa JWT
    }

    // Estrae il nome utente (subject) da un token JWT valido
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Estrae i ruoli (authorities) dal token JWT e li converte in oggetti GrantedAuthority
    public Collection<GrantedAuthority> getAuthoritiesFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        
        String roles = (String) claims.get("roles");
        
        return Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // Valida un token JWT verificando firma, scadenza e formato
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty");
        }
        return false;
    }
}


