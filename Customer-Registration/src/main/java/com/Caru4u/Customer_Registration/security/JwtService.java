package com.Caru4u.Customer_Registration.security;

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

    @Value("${jwt.expiration}")
    private long expiration;


    // ==========================================
    // Signing Key
    // ==========================================

    private SecretKey getSigningKey() {

        byte[] keyBytes =
                Base64.getDecoder()
                        .decode(secret);

        return Keys.hmacShaKeyFor(
                keyBytes
        );
    }


    // ==========================================
    // Generate JWT
    // ==========================================

    public String generateToken(
            Long customerId,
            String email) {

        Date now =
                new Date();

        Date expiry =
                new Date(
                        now.getTime()
                                + expiration
                );


        return Jwts.builder()

                // Email stored as subject
                .setSubject(email)

                // Customer ID
                .claim(
                        "customerId",
                        customerId
                )

                // Role
                .claim(
                        "role",
                        "CUSTOMER"
                )

                // Created time
                .setIssuedAt(now)

                // Expiration time
                .setExpiration(expiry)

                // Sign token
                .signWith(
                        getSigningKey()
                )

                .compact();
    }


    // ==========================================
    // Extract all Claims
    // ==========================================

    public Claims extractClaims(
            String token) {

        return Jwts.parser()

                .setSigningKey(
                        getSigningKey()
                )

                .parseClaimsJws(
                        token
                )

                .getBody();
    }


    // ==========================================
    // Extract Customer ID
    // ==========================================

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
                    "customerId missing from JWT token"
            );
        }


        return customerId.longValue();
    }


    // ==========================================
    // Extract Email
    // ==========================================

    public String extractEmail(
            String token) {

        return extractClaims(token)
                .getSubject();
    }


    // ==========================================
    // Validate JWT
    // ==========================================

    public boolean isTokenValid(
            String token) {

        try {

            Claims claims =
                    extractClaims(token);


            Date expiry =
                    claims.getExpiration();


            return expiry != null
                    && expiry.after(
                    new Date()
            );

        } catch (Exception e) {

            return false;
        }
    }
}