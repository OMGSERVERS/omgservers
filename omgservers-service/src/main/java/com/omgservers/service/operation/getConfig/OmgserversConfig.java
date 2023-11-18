package com.omgservers.service.operation.getConfig;

import io.smallrye.config.ConfigMapping;

import java.net.URI;

@ConfigMapping(prefix = "omgservers")
public interface OmgserversConfig {
    long datacenterId();

    long nodeId();

    URI serviceUri();

    String serviceUsername();

    String servicePassword();

    String adminUsername();

    String adminPasswordHash();

    String indexName();

    int shardCount();

    Integer migrationConcurrency();

    boolean disableMigration();

    long tokenLifetime();

    boolean disableRelay();

    boolean disableEventHandler();

    Integer eventHandlerConcurrency();

    boolean standalone();

    String workerImage();

    String dockerNetwork();
}
