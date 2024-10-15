package com.omgservers.service.module.pool.impl.service.dockerService.impl.method;

import com.github.dockerjava.api.exception.DockerException;
import com.omgservers.schema.module.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.docker.StopDockerContainerResponse;
import com.omgservers.service.module.pool.impl.service.dockerService.impl.operation.GetDockerDaemonClientOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class StopDockerContainerMethodImpl implements StopDockerContainerMethod {

    final GetDockerDaemonClientOperation getDockerDaemonClientOperation;

    @Override
    public Uni<StopDockerContainerResponse> execute(final StopDockerContainerRequest request) {
        log.debug("Stop docker container, request={}", request);

        final var poolServer = request.getPoolServer();
        final var poolServerContainer = request.getPoolServerContainer();

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .map(voidItem -> {
                    final var containerName = poolServerContainer.getContainerName();
                    final var dockerDaemonUri = poolServer.getConfig().getDockerHostConfig().getDockerDaemonUri();
                    final var dockerClient = getDockerDaemonClientOperation.getClient(dockerDaemonUri);

                    try {
                        dockerClient.stopContainerCmd(containerName).exec();
                        log.info("The container has been stopped, containerName={}, dockerDaemonUri={}",
                                containerName, dockerDaemonUri);
                        // TODO: get final the container logs
                        dockerClient.removeContainerCmd(containerName).exec();
                        log.info("The container has been removed, containerName={}, dockerDaemonUri={}",
                                containerName, dockerDaemonUri);
                        return new StopDockerContainerResponse(Boolean.TRUE);
                    } catch (DockerException e) {
                        log.warn("Failed to stop docker container, containerName={}, dockerDaemonUri={}, {}:{}",
                                containerName, dockerDaemonUri, e.getClass().getSimpleName(), e.getMessage());
                        return new StopDockerContainerResponse(Boolean.FALSE);
                    }
                });
    }
}
