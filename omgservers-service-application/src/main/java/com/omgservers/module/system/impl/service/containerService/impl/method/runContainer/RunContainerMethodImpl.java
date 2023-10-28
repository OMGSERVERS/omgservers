package com.omgservers.module.system.impl.service.containerService.impl.method.runContainer;

import com.omgservers.model.dto.internal.RunContainerRequest;
import com.omgservers.model.dto.internal.RunContainerResponse;
import com.omgservers.module.system.impl.component.dockerClientWrapper.DockerClientWrapper;
import io.smallrye.mutiny.Uni;
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
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var containers = dockerClientWrapper.getDockerClient().listContainersCmd().exec();
                    log.info("Containers, {}", containers);
                })
                .replaceWith(RunContainerResponse::new);
    }
}
