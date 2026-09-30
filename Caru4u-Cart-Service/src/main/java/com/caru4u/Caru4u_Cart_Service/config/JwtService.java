package com.caru4u.Caru4u_Cart_Service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
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

    /**
     * Converts Base64 JWT secret into a SecretKey.
     *
     * IMPORTANT:
     * Customer Service and Cart Service must use
     * the SAME jwt.secret.
     */
    private SecretKey getSigningKey() {

        byte[] keyBytes = Base64.getDecoder()
                .decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Validate signature and extract all claims.
     *
     * JJWT 0.12.6 syntax.
     */
    public Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Extract customerId custom claim.
     */
    public Long extractCustomerId(String token) {

        Claims claims = extractClaims(token);

        Number customerId =
                claims.get("customerId", Number.class);

        if (customerId == null) {
            throw new IllegalArgumentException(
                    "customerId missing from JWT"
            );
        }

        return customerId.longValue();
    }

    /**
     * Extract subject.
     *
     * If Customer Service puts email in subject,
     * this returns the customer's email.
     */
    public String extractEmail(String token) {

        return extractClaims(token)
                .getSubject();
    }

    /**
     * Check JWT signature + expiration.
     */
    public boolean isTokenValid(String token) {

        try {

            Claims claims = extractClaims(token);

            Date expiration = claims.getExpiration();

            return expiration != null
                    && expiration.after(new Date());

        } catch (JwtException |
                 IllegalArgumentException e) {
            System.out.println("========== JWT ERROR ==========");
            e.printStackTrace();
            System.out.println("===============================");

            return false;
        }
    }
}