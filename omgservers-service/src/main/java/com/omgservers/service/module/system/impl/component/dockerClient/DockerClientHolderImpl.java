package com.omgservers.service.module.system.impl.component.dockerClient;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
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
