package com.omgservers.service.module.docker.impl.service.dockerService.impl.method;

import com.github.dockerjava.api.exception.DockerException;
import com.omgservers.schema.module.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.docker.StopDockerContainerResponse;
import com.omgservers.service.module.docker.impl.operation.GetDockerDaemonClientOperation;
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
                        final var stopContainerResponse = dockerClient.stopContainerCmd(containerName).exec();
                        log.info("Stop container, response={}", stopContainerResponse);
                        final var removeContainerResponse = dockerClient.removeContainerCmd(containerName).exec();
                        log.info("Remove container, response={}", removeContainerResponse);
                        return new StopDockerContainerResponse(Boolean.TRUE);
                    } catch (DockerException e) {
                        log.warn("Failed to stop docker container, {}", e.getMessage());
                        return new StopDockerContainerResponse(Boolean.FALSE);
                    }
                });
    }
}
