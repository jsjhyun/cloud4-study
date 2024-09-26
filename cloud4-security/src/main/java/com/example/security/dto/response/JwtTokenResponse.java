package com.example.security.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class JwtTokenResponse {
    private String accessToken;
}
