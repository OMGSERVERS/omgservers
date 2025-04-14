package com.omgservers.service.shard.docker.impl.service.dockerService.impl.method;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.api.exception.NotModifiedException;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.docker.StopDockerContainerRequest;
import com.omgservers.schema.shard.docker.StopDockerContainerResponse;
import com.omgservers.service.operation.docker.GetDockerDaemonClientOperation;
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
    public Uni<StopDockerContainerResponse> execute(final ShardModel shardModel,
                                                    final StopDockerContainerRequest request) {
        log.trace("{}", request);

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

                    log.info("The docker container \"{}\" was stopped on the server \"{}\"",
                            containerName, dockerDaemonUri);

                    return new StopDockerContainerResponse(Boolean.TRUE);
                });
    }

    Boolean stopDockerContainer(final URI dockerDaemonUri,
                                final DockerClient dockerClient,
                                final String containerName) {
        try {
            dockerClient.stopContainerCmd(containerName).exec();
            log.debug("The container has been stopped, containerName={}, dockerDaemonUri={}",
                    containerName, dockerDaemonUri);
            return Boolean.TRUE;
        } catch (DockerException e) {
            if (e instanceof NotModifiedException) {
                log.debug("Failed to stop docker container \"{}\" on the server \"{}\", {}:{}",
                        containerName, dockerDaemonUri, e.getClass().getSimpleName(), e.getMessage());
            } else {
                log.warn("Failed to stop docker container \"{}\" on the server \"{}\", {}:{}",
                        containerName, dockerDaemonUri, e.getClass().getSimpleName(), e.getMessage());
            }

            return Boolean.FALSE;
        }
    }

    Boolean removeDockerContainer(final URI dockerDaemonUri,
                                  final DockerClient dockerClient,
                                  final String containerName) {
        try {
            dockerClient.removeContainerCmd(containerName).exec();
            log.debug("The container has been removed, containerName={}, dockerDaemonUri={}",
                    containerName, dockerDaemonUri);
            return Boolean.TRUE;
        } catch (DockerException e) {
            log.warn("Failed to remove docker container, containerName={}, dockerDaemonUri={}, {}:{}",
                    containerName, dockerDaemonUri, e.getClass().getSimpleName(), e.getMessage());
            return Boolean.FALSE;
        }
    }
}
