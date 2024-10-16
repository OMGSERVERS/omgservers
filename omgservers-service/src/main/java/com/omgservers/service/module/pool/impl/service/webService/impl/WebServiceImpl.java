package com.omgservers.service.module.pool.impl.service.webService.impl;

import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.docker.StopDockerContainerResponse;
import com.omgservers.schema.module.pool.pool.DeletePoolRequest;
import com.omgservers.schema.module.pool.pool.DeletePoolResponse;
import com.omgservers.schema.module.pool.pool.GetPoolRequest;
import com.omgservers.schema.module.pool.pool.GetPoolResponse;
import com.omgservers.schema.module.pool.pool.SyncPoolRequest;
import com.omgservers.schema.module.pool.pool.SyncPoolResponse;
import com.omgservers.schema.module.pool.poolRequest.DeletePoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.DeletePoolRequestResponse;
import com.omgservers.schema.module.pool.poolRequest.FindPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.FindPoolRequestResponse;
import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestResponse;
import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestResponse;
import com.omgservers.schema.module.pool.poolRequest.ViewPoolRequestsRequest;
import com.omgservers.schema.module.pool.poolRequest.ViewPoolRequestsResponse;
import com.omgservers.schema.module.pool.poolServer.DeletePoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.DeletePoolServerResponse;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import com.omgservers.schema.module.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.SyncPoolServerResponse;
import com.omgservers.schema.module.pool.poolServer.ViewPoolServerResponse;
import com.omgservers.schema.module.pool.poolServer.ViewPoolServersRequest;
import com.omgservers.schema.module.pool.poolServerContainer.DeletePoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.DeletePoolServerContainerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.FindPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.FindPoolServerContainerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.GetPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.GetPoolServerContainerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.SyncPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.SyncPoolServerContainerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.ViewPoolServerContainersRequest;
import com.omgservers.schema.module.pool.poolServerContainer.ViewPoolServerContainersResponse;
import com.omgservers.service.module.pool.impl.service.dockerService.DockerService;
import com.omgservers.service.module.pool.impl.service.poolService.PoolService;
import com.omgservers.service.module.pool.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final DockerService dockerService;
    final PoolService poolService;

    @Override
    public Uni<GetPoolResponse> getPool(final GetPoolRequest request) {
        return poolService.getPool(request);
    }

    @Override
    public Uni<SyncPoolResponse> syncPool(final SyncPoolRequest request) {
        return poolService.syncPool(request);
    }

    @Override
    public Uni<DeletePoolResponse> deletePool(final DeletePoolRequest request) {
        return poolService.deletePool(request);
    }

    @Override
    public Uni<GetPoolServerResponse> getPoolServer(final GetPoolServerRequest request) {
        return poolService.getPoolServer(request);
    }

    @Override
    public Uni<ViewPoolServerResponse> viewPoolServers(final ViewPoolServersRequest request) {
        return poolService.viewPoolServers(request);
    }

    @Override
    public Uni<SyncPoolServerResponse> syncPoolServer(final SyncPoolServerRequest request) {
        return poolService.syncPoolServer(request);
    }

    @Override
    public Uni<DeletePoolServerResponse> deletePoolServer(final DeletePoolServerRequest request) {
        return poolService.deletePoolServer(request);
    }

    @Override
    public Uni<GetPoolRequestResponse> getPoolRequest(final GetPoolRequestRequest request) {
        return poolService.getPoolRequest(request);
    }

    @Override
    public Uni<FindPoolRequestResponse> findPoolRequest(final FindPoolRequestRequest request) {
        return poolService.findPoolRequest(request);
    }

    @Override
    public Uni<ViewPoolRequestsResponse> viewPoolRequests(final ViewPoolRequestsRequest request) {
        return poolService.viewPoolRequests(request);
    }

    @Override
    public Uni<SyncPoolRequestResponse> syncPoolRequest(final SyncPoolRequestRequest request) {
        return poolService.syncPoolRequest(request);
    }

    @Override
    public Uni<DeletePoolRequestResponse> deletePoolRequest(final DeletePoolRequestRequest request) {
        return poolService.deletePoolRequest(request);
    }

    @Override
    public Uni<GetPoolServerContainerResponse> getPoolServerContainer(final GetPoolServerContainerRequest request) {
        return poolService.getPoolServerContainer(request);
    }

    @Override
    public Uni<FindPoolServerContainerResponse> findPoolServerContainer(final FindPoolServerContainerRequest request) {
        return poolService.findPoolServerContainer(request);
    }

    @Override
    public Uni<ViewPoolServerContainersResponse> viewPoolServerContainers(
            final ViewPoolServerContainersRequest request) {
        return poolService.viewPoolServerContainers(request);
    }

    @Override
    public Uni<SyncPoolServerContainerResponse> syncPoolServerContainer(final SyncPoolServerContainerRequest request) {
        return poolService.syncPoolServerContainer(request);
    }

    @Override
    public Uni<DeletePoolServerContainerResponse> deletePoolServerContainer(
            final DeletePoolServerContainerRequest request) {
        return poolService.deletePoolServerContainer(request);
    }

    @Override
    public Uni<StartDockerContainerResponse> startDockerContainer(final StartDockerContainerRequest request) {
        return dockerService.startDockerContainer(request);
    }

    @Override
    public Uni<StopDockerContainerResponse> stopDockerContainer(final StopDockerContainerRequest request) {
        return dockerService.stopDockerContainer(request);
    }
}
