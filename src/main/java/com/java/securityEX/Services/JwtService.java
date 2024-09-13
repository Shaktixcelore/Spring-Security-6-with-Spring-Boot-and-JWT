package com.java.securityEX.Services;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private String secretKey = ""; 

    public JwtService() {
        if (secretKey.isEmpty()) {
            generateSecretKey();
        }
    }

    // Generate the secret key (use this only once, preferably in a config or environment variable)
    private void generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to generate JWT Token
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 minutes expiration
            .signWith(getKey())
            .compact();
    }

    // Method to retrieve the key for signing JWT
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes); // Ensure this key is compatible with HMAC-SHA256
    }
}
