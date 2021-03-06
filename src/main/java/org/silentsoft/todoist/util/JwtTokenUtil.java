package org.silentsoft.todoist.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    public enum TokenType {
        ACCESS_TOKEN(1000 * 60 * 15),               // 15 minutes
        REFRESH_TOKEN(1000 * 60 * 60 * 24 * 7 * 8); // 8  weeks

        private long expiry;

        TokenType(long expiry) {
            this.expiry = expiry;
        }

        public long getExpiry() {
            return expiry;
        }
    }

    @Value("${jwt.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public long getExpiryFromNow(String token) {
        return (getExpirationDateFromToken(token).getTime() - System.currentTimeMillis()) / 1000;
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getAllClaimsFromToken(token));
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token).getBody();
    }

    public boolean expiredToken(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public boolean notExpiredToken(String token) {
        return !expiredToken(token);
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(new HashMap<String, Object>(), userDetails.getUsername(), TokenType.ACCESS_TOKEN);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(new HashMap<String, Object>(), userDetails.getUsername(), TokenType.REFRESH_TOKEN);
    }

    private String generateToken(Map<String, Object> claims, String subject, TokenType tokenType) {
        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(subject)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + tokenType.getExpiry()))
                   .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                   .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && notExpiredToken(token);
    }

}