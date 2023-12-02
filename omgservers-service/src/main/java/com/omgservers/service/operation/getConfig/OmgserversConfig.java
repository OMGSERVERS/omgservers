package com.omgservers.service.operation.getConfig;

import io.smallrye.config.ConfigMapping;

import java.net.URI;
import java.util.List;

@ConfigMapping(prefix = "omgservers")
public interface OmgserversConfig {
    long datacenterId();

    long nodeId();

    URI externalUri();

    URI internalUri();

    String serviceUsername();

    String servicePassword();

    String adminUsername();

    String adminPasswordHash();

    String indexName();

    List<URI> addresses();

    int shardCount();

    boolean bootstrapService();

    Integer migrationConcurrency();

    boolean disableMigration();

    long tokenLifetime();

    boolean disableRelay();

    String workersImage();

    String workersNetwork();

    boolean disableDocker();

    String dockerHost();
}
