package com.omgservers.service.operation.server;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithConverter;
import org.eclipse.microprofile.config.spi.Converter;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@ConfigMapping(prefix = "omgservers")
public interface ServiceConfig {

    UserConfig user();

    JwtConfig jwt();

    MigrationConfig migration();

    IndexConfig index();

    MasterConfig master();

    ShardConfig shard();

    ClientConfig client();

    DockerConfig docker();

    RuntimeConfig runtime();

    RegistryConfig registry();

    FeaturesConfig features();

    JobsConfig jobs();

    BootstrapConfig bootstrap();

    interface UserConfig {
        String alias();

        String password();
    }

    interface JwtConfig {
        String issuer();

        String x5c();
    }

    interface MigrationConfig {
        boolean enabled();

        int concurrency();
    }

    interface IndexConfig {

        boolean enabled();

        List<URI> shards();

        int slotsCount();
    }

    interface MasterConfig {
        URI uri();
    }

    interface ShardConfig {
        long id();

        URI uri();
    }

    interface ClientConfig {
        long inactiveInterval();

        URI dispatcherUri();
    }

    interface DockerConfig {
        boolean tlsVerify();

        @WithConverter(UserHomeConverter.class)
        String certPath();
    }

    interface RuntimeConfig {
        long inactiveInterval();

        long minLifetime();

        String dockerNetwork();

        URI serviceUri();

        URI dispatcherUri();

        long defaultCpuLimit();

        long defaultMemoryLimit();
    }

    interface RegistryConfig {
        URI uri();
    }

    interface FeaturesConfig {
    }

    interface JobsConfig {
        JobsJobConfig eventHandler();

        JobsJobConfig scheduler();

        JobsJobConfig bootstrap();
    }

    interface JobsJobConfig {
        boolean enabled();
    }

    interface BootstrapConfig {

        boolean enabled();

        BootstrapDefaultUserConfig adminUser();

        BootstrapDefaultUserConfig supportUser();

        BootstrapDefaultUserConfig serviceUser();

        BootstrapDefaultPoolConfig defaultPool();
    }

    interface BootstrapDefaultUserConfig {
        String alias();

        Optional<String> password();
    }

    interface BootstrapDefaultPoolConfig {
        boolean enabled();

        List<BootstrapDefaultPoolServerConfig> servers();
    }

    interface BootstrapDefaultPoolServerConfig {

        URI serverUri();

        URI dockerDaemonUri();

        int cpuCount();

        int memorySize();

        int maxContainers();
    }

    class UserHomeConverter implements Converter<String> {
        @Override
        public String convert(String path) throws IllegalArgumentException, NullPointerException {
            return path.replaceFirst("^~", System.getProperty("user.home"));
        }
    }
}
