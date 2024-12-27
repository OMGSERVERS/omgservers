package com.omgservers.tester.operation.getDockerClient;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import com.omgservers.tester.operation.getConfig.GetConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetDockerClientOperationImpl implements GetDockerClientOperation {

    final GetConfigOperation getConfigOperation;
    final Map<URI, DockerClient> cache;

    GetDockerClientOperationImpl(final GetConfigOperation getConfigOperation) {
        this.getConfigOperation = getConfigOperation;
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public DockerClient getDockerClient(final Long registryUserId,
                                        final String registryUserPassword) {
        final var config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withRegistryUrl(getConfigOperation.getConfig().registry().url())
                .withRegistryUsername(registryUserId.toString())
                .withRegistryPassword(registryUserPassword)
                .build();

        final var httpClient = new ZerodepDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .build();

        final var dockerClient = DockerClientImpl.getInstance(config, httpClient);

        log.info("A docker client was created, registryUserId={}", registryUserId);

        return dockerClient;
    }
}
