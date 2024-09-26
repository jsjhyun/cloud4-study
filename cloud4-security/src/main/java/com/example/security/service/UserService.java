package com.example.security.service;

import com.example.security.config.jwt.impl.AuthTokenImpl;
import com.example.security.config.jwt.impl.JwtProviderImpl;
import com.example.security.domain.User;
import com.example.security.dto.*;
import com.example.security.dto.request.AddUserRequest;
import com.example.security.dto.request.JwtTokenLoginRequest;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import static com.example.security.domain.Provider.*;
import static com.example.security.domain.Role.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProviderImpl jwtProvider; // JwtProviderImpl 추가

    public Long userSignUp(AddUserRequest addUserRequest){
        return userRepository.save(User.builder()
                .password(bCryptPasswordEncoder.encode(addUserRequest.getPassword()))
                .email(addUserRequest.getEmail())
                .role(USER)
                .build()).getId();
    }

    public JwtTokenDto login(JwtTokenLoginRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Email"));

        if(!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("wrong password!");
        }

        Map<String, Object> claims = Map.of(
                "accountId", user.getId(),
                "role", user.getRole()
        );

        AuthTokenImpl accessToken = jwtProvider.createAccessToken(
                user.getId().toString(),
                user.getRole(),
                claims
        );

        AuthTokenImpl refreshToken = jwtProvider.createRefreshToken(
                user.getId().toString(),
                user.getRole(),
                claims
        );

        return JwtTokenDto.builder()
                .accessToken(accessToken.getToken())
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find Email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}

