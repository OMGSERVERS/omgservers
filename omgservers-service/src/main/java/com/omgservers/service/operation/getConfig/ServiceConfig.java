package com.omgservers.service.operation.getConfig;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithConverter;
import org.eclipse.microprofile.config.spi.Converter;

import java.net.URI;
import java.util.List;

@ConfigMapping(prefix = "omgservers")
public interface ServiceConfig {

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

        long defaultCpuLimit();

        long defaultMemoryLimit();
    }

    interface InitializationConfig {
        InitializationDatabaseSchemaConfig databaseSchema();

        InitializationServerIndexConfig serverIndex();

        InitializationRelayJobConfig relayJob();

        InitializationSchedulerJobConfig schedulerJob();

        InitializationDispatcherJobConfig dispatcherJob();

        InitializationDispatcherJobConfig bootstrapJob();
    }

    interface BootstrapConfig {

        boolean enabled();

        BootstrapUserPasswordConfig adminUser();

        BootstrapUserPasswordConfig supportUser();

        BootstrapUserPasswordConfig registryUser();

        BootstrapUserPasswordConfig builderUser();

        BootstrapUserPasswordConfig serviceUser();

        BootstrapDefaultPoolConfig defaultPool();
    }

    interface BootstrapUserPasswordConfig {
        String alias();

        String password();
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
