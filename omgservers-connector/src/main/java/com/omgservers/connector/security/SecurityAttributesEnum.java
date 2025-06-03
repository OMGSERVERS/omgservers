package com.omgservers.connector.security;

import lombok.Getter;

@Getter
public enum SecurityAttributesEnum {
    TOKEN_ID("token_id"),
    RAW_TOKEN("raw_token"),
    USER_ID("user_id"),
    CLIENT_ID("client_id"),
    SUBJECT("subject"),
    USER_ROLE("user_role");

    final String attributeName;

    SecurityAttributesEnum(final String attributeName) {
        this.attributeName = attributeName;
    }
}
