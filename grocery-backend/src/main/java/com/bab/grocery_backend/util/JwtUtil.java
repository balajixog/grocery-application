package com.bab.grocery_backend.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final String SECRET="balajisvvczhgdfa8yrhsjber3h8euzdfjhf8ey";
    private final long EXPIRATION=1000*60*60;
    private final SecretKey secretKey= Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String genrateToken(String email){
      return  Jwts.builder()
           .subject(email)
           .issuedAt(new Date(System.currentTimeMillis()))
           .expiration(new Date(System.currentTimeMillis()+EXPIRATION))
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
}
