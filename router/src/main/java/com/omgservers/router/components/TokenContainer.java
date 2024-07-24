package com.omgservers.router.components;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class TokenContainer {

    final AtomicReference<String> serviceToken;

    public TokenContainer() {
        serviceToken = new AtomicReference<String>();
    }

    public void set(String token) {
        serviceToken.set(token);
    }

    public String get() {
        return serviceToken.get();
    }
}
