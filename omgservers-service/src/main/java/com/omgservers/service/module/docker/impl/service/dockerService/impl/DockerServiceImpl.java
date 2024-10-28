package com.omgservers.service.module.docker.impl.service.dockerService.impl;

import com.omgservers.schema.module.docker.PingDockerHostRequest;
import com.omgservers.schema.module.docker.PingDockerHostResponse;
import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.docker.StopDockerContainerResponse;
import com.omgservers.service.module.docker.impl.operation.GetDockerModuleClientOperation;
import com.omgservers.service.module.docker.impl.service.dockerService.DockerService;
import com.omgservers.service.module.docker.impl.service.dockerService.impl.method.PingDockerHostMethod;
import com.omgservers.service.module.docker.impl.service.dockerService.impl.method.StartDockerContainerMethod;
import com.omgservers.service.module.docker.impl.service.dockerService.impl.method.StopDockerContainerMethod;
import com.omgservers.service.module.docker.impl.service.webService.impl.api.DockerApi;
import com.omgservers.service.operation.handleInternalRequest.HandleShardedRequestOperation;
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
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDockerModuleClientOperation::getClient,
                DockerApi::execute,
                pingDockerHostMethod::execute);
    }

    @Override
    public Uni<StartDockerContainerResponse> execute(@Valid final StartDockerContainerRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDockerModuleClientOperation::getClient,
                DockerApi::execute,
                startDockerContainerMethod::execute);
    }

    @Override
    public Uni<StopDockerContainerResponse> execute(@Valid final StopDockerContainerRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDockerModuleClientOperation::getClient,
                DockerApi::execute,
                stopDockerContainerMethod::execute);
    }
}
