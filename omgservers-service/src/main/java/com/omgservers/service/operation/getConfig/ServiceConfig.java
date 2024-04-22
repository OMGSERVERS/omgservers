package com.omgservers.service.operation.getConfig;

import io.smallrye.config.ConfigMapping;

import java.net.URI;
import java.util.List;

@ConfigMapping(prefix = "omgservers")
public interface ServiceConfig {

    GeneratorConfig generator();

    IndexConfig index();

    MigrationConfig migration();

    BootstrapConfig bootstrap();

    RelayJobConfig relayJob();

    ClientsConfig clients();

    WorkersConfig workers();

    BuilderConfig builder();

    interface GeneratorConfig {
        long datacenterId();

        long instanceId();
    }

    interface MigrationConfig {
        boolean enabled();

        int concurrency();
    }

    interface IndexConfig {
        String name();

        int shardCount();

        URI serverUri();
    }

    interface RelayJobConfig {
        boolean enabled();
    }

    interface ClientsConfig {
        long tokenLifetime();

        long inactiveInterval();
    }

    interface WorkersConfig {
        long inactiveInterval();

        String dockerImage();

        String dockerNetwork();

        URI serviceUri();
    }

    interface BootstrapConfig {
        long rootId();

        BootstrapIndexConfig index();

        BootstrapLocalHostConfig localHost();
    }

    interface BootstrapIndexConfig {
        boolean enabled();

        List<URI> servers();
    }

    interface BootstrapLocalHostConfig {
        boolean enabled();

        URI uri();

        int cpuCount();

        int memorySize();

        int maxContainers();
    }

    interface BuilderConfig {
        URI uri();

        String userId();

        String userToken();
    }
}
