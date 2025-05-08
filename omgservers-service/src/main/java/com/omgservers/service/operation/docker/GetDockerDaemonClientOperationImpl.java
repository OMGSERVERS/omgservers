package com.omgservers.service.operation.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetDockerDaemonClientOperationImpl implements GetDockerDaemonClientOperation {

    final GetServiceConfigOperation getServiceConfigOperation;

    final Map<URI, DockerClient> cache;

    GetDockerDaemonClientOperationImpl(final GetServiceConfigOperation getServiceConfigOperation) {
        this.getServiceConfigOperation = getServiceConfigOperation;
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized DockerClient execute(final URI uri) {
        if (!cache.containsKey(uri)) {
            final var tlsVerify = getServiceConfigOperation.getServiceConfig().docker().tlsVerify();
            final var certPath = getServiceConfigOperation.getServiceConfig().docker().certPath();

            final var config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .withDockerHost(uri.toString())
                    .withDockerTlsVerify(tlsVerify)
                    .withDockerCertPath(certPath)
                    .withRegistryUrl(getServiceConfigOperation.getServiceConfig().registry().uri().toString())
                    .withRegistryUsername(getServiceConfigOperation.getServiceConfig().user().alias())
                    .withRegistryPassword(getServiceConfigOperation.getServiceConfig().user().password())
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
}
