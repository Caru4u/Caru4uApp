package Caru4u.Order_Servivce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {

        byte[] keyBytes =
                Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }


    public Claims extractClaims(String token) {

        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public Long extractCustomerId(String token) {

        Claims claims =
                extractClaims(token);

        Number customerId =
                claims.get(
                        "customerId",
                        Number.class
                );

        return customerId.longValue();
    }


    public String extractEmail(String token) {

        return extractClaims(token)
                .getSubject();
    }
}