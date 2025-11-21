package com.example.LVTN.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY; // lấy từ application.yml

    private final long expirationMs = 1000L * 60 * 60 * 24; // 1 ngày

    private SecretKeySpec getKey() {
        return new SecretKeySpec(SIGNER_KEY.getBytes(), "HmacSHA256");
    }

    public String generateToken(String phone, String role, String fullName, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("fullName", fullName);
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(phone)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, String username) {
        try {
            final String subject = parseToken(token).getSubject();
            return (subject.equals(username) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }
}
