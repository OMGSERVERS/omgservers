package com.omgservers.service.module.pool.impl.service.webService.impl;

import com.omgservers.model.dto.pool.pool.DeletePoolRequest;
import com.omgservers.model.dto.pool.pool.DeletePoolResponse;
import com.omgservers.model.dto.pool.pool.GetPoolRequest;
import com.omgservers.model.dto.pool.pool.GetPoolResponse;
import com.omgservers.model.dto.pool.pool.SyncPoolRequest;
import com.omgservers.model.dto.pool.pool.SyncPoolResponse;
import com.omgservers.model.dto.pool.poolRequest.DeletePoolRequestRequest;
import com.omgservers.model.dto.pool.poolRequest.DeletePoolRequestResponse;
import com.omgservers.model.dto.pool.poolRequest.FindPoolRequestRequest;
import com.omgservers.model.dto.pool.poolRequest.FindPoolRequestResponse;
import com.omgservers.model.dto.pool.poolRequest.GetPoolRequestRequest;
import com.omgservers.model.dto.pool.poolRequest.GetPoolRequestResponse;
import com.omgservers.model.dto.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.model.dto.pool.poolRequest.SyncPoolRequestResponse;
import com.omgservers.model.dto.pool.poolRequest.ViewPoolRequestsRequest;
import com.omgservers.model.dto.pool.poolRequest.ViewPoolRequestsResponse;
import com.omgservers.model.dto.pool.poolServer.DeletePoolServerRequest;
import com.omgservers.model.dto.pool.poolServer.DeletePoolServerResponse;
import com.omgservers.model.dto.pool.poolServer.GetPoolServerRequest;
import com.omgservers.model.dto.pool.poolServer.GetPoolServerResponse;
import com.omgservers.model.dto.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.model.dto.pool.poolServer.SyncPoolServerResponse;
import com.omgservers.model.dto.pool.poolServer.ViewPoolServerResponse;
import com.omgservers.model.dto.pool.poolServer.ViewPoolServersRequest;
import com.omgservers.model.dto.pool.poolServerContainer.DeletePoolServerContainerRequest;
import com.omgservers.model.dto.pool.poolServerContainer.DeletePoolServerContainerResponse;
import com.omgservers.model.dto.pool.poolServerContainer.FindPoolServerContainerRequest;
import com.omgservers.model.dto.pool.poolServerContainer.FindPoolServerContainerResponse;
import com.omgservers.model.dto.pool.poolServerContainer.GetPoolServerContainerRequest;
import com.omgservers.model.dto.pool.poolServerContainer.GetPoolServerContainerResponse;
import com.omgservers.model.dto.pool.poolServerContainer.SyncPoolServerContainerRequest;
import com.omgservers.model.dto.pool.poolServerContainer.SyncPoolServerContainerResponse;
import com.omgservers.model.dto.pool.poolServerContainer.ViewPoolServerContainersRequest;
import com.omgservers.model.dto.pool.poolServerContainer.ViewPoolServerContainersResponse;
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
}
