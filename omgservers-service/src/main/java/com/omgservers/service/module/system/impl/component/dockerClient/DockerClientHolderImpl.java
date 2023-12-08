package com.omgservers.service.module.system.impl.component.dockerClient;

import com.github.dockerjava.api.DockerClient;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DockerClientHolderImpl implements DockerClientHolder {

    final GetConfigOperation getConfigOperation;

    final DockerClientContainer dockerClientContainer;

    @Override
    public DockerClient getDockerClient() {
        final var dockerClient = dockerClientContainer.get();
        if (Objects.isNull(dockerClient)) {
            throw new ServerSideConflictException("Docker client is not ready yet");
        }

        return dockerClient;
    }
}
