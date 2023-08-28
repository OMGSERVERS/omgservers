package com.omgservers.test.operations.getConfigOperation;

import io.smallrye.config.ConfigMapping;

import java.net.URI;
import java.util.Map;

@ConfigMapping(prefix = "omgservers.integrationtest")
public interface PlatformIntegrationTestConfig {

    Tester tester();

    Environment environment();

    interface Tester {
        Integer requestsConcurrency();

        String serviceUsername();

        String servicePassword();

        String adminUsername();

        String adminPassword();
    }

    interface Environment {
        String indexName();

        Map<String, Server> servers();
    }

    interface Server {
        URI internalAddress();

        URI externalAddress();

        String serviceUsername();

        String servicePassword();
    }
}