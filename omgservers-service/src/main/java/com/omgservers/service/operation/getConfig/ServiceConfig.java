package com.omgservers.service.operation.getConfig;

import io.smallrye.config.ConfigMapping;

import java.net.URI;
import java.util.List;

@ConfigMapping(prefix = "omgservers")
public interface ServiceConfig {
    long datacenterId();

    long instanceId();

    URI externalUri();

    URI internalUri();

    String indexName();

    List<URI> addresses();

    int shardCount();

    boolean bootstrapService();

    Integer migrationConcurrency();

    boolean disableMigration();

    long clientTokenLifetime();

    long clientInactiveInterval();

    boolean disableRelayJob();

    long workersInactiveInterval();

    String workersDockerImage();

    String workersDockerNetwork();

    boolean disableDocker();

    String dockerHost();
}
