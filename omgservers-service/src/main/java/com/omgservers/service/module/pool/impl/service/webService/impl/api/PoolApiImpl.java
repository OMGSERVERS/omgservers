package com.omgservers.service.module.pool.impl.service.webService.impl.api;

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
import com.omgservers.schema.model.internalRole.InternalRoleEnum;
import com.omgservers.service.module.pool.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RolesAllowed({InternalRoleEnum.Names.SERVICE})
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolApiImpl implements PoolApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    public Uni<GetPoolResponse> getPool(final GetPoolRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getPool);
    }

    @Override
    public Uni<SyncPoolResponse> syncPool(final SyncPoolRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncPool);
    }

    @Override
    public Uni<DeletePoolResponse> deletePool(final DeletePoolRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deletePool);
    }

    @Override
    public Uni<GetPoolServerResponse> getPoolServer(final GetPoolServerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getPoolServer);
    }

    @Override
    public Uni<ViewPoolServerResponse> viewPoolServers(final ViewPoolServersRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewPoolServers);
    }

    @Override
    public Uni<SyncPoolServerResponse> syncPoolServer(final SyncPoolServerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncPoolServer);
    }

    @Override
    public Uni<DeletePoolServerResponse> deletePoolServer(final DeletePoolServerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deletePoolServer);
    }

    @Override
    public Uni<GetPoolRequestResponse> getPoolRequest(
            final GetPoolRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getPoolRequest);
    }

    @Override
    public Uni<FindPoolRequestResponse> findPoolRequest(
            final FindPoolRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findPoolRequest);
    }

    @Override
    public Uni<ViewPoolRequestsResponse> viewPoolRequests(
            final ViewPoolRequestsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewPoolRequests);
    }

    @Override
    public Uni<SyncPoolRequestResponse> syncPoolRequest(
            final SyncPoolRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncPoolRequest);
    }

    @Override
    public Uni<DeletePoolRequestResponse> deletePoolRequest(
            final DeletePoolRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deletePoolRequest);
    }

    @Override
    public Uni<GetPoolServerContainerResponse> getPoolServerContainer(
            final GetPoolServerContainerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getPoolServerContainer);
    }

    @Override
    public Uni<FindPoolServerContainerResponse> findPoolServerContainer(
            final FindPoolServerContainerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findPoolServerContainer);
    }

    @Override
    public Uni<ViewPoolServerContainersResponse> viewPoolServerContainers(
            final ViewPoolServerContainersRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewPoolServerContainers);
    }

    @Override
    public Uni<SyncPoolServerContainerResponse> syncPoolServerContainer(
            final SyncPoolServerContainerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncPoolServerContainer);
    }

    @Override
    public Uni<DeletePoolServerContainerResponse> deletePoolServerContainer(
            final DeletePoolServerContainerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deletePoolServerContainer);
    }

    @Override
    public Uni<StartDockerContainerResponse> startDockerContainer(final StartDockerContainerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::startDockerContainer);
    }

    @Override
    public Uni<StopDockerContainerResponse> stopDockerContainer(final StopDockerContainerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::stopDockerContainer);
    }
}
