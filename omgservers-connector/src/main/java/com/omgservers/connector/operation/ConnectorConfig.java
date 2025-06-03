package com.omgservers.connector.operation;

import io.smallrye.config.ConfigMapping;

import java.net.URI;

@ConfigMapping(prefix = "omgservers")
public interface ConnectorConfig {

    UserConfig user();

    URI serviceUri();

    long idleConnectionTimeout();

    interface UserConfig {
        String alias();

        String password();
    }
}
