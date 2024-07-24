package com.omgservers.router.operation.getConfig;

import io.smallrye.config.ConfigMapping;

import java.net.URI;

@ConfigMapping(prefix = "omgservers")
public interface RouterConfig {
    URI serviceUri();

    long userId();

    String password();
}
