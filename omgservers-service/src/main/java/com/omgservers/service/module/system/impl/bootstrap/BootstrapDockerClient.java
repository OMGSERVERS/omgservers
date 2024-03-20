package com.omgservers.service.module.system.impl.bootstrap;

import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import com.omgservers.service.configuration.ServicePriorityConfiguration;
import com.omgservers.service.module.system.impl.component.dockerClient.DockerClientContainer;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BootstrapDockerClient {

    final GetConfigOperation getConfigOperation;

    final DockerClientContainer dockerClientContainer;

    @WithSpan
    void startup(@Observes @Priority(ServicePriorityConfiguration.START_UP_DOCKER_CLIENT_PRIORITY) StartupEvent event) {
        final var disableDocker = getConfigOperation.getServiceConfig().disableDocker();
        if (disableDocker) {
            log.warn("Docker integration was disabled, skip operation");
        } else {

            final var dockerHost = getConfigOperation.getServiceConfig().dockerHost();
            final var config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .withDockerHost(dockerHost)
                    .build();

            final var httpClient = new ZerodepDockerHttpClient.Builder()
                    .dockerHost(config.getDockerHost())
                    .sslConfig(config.getSSLConfig())
                    .build();

            final var dockerClient = DockerClientImpl.getInstance(config, httpClient);
            dockerClientContainer.set(dockerClient);

            final var pong = dockerClient.pingCmd().exec();
            log.info("Ping docker, pong={}", pong);

            final var networks = dockerClient.listNetworksCmd().exec().stream()
                    .map(Network::getName).toList();
            log.info("Docker networks, networks={}", networks);

            // TODO: check network list
        }
    }
}
