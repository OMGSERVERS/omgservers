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

    String serviceUsername();

    String servicePassword();

    String adminUsername();

    String adminPassword();

    String indexName();

    List<URI> addresses();

    int shardCount();

    boolean bootstrapService();

    Integer migrationConcurrency();

    boolean disableMigration();

    long tokenLifetime();

    long inactiveInterval();

    boolean disableRelayJob();

    String workersImage();

    String workersNetwork();

    boolean disableDocker();

    String dockerHost();
}
