package com.omgservers.service.module.pool.impl.service.dockerService.impl.method;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.exception.DockerException;
import com.omgservers.schema.module.pool.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.pool.docker.StopDockerContainerResponse;
import com.omgservers.service.module.pool.impl.service.dockerService.impl.operation.GetDockerDaemonClientOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class StopDockerContainerMethodImpl implements StopDockerContainerMethod {

    final GetDockerDaemonClientOperation getDockerDaemonClientOperation;

    @Override
    public Uni<StopDockerContainerResponse> execute(final StopDockerContainerRequest request) {
        log.debug("Requested, {}", request);

        final var poolServer = request.getPoolServer();
        final var poolContainer = request.getPoolContainer();

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .map(voidItem -> {
                    final var containerName = poolContainer.getContainerName();
                    final var dockerDaemonUri = poolServer.getConfig().getDockerHostConfig().getDockerDaemonUri();
                    final var dockerClient = getDockerDaemonClientOperation.getClient(dockerDaemonUri);

                    stopDockerContainer(dockerDaemonUri, dockerClient, containerName);
                    // TODO: get final the container logs
                    removeDockerContainer(dockerDaemonUri, dockerClient, containerName);

                    return new StopDockerContainerResponse(Boolean.TRUE);
                });
    }

    Boolean stopDockerContainer(final URI dockerDaemonUri,
                                final DockerClient dockerClient,
                                final String containerName) {
        try {
            dockerClient.stopContainerCmd(containerName).exec();
            log.info("The container has been stopped, containerName={}, dockerDaemonUri={}",
                    containerName, dockerDaemonUri);
            return Boolean.TRUE;
        } catch (DockerException e) {
            log.warn("Failed to stop docker container, containerName={}, dockerDaemonUri={}, {}:{}",
                    containerName, dockerDaemonUri, e.getClass().getSimpleName(), e.getMessage());
            return Boolean.FALSE;
        }
    }

    Boolean removeDockerContainer(final URI dockerDaemonUri,
                                  final DockerClient dockerClient,
                                  final String containerName) {
        try {
            dockerClient.removeContainerCmd(containerName).exec();
            log.info("The container has been removed, containerName={}, dockerDaemonUri={}",
                    containerName, dockerDaemonUri);
            return Boolean.TRUE;
        } catch (DockerException e) {
            log.warn("Failed to remove docker container, containerName={}, dockerDaemonUri={}, {}:{}",
                    containerName, dockerDaemonUri, e.getClass().getSimpleName(), e.getMessage());
            return Boolean.FALSE;
        }
    }
}
