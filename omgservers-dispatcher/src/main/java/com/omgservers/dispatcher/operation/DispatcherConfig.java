package com.omgservers.dispatcher.operation;

import io.smallrye.config.ConfigMapping;

import java.net.URI;

@ConfigMapping(prefix = "omgservers")
public interface DispatcherConfig {

    URI serviceUri();

    DispatcherUserConfig dispatcherUser();

    String expiredConnectionsHandlerJobInterval();

    long expiredConnectionsIdleTimeout();

    String refreshDispatcherTokenJobInterval();

    interface DispatcherUserConfig {
        String alias();

        String password();
    }
}
