package com.omgservers.worker.component.tokenHolder;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class TokenContainer {

    final AtomicReference<String> atomicReference;

    TokenContainer() {
        this.atomicReference = new AtomicReference<>();
    }

    public void set(final String token) {
        atomicReference.set(token);
    }

    public String get() {
        return atomicReference.get();
    }
}
