package com.omgservers.ctl.operation.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import com.omgservers.ctl.configuration.DockerConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.time.Duration;

@Slf4j
@ApplicationScoped
class CreateDockerDaemonClientOperationImpl implements CreateDockerDaemonClientOperation {

    @Override
    public synchronized DockerClient execute(final URI dockerDaemonUri,
                                             final URI registryUri,
                                             final String registryUsername,
                                             final String registryPassword) {

        final var config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(dockerDaemonUri.toString())
                .withRegistryUrl(registryUri.toString())
                .withRegistryUsername(registryUsername)
                .withRegistryPassword(registryPassword)
                .build();

        final var httpClient = new ZerodepDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .connectionTimeout(Duration.ofSeconds(DockerConfiguration.DEFAULT_CONNECT_TIMEOUT_SECONDS))
                .responseTimeout(Duration.ofSeconds(DockerConfiguration.DEFAULT_RESPONSE_TIMEOUT_SECONDS))
                .build();

        final var dockerClient = DockerClientImpl.getInstance(config, httpClient);

        log.info("Docker client to \"{}\" created", dockerDaemonUri);

        return dockerClient;
    }
}
