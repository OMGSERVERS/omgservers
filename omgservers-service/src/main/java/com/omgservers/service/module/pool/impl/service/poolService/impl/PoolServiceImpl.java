package com.omgservers.service.module.pool.impl.service.poolService.impl;

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
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.pool.impl.operation.getPoolModuleClient.GetPoolModuleClientOperation;
import com.omgservers.service.module.pool.impl.service.poolService.PoolService;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.pool.deletePool.DeletePoolMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.pool.getPool.GetPoolMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.pool.syncPool.SyncPoolMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest.deletePoolRequest.DeletePoolRequestMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest.findPoolRequest.FindPoolRequestMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest.getPoolRequest.GetPoolRequestMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest.syncPoolRequest.SyncPoolRequestMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest.viewPoolRequest.ViewPoolRequestsMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServer.deletePoolServer.DeletePoolServerMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServer.getPoolServer.GetPoolServerMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServer.syncPoolServer.SyncPoolServerMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServer.viewPoolServers.ViewPoolServersMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.deletePoolServerContainer.DeletePoolServerContainerMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.findPoolServerContainer.FindPoolServerContainerMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.getPoolServerContainer.GetPoolServerContainerMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.syncPoolServerContainer.SyncPoolServerContainerMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.viewPoolServerContainers.ViewPoolServerContainersMethod;
import com.omgservers.service.module.pool.impl.service.webService.impl.api.PoolApi;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PoolServiceImpl implements PoolService {

    final DeletePoolServerContainerMethod deletePoolServerContainerMethod;
    final ViewPoolServerContainersMethod viewPoolServerContainersMethod;
    final FindPoolServerContainerMethod findPoolServerContainerMethod;
    final SyncPoolServerContainerMethod syncPoolServerContainerMethod;
    final GetPoolServerContainerMethod getPoolServerContainerMethod;
    final DeletePoolRequestMethod deletePoolRequestMethod;
    final ViewPoolRequestsMethod viewPoolRequestsMethod;
    final DeletePoolServerMethod deletePoolServerMethod;
    final FindPoolRequestMethod findPoolRequestMethod;
    final SyncPoolRequestMethod syncPoolRequestMethod;
    final ViewPoolServersMethod viewPoolServersMethod;
    final GetPoolRequestMethod getPoolRequestMethod;
    final SyncPoolServerMethod syncPoolServerMethod;
    final GetPoolServerMethod getPoolServerMethod;
    final DeletePoolMethod deletePoolMethod;
    final SyncPoolMethod syncPoolMethod;
    final GetPoolMethod getPoolMethod;

    final HandleInternalRequestOperation handleInternalRequestOperation;
    final GetPoolModuleClientOperation getPoolModuleClientOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetPoolResponse> getPool(@Valid final GetPoolRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::getPool,
                getPoolMethod::getPool);
    }

    @Override
    public Uni<SyncPoolResponse> syncPool(@Valid final SyncPoolRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::syncPool,
                syncPoolMethod::syncPool);
    }

    @Override
    public Uni<DeletePoolResponse> deletePool(@Valid final DeletePoolRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::deletePool,
                deletePoolMethod::deletePool);
    }

    @Override
    public Uni<GetPoolServerResponse> getPoolServer(@Valid final GetPoolServerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::getPoolServer,
                getPoolServerMethod::getPoolServer);
    }

    @Override
    public Uni<ViewPoolServerResponse> viewPoolServers(@Valid final ViewPoolServersRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::viewPoolServers,
                viewPoolServersMethod::viewPoolServers);
    }

    @Override
    public Uni<SyncPoolServerResponse> syncPoolServer(@Valid final SyncPoolServerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::syncPoolServer,
                syncPoolServerMethod::syncPoolServer);
    }

    @Override
    public Uni<SyncPoolServerResponse> syncPoolServerWithIdempotency(SyncPoolServerRequest request) {
        return syncPoolServer(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", request.getPoolServer(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncPoolServerResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeletePoolServerResponse> deletePoolServer(@Valid final DeletePoolServerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::deletePoolServer,
                deletePoolServerMethod::deletePoolServer);
    }

    @Override
    public Uni<GetPoolRequestResponse> getPoolRequest(
            @Valid final GetPoolRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::getPoolRequest,
                getPoolRequestMethod::getPoolRequest);
    }

    @Override
    public Uni<FindPoolRequestResponse> findPoolRequest(
            @Valid final FindPoolRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::findPoolRequest,
                findPoolRequestMethod::findPoolRequest);
    }

    @Override
    public Uni<ViewPoolRequestsResponse> viewPoolRequests(
            @Valid final ViewPoolRequestsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::viewPoolRequests,
                viewPoolRequestsMethod::viewPoolRequests);
    }

    @Override
    public Uni<SyncPoolRequestResponse> syncPoolRequest(@Valid final SyncPoolRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::syncPoolRequest,
                syncPoolRequestMethod::syncPoolRequest);
    }

    @Override
    public Uni<SyncPoolRequestResponse> syncPoolRequestWithIdempotency(@Valid final SyncPoolRequestRequest request) {
        return syncPoolRequest(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", request.getPoolRequest(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncPoolRequestResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeletePoolRequestResponse> deletePoolRequest(
            @Valid final DeletePoolRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::deletePoolRequest,
                deletePoolRequestMethod::deletePoolRuntimeServerContainerRequest);
    }

    @Override
    public Uni<GetPoolServerContainerResponse> getPoolServerContainer(
            @Valid final GetPoolServerContainerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::getPoolServerContainer,
                getPoolServerContainerMethod::getPoolServerContainer);
    }

    @Override
    public Uni<FindPoolServerContainerResponse> findPoolServerContainer(
            @Valid final FindPoolServerContainerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::findPoolServerContainer,
                findPoolServerContainerMethod::findPoolServerContainer);
    }

    @Override
    public Uni<ViewPoolServerContainersResponse> viewPoolServerContainers(
            @Valid final ViewPoolServerContainersRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::viewPoolServerContainers,
                viewPoolServerContainersMethod::viewPoolServerContainers);
    }

    @Override
    public Uni<SyncPoolServerContainerResponse> syncPoolServerContainer(
            @Valid final SyncPoolServerContainerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::syncPoolServerContainer,
                syncPoolServerContainerMethod::syncPoolServerContainer);
    }

    @Override
    public Uni<DeletePoolServerContainerResponse> deletePoolServerContainer(
            @Valid final DeletePoolServerContainerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::deletePoolServerContainer,
                deletePoolServerContainerMethod::deletePoolServerContainer);
    }
}
