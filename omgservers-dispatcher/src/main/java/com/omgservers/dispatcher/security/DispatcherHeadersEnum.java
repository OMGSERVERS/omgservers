package com.omgservers.dispatcher.security;

import lombok.Getter;

@Getter
public enum DispatcherHeadersEnum {
    RUNTIME_ID("Runtime-Id"),
    USER_ROLE("User-Role"),
    SUBJECT("Subject");

    final String headerName;

    DispatcherHeadersEnum(final String headerName) {
        this.headerName = headerName;
    }
}
