package com.example.security.config.jwt.filter;

import com.example.security.config.jwt.impl.AuthTokenImpl;
import com.example.security.config.jwt.impl.JwtProviderImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static com.example.security.config.jwt.UserConstants.AUTHORIZATION_TOKEN_KEY;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProviderImpl tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        Optional<String> token = resolveToken(request);

        if (token.isPresent()) {
            AuthTokenImpl jwtToken =
                    tokenProvider.convertAuthToken(token.get().split(" ")[1]);

            if (jwtToken.validate()) {
                Authentication authentication =
                        tokenProvider.getAuthentication(jwtToken);

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> resolveToken(HttpServletRequest request) {

        String authToken = request.getHeader(AUTHORIZATION_TOKEN_KEY);

        if (StringUtils.hasText(authToken)) {
            return Optional.of(authToken);
        } else {
            return Optional.empty();
        }
    }
}
