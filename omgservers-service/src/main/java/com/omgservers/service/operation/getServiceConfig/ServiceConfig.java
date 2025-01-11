package com.omgservers.service.operation.getServiceConfig;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithConverter;
import org.eclipse.microprofile.config.spi.Converter;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@ConfigMapping(prefix = "omgservers")
public interface ServiceConfig {

    ServerConfig server();

    IndexConfig index();

    InitializationConfig initialization();

    BootstrapConfig bootstrap();

    ClientsConfig clients();

    DockerClientConfig dockerClient();

    RuntimesConfig runtimes();

    BuilderConfig builder();

    RegistryConfig registry();

    interface ServerConfig {
        long instanceId();

        String jwtIssuer();

        String x5c();

        ServiceUserConfig serviceUser();
    }

    interface ServiceUserConfig {
        String alias();

        String password();
    }

    interface IndexConfig {
        URI serverUri();

        int shardCount();
    }

    interface InitializationDatabaseSchemaConfig {
        boolean enabled();

        int concurrency();
    }

    interface InitializationServerIndexConfig {
        boolean enabled();

        List<URI> servers();
    }

    interface InitializationRelayJobConfig {
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

        String dockerNetwork();

        long defaultCpuLimit();

        long defaultMemoryLimit();
    }

    interface InitializationConfig {
        InitializationDatabaseSchemaConfig databaseSchema();

        InitializationServerIndexConfig serverIndex();

        InitializationRelayJobConfig relayJob();

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

        String token();
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
