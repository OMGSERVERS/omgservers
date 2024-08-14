package com.omgservers.service.server.security;

import lombok.Getter;

@Getter
public enum ServiceSecurityAttributes {
    TOKEN_ID("token_id"),
    RAW_TOKEN("raw_token"),
    RUNTIME_ID("runtime_id"),
    CLIENT_ID("client_id"),
    USER_ROLE("user_role");

    final String attributeName;

    ServiceSecurityAttributes(final String attributeName) {
        this.attributeName = attributeName;
    }
}
