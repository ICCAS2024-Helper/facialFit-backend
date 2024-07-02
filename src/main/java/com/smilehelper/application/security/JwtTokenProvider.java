package com.smilehelper.application.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

/**
 * JwtTokenProvider 클래스는 JWT 토큰을 생성하고 유효성을 검사합니다.
 */
public class JwtTokenProvider {

    // 안전한 키 생성
    private static final Key JWT_SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // Access Token Expiration Time (30 minutes)
    private static final int ACCESS_TOKEN_EXPIRATION_MS = 30 * 60 * 1000;

    // Refresh Token Expiration Time (7 days)
    private static final int REFRESH_TOKEN_EXPIRATION_MS = 7 * 24 * 60 * 60 * 1000;

    private final static JwtParser jwtParser;

    static {
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY)
                .build();
    }

    /**
     * Access Token 생성
     * @param userDetails 사용자 정보 객체
     * @return 생성된 Access Token
     */
    public static String generateAccessToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(JWT_SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Refresh Token 생성
     * @param userDetails 사용자 정보 객체
     * @return 생성된 Refresh Token
     */
    public static String generateRefreshToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(JWT_SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * JWT 토큰에서 사용자 이름 추출
     * @param token JWT 토큰
     * @return 사용자 이름
     */
    public static String getUsernameFromJwt(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    /**
     * JWT 토큰 유효성 검사
     * @param token JWT 토큰
     * @return 유효성 여부
     */
    public static boolean validateToken(String token) {
        if (token == null || token.isEmpty())
            return false;

        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            // 유효하지 않은 JWT 서명
            // log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            // 유효하지 않은 JWT 토큰
            // log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            // 만료된 JWT 토큰
            // log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            // 지원되지 않는 JWT 토큰
            // log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            // 빈 JWT 클레임
            // log.error("JWT claims string is empty.");
        }
        return false;
    }
}