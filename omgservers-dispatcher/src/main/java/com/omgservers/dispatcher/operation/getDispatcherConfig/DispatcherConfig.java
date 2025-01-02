package com.omgservers.dispatcher.operation.getDispatcherConfig;

import io.smallrye.config.ConfigMapping;

import java.net.URI;

@ConfigMapping(prefix = "omgservers")
public interface DispatcherConfig {

    long idleTimeout();

    URI serviceUri();

    String jobInterval();
}
