package com.omgservers.service.server.operation.getConfig;

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

    JwtConfig jwt();

    BootstrapConfig bootstrap();

    ClientsConfig clients();

    DockerConfig docker();

    WorkersConfig workers();

    BuilderConfig builder();

    RegistryConfig registry();

    interface DefaultsConfig {
        long indexId();

        long rootId();

        long poolId();

        long adminId();

        long supportId();

        long registryUserId();

        long builderUserId();

        long serviceUserId();
    }

    interface GeneratorConfig {
        long datacenterId();

        long instanceId();
    }

    interface IndexConfig {
        int shardCount();

        URI serverUri();
    }

    interface JwtConfig {
        String issuer();

        String x5c();
    }

    interface BootstrapRelayJobConfig {
        boolean enabled();

        String interval();
    }

    interface BootstrapSchedulerJobConfig {
        boolean enabled();

        String interval();
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

        String dockerNetwork();

        URI serviceUri();

        int defaultCpuLimit();

        int defaultMemoryLimit();
    }

    interface BootstrapConfig {
        BootstrapSchemaConfig schema();

        BootstrapIndexConfig index();

        BootstrapDefaultUserConfig admin();

        BootstrapDefaultUserConfig support();

        BootstrapDefaultUserConfig registryUser();

        BootstrapDefaultUserConfig builderUser();

        BootstrapDefaultUserConfig serviceUser();

        BootstrapRootConfig root();

        BootstrapDefaultPoolConfig defaultPool();

        BootstrapDockerHostConfig dockerHost();

        BootstrapRelayJobConfig relayJob();

        BootstrapSchedulerJobConfig schedulerJob();
    }

    interface BootstrapSchemaConfig {
        boolean enabled();

        int concurrency();
    }

    interface BootstrapIndexConfig {
        boolean enabled();

        List<URI> servers();
    }

    interface BootstrapDefaultUserConfig {
        boolean enabled();

        String password();
    }

    interface BootstrapRootConfig {
        boolean enabled();
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

    interface RegistryConfig {
        URI uri();
    }

    class UserHomeConverter implements Converter<String> {
        @Override
        public String convert(String path) throws IllegalArgumentException, NullPointerException {
            return path.replaceFirst("^~", System.getProperty("user.home"));
        }
    }
}
