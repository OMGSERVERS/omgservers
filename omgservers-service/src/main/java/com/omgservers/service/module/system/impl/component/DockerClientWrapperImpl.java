package com.omgservers.service.module.system.impl.component;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
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
class DockerClientWrapperImpl implements DockerClientWrapper {

    final DockerClient dockerClient;

    public DockerClientWrapperImpl() {
        final var config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();

        final var httpClient = new ZerodepDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();

        dockerClient = DockerClientImpl.getInstance(config, httpClient);

        final var pong = dockerClient.pingCmd().exec();
        log.info("Ping docker, pong={}", pong);

        final var networks = dockerClient.listNetworksCmd().exec();
        log.info("Docker networks, networks={}", networks);
    }

    void startup(@Observes @Priority(1000) StartupEvent event) {
        log.info("Docker client was initialized");
    }

    @Override
    public DockerClient getDockerClient() {
        return dockerClient;
    }
}
