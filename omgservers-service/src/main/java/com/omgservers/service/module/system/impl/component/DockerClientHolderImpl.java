package com.omgservers.service.module.system.impl.component;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DockerClientHolderImpl implements DockerClientHolder {

    final GetConfigOperation getConfigOperation;

    final DockerClientContainer dockerClientContainer;

    @Override
    public DockerClient getDockerClient() {
        final var dockerClient = dockerClientContainer.get();
        if (Objects.isNull(dockerClient)) {
            throw new ServerSideConflictException("Docker client is not ready yet");
        }

        return dockerClient;
    }

    @WithSpan
    void startup(@Observes @Priority(1000) StartupEvent event) {
        final var dockerHost = getConfigOperation.getConfig().dockerHost();
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
