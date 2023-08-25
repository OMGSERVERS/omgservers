package com.omgservers.base.impl.operation.getConfigOperation;

import io.smallrye.config.ConfigMapping;

import java.net.URI;

@ConfigMapping(prefix = "omgservers.application")
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
