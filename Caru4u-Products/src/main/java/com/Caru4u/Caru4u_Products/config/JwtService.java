package com.Caru4u.Caru4u_Products.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;


    private SecretKey getSigningKey() {

        byte[] keyBytes =
                Base64.getDecoder()
                        .decode(secret);

        return Keys.hmacShaKeyFor(
                keyBytes
        );
    }


    public Claims extractClaims(
            String token) {

        return Jwts.parser()

                .setSigningKey(
                        getSigningKey()
                )

                .parseClaimsJws(token)

                .getBody();
    }


    public Long extractCustomerId(
            String token) {

        Claims claims =
                extractClaims(token);

        Number customerId =
                claims.get(
                        "customerId",
                        Number.class
                );

        if (customerId == null) {

            throw new IllegalArgumentException(
                    "customerId missing from JWT"
            );
        }

        return customerId.longValue();
    }


    public String extractEmail(
            String token) {

        return extractClaims(token)
                .getSubject();
    }


    public boolean isTokenValid(
            String token) {

        try {

            Claims claims =
                    extractClaims(token);

            Date expiration =
                    claims.getExpiration();

            return expiration != null
                    && expiration.after(
                    new Date()
            );

        } catch (Exception e) {

            return false;
        }
    }
}