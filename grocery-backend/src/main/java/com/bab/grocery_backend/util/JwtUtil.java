package com.bab.grocery_backend.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final String secret;
    private final long expiration;
    private final SecretKey secretKey;

     public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration
    ) {
        this.secret = secret;
        this.expiration = expiration;
        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }
    public String generateToken(String email,String role){
      return  Jwts.builder()
           .subject(email)
           .claim("role",role)
           .issuedAt(new Date(System.currentTimeMillis()))
           .expiration(new Date(System.currentTimeMillis()+expiration))
           .signWith(secretKey)
           .compact();
    }

    public Boolean validationJwtToken(String token){
        try{
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);

            return true;

        }
        catch(JwtException exception){
            return false;
        }
    }
    
    public String extractEmail(String token){
       return Jwts.parser()
       .verifyWith(secretKey)
       .build()
       .parseSignedClaims(token)
       .getPayload()   
       .getSubject();
    }  
    public String extractRole(String token) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("role", String.class);
    }
}
