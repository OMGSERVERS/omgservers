package com.omgservers.application.operation.getConfigOperation;

import io.smallrye.config.ConfigMapping;

import java.net.URI;

@ConfigMapping(prefix = "omgservers.application")
public interface ServiceApplicationConfig {
    URI serverUri();

    String serviceUsername();

    String servicePassword();

    String adminUsername();

    String adminPasswordHash();

    String indexName();

    Integer shardCount();

    Integer migrationConcurrency();

    Boolean disableMigration();

    Long tokenLifetime();

    Boolean disableConsumers();

    Integer consumerCount();

    String consumerQueue();

    Integer producerCount();

    String producerQueue();

    Boolean disableDispatcher();

    Integer dispatcherLimit();

    Boolean standalone();
}
