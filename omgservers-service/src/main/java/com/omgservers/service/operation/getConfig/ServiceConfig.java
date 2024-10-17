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

    JwtConfig jwt();

    InitializationConfig initialization();

    BootstrapConfig bootstrap();

    DispatcherConfig dispatcher();

    ClientsConfig clients();

    DockerConfig docker();

    RuntimesConfig runtimes();

    BuilderConfig builder();

    RegistryConfig registry();

    interface DefaultsConfig {
        long indexId();

        long rootId();

        long poolId();

        long adminUserId();

        long supportUserId();

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

    interface InitializationDatabaseSchemaConfig {
        boolean enabled();

        int concurrency();
    }

    interface InitializationRelayJobConfig {
        boolean enabled();

        String interval();
    }

    interface InitializationSchedulerJobConfig {
        boolean enabled();

        String interval();
    }

    interface InitializationDispatcherJobConfig {
        boolean enabled();

        String interval();
    }

    interface DispatcherConfig {
        long idleTimeout();
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

    interface RuntimesConfig {
        long inactiveInterval();

        String dockerNetwork();

        int defaultCpuLimit();

        int defaultMemoryLimit();
    }

    interface InitializationConfig {
        InitializationDatabaseSchemaConfig databaseSchema();

        InitializationRelayJobConfig relayJob();

        InitializationSchedulerJobConfig schedulerJob();

        InitializationDispatcherJobConfig dispatcherJob();
    }

    interface BootstrapConfig {
        BootstrapIndexConfig index();

        BootstrapDefaultUsersConfig defaultUsers();

        BootstrapUserPasswordConfig adminUser();

        BootstrapUserPasswordConfig supportUser();

        BootstrapUserPasswordConfig registryUser();

        BootstrapUserPasswordConfig builderUser();

        BootstrapUserPasswordConfig serviceUser();

        BootstrapRootConfig root();

        BootstrapDefaultPoolConfig defaultPool();

        BootstrapDockerHostConfig dockerHost();
    }

    interface BootstrapIndexConfig {
        boolean enabled();

        List<URI> servers();
    }

    interface BootstrapDefaultUsersConfig {
        boolean enabled();
    }

    interface BootstrapUserPasswordConfig {
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

        URI serviceUri();

        URI dockerDaemonUri();

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
