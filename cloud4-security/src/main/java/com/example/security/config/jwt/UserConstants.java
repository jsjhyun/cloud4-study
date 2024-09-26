package com.example.security.config.jwt;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class UserConstants {
    public static final String AUTHORIZATION_TOKEN_KEY = "Authorization";
    public static final String REFRESH_TOKEN_TYPE_VALUE = "refresh_token";
    public static final String ACCESS_TOKEN_TYPE_VALUE = "access_token";
}
