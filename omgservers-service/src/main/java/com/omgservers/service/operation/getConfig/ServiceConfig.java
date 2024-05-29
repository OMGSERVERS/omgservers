package com.omgservers.service.operation.getConfig;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithConverter;
import org.eclipse.microprofile.config.spi.Converter;

import java.net.URI;
import java.util.List;

@ConfigMapping(prefix = "omgservers")
public interface ServiceConfig {

    DefaultsConfig defaults();

    GeneratorConfig generator();

    IndexConfig index();

    MigrationConfig migration();

    BootstrapConfig bootstrap();

    RelayJobConfig relayJob();

    ClientsConfig clients();

    DockerConfig docker();

    WorkersConfig workers();

    BuilderConfig builder();

    interface DefaultsConfig {
        long poolId();
    }

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

    interface DockerConfig {
        boolean tlsVerify();

        @WithConverter(UserHomeConverter.class)
        String certPath();
    }

    interface WorkersConfig {
        long inactiveInterval();

        String dockerImage();

        String dockerNetwork();

        URI serviceUri();
    }

    interface BootstrapConfig {
        BootstrapIndexConfig index();

        BootstrapAdminConfig admin();

        BootstrapDefaultPoolConfig defaultPool();

        BootstrapDockerHostConfig dockerHost();
    }

    interface BootstrapIndexConfig {
        boolean enabled();

        List<URI> servers();
    }

    interface BootstrapAdminConfig {
        boolean enabled();

        long userId();

        String password();
    }

    interface BootstrapDefaultPoolConfig {
        boolean enabled();
    }

    interface BootstrapDockerHostConfig {
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

    class UserHomeConverter implements Converter<String> {
        @Override
        public String convert(String path) throws IllegalArgumentException, NullPointerException {
            return path.replaceFirst("^~", System.getProperty("user.home"));
        }
    }
}
