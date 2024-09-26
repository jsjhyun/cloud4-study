//package com.example.security.controller;
//
//import com.example.security.dto.GoogleUserInfoDto;
//import com.example.security.dto.JwtTokenDto;
//import com.example.security.dto.response.JwtTokenResponse;
//import com.example.security.service.UserService;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//@Slf4j
//public class UserInfoController {
//
//    private final UserService userService;
//
//    @GetMapping("/userinfo")
//    public ResponseEntity<JwtTokenResponse> getUserInfo(
//            @AuthenticationPrincipal OAuth2User principal,
//            HttpServletResponse response
//    ) {
//        if (principal != null) {
//            JwtTokenDto jwtTokenDto = userService.googleLogin(GoogleUserInfoDto.builder()
//                    .email((String) principal.getAttribute("email"))
//                    .name((String) principal.getAttribute("name"))
//                    .providerId((String) principal.getAttribute("sub"))
//                    .build());
//
//            Cookie refreshTokenCookie = new Cookie(
//                    "refreshToken",
//                    jwtTokenDto.getRefreshToken()
//            );
//
//            refreshTokenCookie.setHttpOnly(true); // 자바스크립트에서 접근할 수 없도록 설정
//            refreshTokenCookie.setSecure(true); // HTTPS에서만 전송되도록 설정 (생산 환경에서 사용)
//            refreshTokenCookie.setPath("/"); // 쿠키의 유효 경로 설정
//            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 쿠키의 유효 기간 설정 (예: 7일)
//
//            response.addCookie(refreshTokenCookie);
//
//            return ResponseEntity.ok().body(JwtTokenResponse
//                    .builder()
//                    .accessToken(jwtTokenDto.getAccessToken())
//                    .build());
//        } else {
//            return ResponseEntity.ok().body(JwtTokenResponse.builder().accessToken("null").build());
//        }
//    }
//}
//
//// http://localhost:8080/oauth2/authorization/google