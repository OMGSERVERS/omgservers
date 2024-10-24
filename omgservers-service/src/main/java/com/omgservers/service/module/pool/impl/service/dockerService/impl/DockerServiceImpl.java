package com.omgservers.service.module.pool.impl.service.dockerService.impl;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.module.pool.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.pool.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.pool.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.pool.docker.StopDockerContainerResponse;
import com.omgservers.service.module.pool.impl.operation.getPoolModuleClient.GetPoolModuleClientOperation;
import com.omgservers.service.module.pool.impl.operation.getPoolModuleClient.PoolModuleClient;
import com.omgservers.service.module.pool.impl.service.dockerService.DockerService;
import com.omgservers.service.module.pool.impl.service.dockerService.impl.method.StartDockerContainerMethod;
import com.omgservers.service.module.pool.impl.service.dockerService.impl.method.StopDockerContainerMethod;
import com.omgservers.service.module.pool.impl.service.webService.impl.api.PoolApi;
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

    final GetPoolModuleClientOperation getPoolModuleClientOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<StartDockerContainerResponse> execute(@Valid final StartDockerContainerRequest request) {
        final var poolServer = request.getPoolServer();
        return handleRequest(poolServer,
                request,
                startDockerContainerMethod::execute,
                PoolApi::execute);
    }

    @Override
    public Uni<StopDockerContainerResponse> execute(@Valid final StopDockerContainerRequest request) {
        final var poolServer = request.getPoolServer();
        return handleRequest(poolServer,
                request,
                stopDockerContainerMethod::execute,
                PoolApi::execute);
    }

    <R, T> Uni<R> handleRequest(final PoolServerModel poolServer,
                                final T request,
                                final Function<T, Uni<R>> execute,
                                final BiFunction<PoolModuleClient, T, Uni<R>> route) {
        return switch (poolServer.getQualifier()) {
            case DOCKER_HOST -> {
                final var poolServerUri = poolServer.getConfig().getServerUri();
                final var thisServerUri = getConfigOperation.getServiceConfig().index().serverUri();
                if (poolServerUri.equals(thisServerUri)) {
                    yield execute.apply(request);
                } else {
                    final var client = getPoolModuleClientOperation.getClient(poolServerUri);
                    yield route.apply(client, request);
                }
            }
        };
    }
}
