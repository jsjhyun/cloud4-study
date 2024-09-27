package com.elice.practice.controller;

import com.elice.practice.config.jwt.JwtFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.elice.practice.config.jwt.JwtProperties;
import com.elice.practice.domain.RefreshToken;
import com.elice.practice.domain.User;
import com.elice.practice.dto.CreateAccessTokenRequest;
import com.elice.practice.repository.RefreshTokenRepository;
import com.elice.practice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TokenApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        userRepository.deleteAll();
    }

    @DisplayName("createNewAccessToken: 새로운 액세스 토큰을 발급한다.")
    @Test
    public void createNewAccessToken() throws Exception {
        // given
        final String url = "/api/token";

        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        String refreshToekn = JwtFactory.builder()
                .claims(Map.of("id", testUser.getId()))
                .build()
                .createToken(jwtProperties);

        refreshTokenRepository.save(new RefreshToken(testUser.getId(), refreshToekn));

        CreateAccessTokenRequest request = new CreateAccessTokenRequest();
        request.setRefreshToken(refreshToekn);
        final String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }

    @DisplayName("createNewAccessToken: 유효하지 않은 리프레시 토큰으로 요청 실패")
    @Test
    public void createNewAccessTokenWithInvalidToken() throws Exception {
        // given
        final String url = "/api/token";

        // 사용자 없이 리프레시 토큰 생성 (유효하지 않은 리프레시 토큰)
        String invalidRefreshToken = JwtFactory.builder()
                .claims(Map.of("id", 9999L))  // 유효하지 않은 사용자 ID
                .build()
                .createToken(jwtProperties);

        // 요청 데이터 설정
        CreateAccessTokenRequest request = new CreateAccessTokenRequest();
        request.setRefreshToken(invalidRefreshToken);
        final String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        resultActions
                .andExpect(status().isUnauthorized())  // 401 Unauthorized 기대
                .andExpect(jsonPath("$.error").value("Invalid token: Unexpected token"));  // 에러 메시지 존재 확인
    }
}