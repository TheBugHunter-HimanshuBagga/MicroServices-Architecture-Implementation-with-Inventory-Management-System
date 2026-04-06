package com.HimanshuBagga.ecommerce.api_gateway.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtService {
//JWT Token → Decode → Verify → Extract Data (userId)
    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8)); // Converts string → cryptographic key
    }

    public Long getUserIdFromToken(String token){
        Claims claim = Jwts.parser() // claim is jwt data , parser() = a tool that reads and understands a JWT token
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.valueOf(claim.getSubject());
    }

    public String getUserRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("role", String.class);
    }
/*
{
  "sub": "101",
  "role": "ADMIN",
  "exp": 1710000000
}

 claims.get("role", String.class);

 "ADMIN"
*/
}
