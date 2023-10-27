package com.omgservers.module.system.impl.component.dockerClientWrapper;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

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
                .maxConnections(4)
                .connectionTimeout(Duration.ofSeconds(4))
                .responseTimeout(Duration.ofSeconds(8))
                .build();

        dockerClient = DockerClientImpl.getInstance(config, httpClient);
    }

    @Override
    public DockerClient getDockerClient() {
        return dockerClient;
    }
}
