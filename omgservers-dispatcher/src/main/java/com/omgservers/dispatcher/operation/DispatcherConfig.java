package com.omgservers.dispatcher.operation;

import io.smallrye.config.ConfigMapping;

import java.net.URI;

@ConfigMapping(prefix = "omgservers")
public interface DispatcherConfig {

    long idleTimeout();

    URI serviceUri();

    DispatcherUserConfig dispatcherUser();

    String expiredConnectionsHandlerJobInterval();

    String refreshDispatcherTokenJobInterval();

    interface DispatcherUserConfig {
        String alias();

        String password();
    }
}
