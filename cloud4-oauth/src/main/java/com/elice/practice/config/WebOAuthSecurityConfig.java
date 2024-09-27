package com.elice.practice.config;

import com.elice.practice.config.jwt.TokenProvider;
import com.elice.practice.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.elice.practice.config.oauth.OAuth2SuccessHandler;
import com.elice.practice.config.oauth.OAuth2UserCustomService;
import com.elice.practice.repository.RefreshTokenRepository;
import com.elice.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

// WebOAuthSecurityConfig 클래스는 Spring Security 설정을 담당
@Configuration
@RequiredArgsConstructor
public class WebOAuthSecurityConfig {

    // 의존성 주입
    // OAuth2UserCustomService - OAuth2 사용자 정보를 처리하는 서비스
    private final OAuth2UserCustomService oAuth2UserCustomService;

    // TokenProvider - JWT 토큰을 생성하고 검증하는 클래스
    private final TokenProvider tokenProvider;

    // RefreshTokenRepository - 리프레시 토큰을 저장하고 관리하는 저장소
    private final RefreshTokenRepository refreshTokenRepository;

    // UserService - 사용자 관련 비즈니스 로직을 처리하는 서비스
    private final UserService userService;

    // 정적 리소스 및 H2 콘솔 요청에 대해 보안 필터를 적용하지 않음
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console()) // H2 데이터베이스 콘솔에 대한 요청 무시
                .requestMatchers("/img/**", "/css/**", "/js/**"); // 정적 리소스에 대한 요청 무시
    }

    // Spring Security 필터 체인 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 기본적인 보안 기능 비활성화
        http.csrf().disable() // CSRF 비활성화
                .httpBasic().disable() // HTTP Basic 인증 비활성화
                .formLogin().disable() // 기본 로그인 폼 비활성화
                .logout().disable(); // 로그아웃 기능 비활성화

        // 세션을 사용하지 않고, JWT를 통한 Stateless 방식으로 처리
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 정책을 Stateless로 설정

        // JWT 인증 필터를 기존 UsernamePasswordAuthenticationFilter 앞에 추가
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 권한 설정
        http.authorizeRequests()
                .requestMatchers("/api/token").permitAll() // /api/token 경로는 누구나 접근 가능
                .requestMatchers("/api/**").authenticated() // /api/** 경로는 인증된 사용자만 접근 가능
                .anyRequest().permitAll(); // 그 외 경로는 모두 접근 가능

        // OAuth2 로그인 설정
        http.oauth2Login()
                .loginPage("/login") // 로그인 페이지 경로 설정
                .authorizationEndpoint()
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository()) // 쿠키 기반 OAuth2 요청 저장소 사용
                .and()
                .successHandler(oAuth2SuccessHandler()) // 성공 핸들러 설정
                .userInfoEndpoint()
                .userService(oAuth2UserCustomService); // 사용자 정보 서비스 설정

        // 로그아웃 설정
        http.logout()
                .logoutSuccessUrl("/login"); // 로그아웃 성공 시 이동할 경로 설정

        // 예외 처리 - 인증되지 않은 사용자가 /api/** 경로에 접근할 경우 401 Unauthorized 응답
        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher("/api/**"));

        return http.build(); // 설정 완료 후 필터 체인 빌드
    }

    // OAuth2 성공 핸들러 설정
    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                userService);
    }

    // JWT 인증 필터 설정
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    // 쿠키 기반 OAuth2 요청 저장소 설정
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    // 비밀번호 암호화를 위한 BCryptPasswordEncoder 설정
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
