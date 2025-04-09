package vn.student_management.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "your_secret_key_should_be_long_enough_123456";
    private final long EXPIRATION_TIME = 86400000; // 1 ngày
    private final long REFRESH_THRESHOLD = 86400000; // 1 ngày

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean shouldRefreshToken(String token) {
        try {
            Date expiration = extractExpiration(token);
            long diffMillis = System.currentTimeMillis() - expiration.getTime();
            // Nếu token hết hạn chưa quá 1 ngày (86400000ms) thì vẫn được refresh
            return diffMillis >= 0 && diffMillis <= REFRESH_THRESHOLD;
        } catch (ExpiredJwtException e) {
            long expiredAt = e.getClaims().getExpiration().getTime();
            long diffMillis = System.currentTimeMillis() - expiredAt;
            return diffMillis <= REFRESH_THRESHOLD;
        }
    }

    public Date extractExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsernameHandleExpired(String token) {
        try {
            return extractUsername(token);
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }
}
