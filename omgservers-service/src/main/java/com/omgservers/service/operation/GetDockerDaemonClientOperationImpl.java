package com.omgservers.service.operation;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetDockerDaemonClientOperationImpl implements GetDockerDaemonClientOperation {

    final GetConfigOperation getConfigOperation;

    final Map<URI, DockerClient> cache;

    GetDockerDaemonClientOperationImpl(final GetConfigOperation getConfigOperation) {
        this.getConfigOperation = getConfigOperation;
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized DockerClient getClient(final URI uri) {
        if (!cache.containsKey(uri)) {
            final var tlsVerify = getConfigOperation.getServiceConfig().docker().tlsVerify();
            final var certPath = getConfigOperation.getServiceConfig().docker().certPath();

            final var config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .withDockerHost(uri.toString())
                    .withDockerTlsVerify(tlsVerify)
                    .withDockerCertPath(certPath)
                    .withRegistryUrl(getConfigOperation.getServiceConfig().registry().uri().toString())
                    .withRegistryUsername(getConfigOperation.getServiceConfig().bootstrap().serviceUser().alias())
                    .withRegistryPassword(getConfigOperation.getServiceConfig().bootstrap().serviceUser().password())
                    .build();

            final var httpClient = new ZerodepDockerHttpClient.Builder()
                    .dockerHost(config.getDockerHost())
                    .sslConfig(config.getSSLConfig())
                    // TODO: move out to the configuration
                    .connectionTimeout(Duration.ofSeconds(16))
                    .responseTimeout(Duration.ofSeconds(32))
                    .build();

            final var dockerClient = DockerClientImpl.getInstance(config, httpClient);

            log.info("A docker client for the {} was created", uri);

            cache.put(uri, dockerClient);
        }
        return cache.get(uri);
    }

    @Override
    public Boolean hasCacheFor(final URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException("uri is null");
        }

        return cache.containsKey(uri);
    }

    @Override
    public Integer sizeOfCache() {
        return cache.size();
    }
}
