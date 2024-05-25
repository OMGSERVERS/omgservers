package com.omgservers.tester.operation.getConfig;

import io.smallrye.config.ConfigMapping;

import java.net.URI;

@ConfigMapping(prefix = "omgservers.tester")
public interface TesterConfig {

    URI serviceUri();

    AdminConfig admin();

    interface AdminConfig {
        long userId();

        String password();
    }
}
