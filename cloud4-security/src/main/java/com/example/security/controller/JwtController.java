package com.example.security.controller;

import com.example.security.dto.JwtTokenDto;
import com.example.security.dto.request.JwtTokenLoginRequest;
import com.example.security.dto.response.JwtTokenResponse;
import com.example.security.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class JwtController {

    private final UserService userService;

    @PostMapping("/jwt-login")
    public ResponseEntity<JwtTokenResponse> jwtLogin(
            @RequestBody JwtTokenLoginRequest request,
            HttpServletResponse response
    ) {
        JwtTokenDto jwtTokenResponse = userService.login(request);

        Cookie refreshTokenCookie = new Cookie(
                "refreshToken",
                jwtTokenResponse.getRefreshToken()
        );

        refreshTokenCookie.setHttpOnly(true); // 자바스크립트에서 접근할 수 없도록 설정
        refreshTokenCookie.setSecure(true); // HTTPS에서만 전송되도록 설정 (생산 환경에서 사용)
        refreshTokenCookie.setPath("/"); // 쿠키의 유효 경로 설정
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 쿠키의 유효 기간 설정 (예: 7일)

        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok().body(JwtTokenResponse
                .builder()
                .accessToken(jwtTokenResponse.getAccessToken())
                .build());
    }

}

