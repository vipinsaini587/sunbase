package com.loginService.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${jwt.expirationMs}")
    private long expirationMs;

//    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${jwt.secret}")
    private String SECRET_KEY;


    // Generate token for user
    public String generateToken(String username) {
        //System.out.println( Base64.getEncoder().encodeToString(SECRET_KEY.getEncoded()));
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
        return token;
    }

    // Extract username from token
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from token
    public Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    // Extract a claim from the token using the provided function
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        boolean jwtExpired = true;
        try{
            jwtExpired = extractExpiration(token).before(new Date());
        }catch (ExpiredJwtException e){
            return true;
        }
        return jwtExpired;
    }
    public boolean tokenExpired(String token){
        token = token.substring(6,token.length());
        return isTokenExpired(token);
    }
}
