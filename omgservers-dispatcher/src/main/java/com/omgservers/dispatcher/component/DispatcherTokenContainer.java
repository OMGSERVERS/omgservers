package com.omgservers.dispatcher.component;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@ApplicationScoped
public class DispatcherTokenContainer {

    final AtomicReference<String> dispatcherToken;

    public DispatcherTokenContainer() {
        dispatcherToken = new AtomicReference<>();
    }

    public String getToken() {
        return dispatcherToken.get();
    }

    public void setToken(final String rawToken) {
        dispatcherToken.set(rawToken);
    }
}
