package org.example.vofasbackendv1.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;

@Component
public class JwtTokenProvider {

    //    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final String SECRET = "aP9z!g4tLm#dXy7eWqRtUvMnBoLiKjHgF3rVtYz1234567890aBcDeFgHiJkLmNoPq";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    private final long EXPIRATION_TIME = 3600000; // 1 hour

    // Generate JWT token with email and role
    public String generateToken(Long userID, String role) {
        return Jwts.builder().setSubject(userID.toString()).claim("role", role)  // Add role to the token
                .setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("role", String.class);
    }

    public Authentication getAuthentication(String token) {
        String userId = getUsernameFromToken(token);
        String role = getRoleFromToken(token);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        return new UsernamePasswordAuthenticationToken(userId, null, Collections.singletonList(authority));
    }
}