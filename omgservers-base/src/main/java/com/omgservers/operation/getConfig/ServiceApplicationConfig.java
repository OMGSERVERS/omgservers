package com.omgservers.operation.getConfig;

import io.smallrye.config.ConfigMapping;

import java.net.URI;

@ConfigMapping(prefix = "omgservers")
public interface ServiceApplicationConfig {
    long datacenterId();

    long nodeId();

    URI serverUri();

    String serviceUsername();

    String servicePassword();

    String adminUsername();

    String adminPasswordHash();

    String indexName();

    int shardCount();

    Integer migrationConcurrency();

    boolean disableMigration();

    long tokenLifetime();

    boolean disableDispatcher();

    int dispatcherCount();

    int postponeInterval();

    boolean standalone();
}
