package com.example.security.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoogleUserInfoDto {
    private String email;
    private String name;
    private String providerId;
}
