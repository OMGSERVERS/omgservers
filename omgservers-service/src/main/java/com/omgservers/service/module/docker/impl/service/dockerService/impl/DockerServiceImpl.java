package com.omgservers.service.module.docker.impl.service.dockerService.impl;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.docker.StopDockerContainerResponse;
import com.omgservers.service.module.docker.impl.operation.DockerModuleClient;
import com.omgservers.service.module.docker.impl.operation.GetDockerModuleClientOperation;
import com.omgservers.service.module.docker.impl.service.dockerService.DockerService;
import com.omgservers.service.module.docker.impl.service.dockerService.impl.method.StartDockerContainerMethod;
import com.omgservers.service.module.docker.impl.service.dockerService.impl.method.StopDockerContainerMethod;
import com.omgservers.service.module.docker.impl.service.webService.impl.api.DockerApi;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DockerServiceImpl implements DockerService {

    final StartDockerContainerMethod startDockerContainerMethod;
    final StopDockerContainerMethod stopDockerContainerMethod;

    final GetDockerModuleClientOperation getDockerModuleClientOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<StartDockerContainerResponse> startDockerContainer(@Valid final StartDockerContainerRequest request) {
        final var poolServer = request.getPoolServer();
        return handleRequest(poolServer,
                request,
                startDockerContainerMethod::execute,
                DockerApi::startDockerContainer);
    }

    @Override
    public Uni<StopDockerContainerResponse> stopDockerContainer(@Valid final StopDockerContainerRequest request) {
        final var poolServer = request.getPoolServer();
        return handleRequest(poolServer,
                request,
                stopDockerContainerMethod::execute,
                DockerApi::stopDockerContainer);
    }

    <R, T> Uni<R> handleRequest(final PoolServerModel poolServer,
                                final T request,
                                final Function<T, Uni<R>> execute,
                                final BiFunction<DockerModuleClient, T, Uni<R>> route) {
        return switch (poolServer.getQualifier()) {
            case DOCKER_HOST -> {
                final var poolServerUri = poolServer.getConfig().getServerUri();
                final var thisServerUri = getConfigOperation.getServiceConfig().index().serverUri();
                if (poolServerUri.equals(thisServerUri)) {
                    yield execute.apply(request);
                } else {
                    final var client = getDockerModuleClientOperation.getClient(poolServerUri);
                    yield route.apply(client, request);
                }
            }
        };
    }
}
