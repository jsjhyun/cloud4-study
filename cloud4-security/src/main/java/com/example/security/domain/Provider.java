package com.example.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Provider {
    GOOGLE("GOOGLE"),
    NONE("NONE");

    private final String value;
}
