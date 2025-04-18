package com.omgservers.service.shard.docker.impl.service.dockerService.impl;

import com.omgservers.schema.shard.docker.PingDockerHostRequest;
import com.omgservers.schema.shard.docker.PingDockerHostResponse;
import com.omgservers.schema.shard.docker.StartDockerContainerRequest;
import com.omgservers.schema.shard.docker.StartDockerContainerResponse;
import com.omgservers.schema.shard.docker.StopDockerContainerRequest;
import com.omgservers.schema.shard.docker.StopDockerContainerResponse;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
import com.omgservers.service.shard.docker.impl.operation.GetDockerModuleClientOperation;
import com.omgservers.service.shard.docker.impl.service.dockerService.DockerService;
import com.omgservers.service.shard.docker.impl.service.dockerService.impl.method.PingDockerHostMethod;
import com.omgservers.service.shard.docker.impl.service.dockerService.impl.method.StartDockerContainerMethod;
import com.omgservers.service.shard.docker.impl.service.dockerService.impl.method.StopDockerContainerMethod;
import com.omgservers.service.shard.docker.impl.service.webService.impl.api.DockerApi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DockerServiceImpl implements DockerService {

    final StartDockerContainerMethod startDockerContainerMethod;
    final StopDockerContainerMethod stopDockerContainerMethod;
    final PingDockerHostMethod pingDockerHostMethod;

    final GetDockerModuleClientOperation getDockerModuleClientOperation;
    final HandleShardedRequestOperation handleShardedRequestOperation;

    @Override
    public Uni<PingDockerHostResponse> execute(@Valid final PingDockerHostRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getDockerModuleClientOperation::getClient,
                DockerApi::execute,
                pingDockerHostMethod::execute);
    }

    @Override
    public Uni<StartDockerContainerResponse> execute(@Valid final StartDockerContainerRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getDockerModuleClientOperation::getClient,
                DockerApi::execute,
                startDockerContainerMethod::execute);
    }

    @Override
    public Uni<StopDockerContainerResponse> execute(@Valid final StopDockerContainerRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getDockerModuleClientOperation::getClient,
                DockerApi::execute,
                stopDockerContainerMethod::execute);
    }
}
