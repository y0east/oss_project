package kr.ac.hufs.ice.ice.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.ac.hufs.ice.ice.entity.member.Role;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "my-very-very-secret-key-for-hufs-oss-project1234";
    public static final long ACCESS_TOKEN_EXP = 1000 * 60 * 60; // 1시간
    public static final long REFRESH_TOKEN_EXP = 1000L * 60 * 60 * 24 * 7; // 7일
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateAccessToken(String studentId, Role role) {
        Claims claims = Jwts.claims().setSubject(studentId);
        claims.put("role", role.name());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXP))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //refresh Token 이라 studentId만 넣음
    public String generateRefreshToken(String studentId) {
        return Jwts.builder()
                .setSubject(studentId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXP))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }


    public String getStudentIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Role getRoleFromToken(String token) {
        String roleName = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
        return Role.valueOf(roleName);
    }

    // AccessToken 이 발급된 시간
    public long getIssuedAt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getIssuedAt()
                .getTime();
    }
}

