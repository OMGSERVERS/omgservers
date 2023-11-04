package com.omgservers.service.module.system.impl.service.containerService.impl.method.runContainer;

import com.github.dockerjava.api.model.HostConfig;
import com.omgservers.model.dto.system.RunContainerRequest;
import com.omgservers.model.dto.system.RunContainerResponse;
import com.omgservers.service.module.system.impl.component.dockerClientWrapper.DockerClientWrapper;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RunContainerMethodImpl implements RunContainerMethod {

    final DockerClientWrapper dockerClientWrapper;

    @Override
    public Uni<RunContainerResponse> runContainer(final RunContainerRequest request) {
        final var container = request.getContainer();
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> {
                    final var image = container.getImage();
                    final var name = container.getId().toString();
                    final var environment = container.getConfig().getEnvironment().entrySet().stream()
                            .map(entry -> entry.getKey() + "=" + entry.getValue())
                            .toList();

                    final var dockerClient = dockerClientWrapper.getDockerClient();

                    dockerClient.createContainerCmd(image)
                            .withName(name)
                            .withEnv(environment)
                            .withHostConfig(HostConfig.newHostConfig().withNetworkMode("host"))
                            .exec();

                    dockerClient.startContainerCmd(name).exec();
                })
                .replaceWith(RunContainerResponse::new);
    }
}
