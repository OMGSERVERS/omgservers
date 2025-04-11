package com.omgservers.dispatcher.operation;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "omgservers")
public interface DispatcherConfig {

    String idleConnectionsHandlerJobInterval();

    long idleConnectionsTimeout();
}
