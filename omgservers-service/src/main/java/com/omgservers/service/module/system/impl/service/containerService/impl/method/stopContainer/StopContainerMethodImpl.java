package com.omgservers.service.module.system.impl.service.containerService.impl.method.stopContainer;

import com.omgservers.model.dto.system.StopContainerRequest;
import com.omgservers.model.dto.system.StopContainerResponse;
import com.omgservers.service.module.system.impl.component.DockerClientHolder;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class StopContainerMethodImpl implements StopContainerMethod {

    final DockerClientHolder dockerClientHolder;

    @Override
    public Uni<StopContainerResponse> stopContainer(final StopContainerRequest request) {
        log.debug("Stop container, request={}", request);

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> {
                    final var containerName = request.getId().toString();
                    final var dockerClient = dockerClientHolder.getDockerClient();

                    dockerClient.stopContainerCmd(containerName).exec();
                    if (request.getRemove()) {
                        dockerClient.removeContainerCmd(containerName).exec();
                    }
                })
                .replaceWith(new StopContainerResponse(false));
    }
}
