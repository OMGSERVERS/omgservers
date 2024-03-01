package com.omgservers.service.module.system.impl.service.containerService.impl.method.runContainer;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.omgservers.model.dto.system.RunContainerRequest;
import com.omgservers.model.dto.system.RunContainerResponse;
import com.omgservers.service.module.system.impl.component.dockerClient.DockerClientHolder;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
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

    final GetConfigOperation getConfigOperation;

    final DockerClientHolder dockerClientHolder;

    @Override
    public Uni<RunContainerResponse> runContainer(final RunContainerRequest request) {
        log.debug("Run container, request={}", request);

        final var container = request.getContainer();
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> {
                    final var image = container.getImage();
                    final var name = container.getId().toString();
                    final var environment = container.getConfig().getEnvironment().entrySet().stream()
                            .map(entry -> entry.getKey() + "=" + entry.getValue())
                            .toList();

                    final var dockerClient = dockerClientHolder.getDockerClient();
                    final var dockerNetwork = getConfigOperation.getServiceConfig().workersDockerNetwork();

                    final var createContainerResponse = dockerClient.createContainerCmd(image)
                            .withName(name)
                            .withEnv(environment)
                            .withExposedPorts(ExposedPort.parse("8080/tcp"))
                            .withHostConfig(HostConfig.newHostConfig()
                                    .withNetworkMode(dockerNetwork)
                                    .withPortBindings(PortBinding.parse(":8080")))
                            .exec();
                    log.info("Create container, response={}", createContainerResponse);

                    final var inspectContainerResponse = dockerClient.inspectContainerCmd(name)
                            .exec();
                    log.info("Inspect container, response={}", inspectContainerResponse);

                    log.info("Start container, id={}, qualifier={}, entity={}",
                            container.getId(),
                            container.getQualifier(),
                            container.getEntityId());

                    final var startContainerResponse = dockerClient.startContainerCmd(name)
                            .exec();

                    log.info("Start container, response={}", startContainerResponse);
                })
                .replaceWith(RunContainerResponse::new);
    }
}
