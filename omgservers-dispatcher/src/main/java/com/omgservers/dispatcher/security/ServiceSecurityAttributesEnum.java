package com.omgservers.dispatcher.security;

import lombok.Getter;

@Getter
public enum ServiceSecurityAttributesEnum {
    TOKEN_ID("token_id"),
    RAW_TOKEN("raw_token"),
    RUNTIME_ID("runtime_id"),
    USER_ID("user_id"),
    SUBJECT("subject"),
    USER_ROLE("user_role");

    final String attributeName;

    ServiceSecurityAttributesEnum(final String attributeName) {
        this.attributeName = attributeName;
    }
}
