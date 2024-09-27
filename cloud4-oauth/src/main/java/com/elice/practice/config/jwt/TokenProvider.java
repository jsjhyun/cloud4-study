package com.elice.practice.config.jwt;

import com.elice.practice.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor  // final 필드에 대해 자동으로 생성자를 만들어주는 Lombok 어노테이션
@Service  // Spring의 서비스 레이어에 속하는 클래스로 지정
public class TokenProvider {

    private final JwtProperties jwtProperties;  // JWT 관련 설정 정보를 담고 있는 객체 (issuer, secretKey 등)

    // 사용자 정보를 기반으로 JWT 토큰을 생성하는 메서드, 만료 시간을 인자로 받아서 토큰 생성
    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();  // 현재 시간을 가져옴
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);  // 만료 시간을 더하여 토큰 생성
    }

    // JWT 토큰을 실제로 생성하는 메서드
    private String makeToken(Date expiry, User user) {
        Date now = new Date();  // 현재 시간

        // JWT 토큰의 구성 요소를 설정하고, 서명(Signature)을 추가하여 토큰을 생성
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)  // 헤더에 JWT 타입 설정
                .setIssuer(jwtProperties.getIssuer())  // 토큰 발급자 정보 설정
                .setIssuedAt(now)  // 토큰 발급 시간 설정
                .setExpiration(expiry)  // 토큰 만료 시간 설정
                .setSubject(user.getEmail())  // 토큰의 주체(subject)로 사용자의 이메일 설정
                .claim("id", user.getId())  // 사용자 ID를 클레임에 추가
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())  // HMAC SHA-256 알고리즘으로 서명
                .compact();  // 최종적으로 JWT 토큰을 생성하여 반환
    }

    // 토큰의 유효성을 검증하는 메서드
    public boolean validToken(String token) {
        try {
            // 주어진 토큰을 파싱하여 유효한지 검증, 비밀 키를 사용해 서명 검증
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())  // 서명 검증을 위한 비밀 키 설정
                    .parseClaimsJws(token);

            return true;  // 토큰이 유효할 경우 true 반환
        } catch (Exception e) {
            return false;  // 토큰이 유효하지 않을 경우 false 반환
        }
    }

    // 토큰을 기반으로 Authentication 객체를 생성하는 메서드 (Spring Security에서 사용)
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);  // 토큰에서 클레임을 가져옴
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));  // 사용자 권한 설정

        // 사용자 정보를 담은 Authentication 객체 생성 (Spring Security의 인증 정보로 사용)
        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject
                (), "", authorities), token, authorities);
    }

    // 토큰에서 사용자 ID를 추출하는 메서드
    public Long getUserId(String token) {
        Claims claims = getClaims(token);  // 토큰에서 클레임을 가져옴
        return claims.get("id", Long.class);  // 클레임에서 사용자 ID를 추출
    }

    // 토큰에서 클레임을 파싱하여 가져오는 메서드
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())  // 비밀 키를 사용해 토큰을 파싱
                .parseClaimsJws(token)  // 토큰에서 JWT의 본문을 가져옴
                .getBody();  // 클레임을 반환
    }
}
