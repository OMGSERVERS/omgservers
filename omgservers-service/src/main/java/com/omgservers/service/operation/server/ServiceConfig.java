package com.omgservers.service.operation.server;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithConverter;
import org.eclipse.microprofile.config.spi.Converter;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@ConfigMapping(prefix = "omgservers")
public interface ServiceConfig {

    ServerConfig server();

    ClientsConfig clients();

    DockerClientConfig dockerClient();

    RuntimesConfig runtimes();

    BuilderConfig builder();

    RegistryConfig registry();

    FeatureFlagsConfig featureFlags();

    BootstrapConfig bootstrap();

    InitializationConfig initialization();

    interface ServerConfig {
        long id();

        long instanceId();

        String jwtIssuer();

        String x5c();

        ServiceUserConfig serviceUser();

        URI uri();

        int shardCount();
    }

    interface ServiceUserConfig {
        String alias();

        String password();
    }

    interface InitializationDatabaseSchemaConfig {
        boolean enabled();

        int concurrency();
    }

    interface InitializationServerIndexConfig {
        boolean enabled();

        List<URI> servers();
    }

    interface InitializationEventHandlerJobConfig {
        boolean enabled();

        String interval();
    }

    interface InitializationSchedulerJobConfig {
        boolean enabled();

        String interval();
    }

    interface InitializationBootstrapJobConfig {
        boolean enabled();

        String interval();
    }

    interface ClientsConfig {
        long tokenLifetime();

        long inactiveInterval();
    }

    interface DockerClientConfig {
        boolean tlsVerify();

        @WithConverter(UserHomeConverter.class)
        String certPath();
    }

    interface RuntimesConfig {
        long inactiveInterval();

        RuntimesLobbyConfig lobby();

        String dockerNetwork();

        RuntimesOverridingConfig overriding();

        long defaultCpuLimit();

        long defaultMemoryLimit();
    }

    interface RuntimesOverridingConfig {
        boolean enabled();

        URI uri();
    }

    interface RuntimesLobbyConfig {
        long minLifetime();
    }

    interface InitializationConfig {
        InitializationDatabaseSchemaConfig databaseSchema();

        InitializationServerIndexConfig serverIndex();

        InitializationEventHandlerJobConfig eventHandlerJob();

        InitializationSchedulerJobConfig schedulerJob();

        InitializationBootstrapJobConfig bootstrapJob();
    }

    interface BootstrapConfig {

        boolean enabled();

        BootstrapUserPasswordConfig adminUser();

        BootstrapUserPasswordConfig supportUser();

        BootstrapUserPasswordConfig registryUser();

        BootstrapUserPasswordConfig builderUser();

        BootstrapUserPasswordConfig serviceUser();

        BootstrapUserPasswordConfig dispatcherUser();

        BootstrapDefaultPoolConfig defaultPool();
    }

    interface BootstrapUserPasswordConfig {
        String alias();

        Optional<String> password();
    }

    interface BootstrapDefaultPoolConfig {
        boolean enabled();

        List<BootstrapDockerHostConfig> dockerHosts();
    }

    interface BootstrapDockerHostConfig {

        URI dockerDaemonUri();

        int cpuCount();

        int memorySize();

        int maxContainers();
    }

    interface BuilderConfig {
        URI uri();

        String username();

        Optional<String> token();
    }

    interface RegistryConfig {
        URI uri();
    }

    interface FeatureFlagsConfig {
        boolean builderEnabled();
    }

    class UserHomeConverter implements Converter<String> {
        @Override
        public String convert(String path) throws IllegalArgumentException, NullPointerException {
            return path.replaceFirst("^~", System.getProperty("user.home"));
        }
    }
}
