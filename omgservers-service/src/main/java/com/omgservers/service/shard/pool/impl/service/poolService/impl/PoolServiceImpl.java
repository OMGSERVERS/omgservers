package com.omgservers.service.shard.pool.impl.service.poolService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.shard.pool.pool.DeletePoolRequest;
import com.omgservers.schema.shard.pool.pool.DeletePoolResponse;
import com.omgservers.schema.shard.pool.pool.GetPoolRequest;
import com.omgservers.schema.shard.pool.pool.GetPoolResponse;
import com.omgservers.schema.shard.pool.pool.SyncPoolRequest;
import com.omgservers.schema.shard.pool.pool.SyncPoolResponse;
import com.omgservers.schema.shard.pool.poolCommand.DeletePoolCommandRequest;
import com.omgservers.schema.shard.pool.poolCommand.DeletePoolCommandResponse;
import com.omgservers.schema.shard.pool.poolCommand.GetPoolCommandRequest;
import com.omgservers.schema.shard.pool.poolCommand.GetPoolCommandResponse;
import com.omgservers.schema.shard.pool.poolCommand.SyncPoolCommandRequest;
import com.omgservers.schema.shard.pool.poolCommand.SyncPoolCommandResponse;
import com.omgservers.schema.shard.pool.poolCommand.ViewPoolCommandRequest;
import com.omgservers.schema.shard.pool.poolCommand.ViewPoolCommandResponse;
import com.omgservers.schema.shard.pool.poolContainer.DeletePoolContainerRequest;
import com.omgservers.schema.shard.pool.poolContainer.DeletePoolContainerResponse;
import com.omgservers.schema.shard.pool.poolContainer.FindPoolContainerRequest;
import com.omgservers.schema.shard.pool.poolContainer.FindPoolContainerResponse;
import com.omgservers.schema.shard.pool.poolContainer.GetPoolContainerRequest;
import com.omgservers.schema.shard.pool.poolContainer.GetPoolContainerResponse;
import com.omgservers.schema.shard.pool.poolContainer.SyncPoolContainerRequest;
import com.omgservers.schema.shard.pool.poolContainer.SyncPoolContainerResponse;
import com.omgservers.schema.shard.pool.poolContainer.ViewPoolContainersRequest;
import com.omgservers.schema.shard.pool.poolContainer.ViewPoolContainersResponse;
import com.omgservers.schema.shard.pool.poolRequest.DeletePoolRequestRequest;
import com.omgservers.schema.shard.pool.poolRequest.DeletePoolRequestResponse;
import com.omgservers.schema.shard.pool.poolRequest.FindPoolRequestRequest;
import com.omgservers.schema.shard.pool.poolRequest.FindPoolRequestResponse;
import com.omgservers.schema.shard.pool.poolRequest.GetPoolRequestRequest;
import com.omgservers.schema.shard.pool.poolRequest.GetPoolRequestResponse;
import com.omgservers.schema.shard.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.schema.shard.pool.poolRequest.SyncPoolRequestResponse;
import com.omgservers.schema.shard.pool.poolRequest.ViewPoolRequestsRequest;
import com.omgservers.schema.shard.pool.poolRequest.ViewPoolRequestsResponse;
import com.omgservers.schema.shard.pool.poolServer.DeletePoolServerRequest;
import com.omgservers.schema.shard.pool.poolServer.DeletePoolServerResponse;
import com.omgservers.schema.shard.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.shard.pool.poolServer.GetPoolServerResponse;
import com.omgservers.schema.shard.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.schema.shard.pool.poolServer.SyncPoolServerResponse;
import com.omgservers.schema.shard.pool.poolServer.ViewPoolServersRequest;
import com.omgservers.schema.shard.pool.poolServer.ViewPoolServersResponse;
import com.omgservers.schema.shard.pool.poolState.GetPoolStateRequest;
import com.omgservers.schema.shard.pool.poolState.GetPoolStateResponse;
import com.omgservers.schema.shard.pool.poolState.UpdatePoolStateRequest;
import com.omgservers.schema.shard.pool.poolState.UpdatePoolStateResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.CalculateShardOperation;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
import com.omgservers.service.shard.pool.impl.operation.getPoolModuleClient.GetPoolModuleClientOperation;
import com.omgservers.service.shard.pool.impl.service.poolService.PoolService;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.pool.DeletePoolMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.pool.GetPoolMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.pool.SyncPoolMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand.DeletePoolCommandMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand.GetPoolCommandMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand.SyncPoolCommandMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolCommand.ViewPoolCommandsMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer.DeletePoolContainerMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer.FindPoolContainerMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer.GetPoolContainerMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer.SyncPoolContainerMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer.ViewPoolContainersMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest.DeletePoolRequestMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest.FindPoolRequestMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest.GetPoolRequestMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest.SyncPoolRequestMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest.ViewPoolRequestsMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolServer.DeletePoolServerMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolServer.GetPoolServerMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolServer.SyncPoolServerMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolServer.ViewPoolServersMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolState.GetPoolStateMethod;
import com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolState.UpdatePoolStateMethod;
import com.omgservers.service.shard.pool.impl.service.webService.impl.api.PoolApi;
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

    final DeletePoolContainerMethod deletePoolContainerMethod;
    final ViewPoolContainersMethod viewPoolContainersMethod;
    final FindPoolContainerMethod findPoolContainerMethod;
    final SyncPoolContainerMethod syncPoolContainerMethod;
    final DeletePoolRequestMethod deletePoolRequestMethod;
    final DeletePoolCommandMethod deletePoolCommandMethod;
    final GetPoolContainerMethod getPoolContainerMethod;
    final ViewPoolRequestsMethod viewPoolRequestsMethod;
    final DeletePoolServerMethod deletePoolServerMethod;
    final ViewPoolCommandsMethod viewPoolCommandsMethod;
    final SyncPoolCommandMethod syncPoolCommandMethod;
    final FindPoolRequestMethod findPoolRequestMethod;
    final SyncPoolRequestMethod syncPoolRequestMethod;
    final ViewPoolServersMethod viewPoolServersMethod;
    final UpdatePoolStateMethod updatePoolStateMethod;
    final GetPoolCommandMethod getPoolCommandMethod;
    final GetPoolRequestMethod getPoolRequestMethod;
    final SyncPoolServerMethod syncPoolServerMethod;
    final GetPoolServerMethod getPoolServerMethod;
    final GetPoolStateMethod getPoolStateMethod;
    final DeletePoolMethod deletePoolMethod;
    final SyncPoolMethod syncPoolMethod;
    final GetPoolMethod getPoolMethod;

    final HandleShardedRequestOperation handleShardedRequestOperation;
    final GetPoolModuleClientOperation getPoolModuleClientOperation;
    final CalculateShardOperation calculateShardOperation;

    /*
    Pool
     */

    @Override
    public Uni<GetPoolResponse> execute(@Valid final GetPoolRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                getPoolMethod::execute);
    }

    @Override
    public Uni<SyncPoolResponse> execute(@Valid final SyncPoolRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                syncPoolMethod::execute);
    }

    @Override
    public Uni<SyncPoolResponse> executeWithIdempotency(SyncPoolRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getPool(), t.getMessage());
                            return Uni.createFrom().item(new SyncPoolResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeletePoolResponse> execute(@Valid final DeletePoolRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                deletePoolMethod::execute);
    }

    /*
    PoolCommand
     */

    @Override
    public Uni<GetPoolCommandResponse> execute(@Valid final GetPoolCommandRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                getPoolCommandMethod::execute);
    }

    @Override
    public Uni<ViewPoolCommandResponse> execute(@Valid final ViewPoolCommandRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                viewPoolCommandsMethod::execute);
    }

    @Override
    public Uni<SyncPoolCommandResponse> execute(@Valid final SyncPoolCommandRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                syncPoolCommandMethod::execute);
    }

    @Override
    public Uni<SyncPoolCommandResponse> executeWithIdempotency(SyncPoolCommandRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getPoolCommand(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncPoolCommandResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeletePoolCommandResponse> execute(@Valid final DeletePoolCommandRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                deletePoolCommandMethod::execute);
    }

    /*
    PoolRequest
     */

    @Override
    public Uni<GetPoolRequestResponse> execute(@Valid final GetPoolRequestRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                getPoolRequestMethod::execute);
    }

    @Override
    public Uni<FindPoolRequestResponse> execute(@Valid final FindPoolRequestRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                findPoolRequestMethod::execute);
    }

    @Override
    public Uni<ViewPoolRequestsResponse> execute(@Valid final ViewPoolRequestsRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                viewPoolRequestsMethod::execute);
    }

    @Override
    public Uni<SyncPoolRequestResponse> execute(@Valid final SyncPoolRequestRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                syncPoolRequestMethod::execute);
    }

    @Override
    public Uni<SyncPoolRequestResponse> executeWithIdempotency(@Valid final SyncPoolRequestRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getPoolRequest(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncPoolRequestResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeletePoolRequestResponse> execute(@Valid final DeletePoolRequestRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                deletePoolRequestMethod::execute);
    }

    /*
    PoolServer
     */

    @Override
    public Uni<GetPoolServerResponse> execute(@Valid final GetPoolServerRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                getPoolServerMethod::execute);
    }

    @Override
    public Uni<ViewPoolServersResponse> execute(@Valid final ViewPoolServersRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                viewPoolServersMethod::execute);
    }

    @Override
    public Uni<SyncPoolServerResponse> execute(@Valid final SyncPoolServerRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                syncPoolServerMethod::execute);
    }

    @Override
    public Uni<SyncPoolServerResponse> executeWithIdempotency(SyncPoolServerRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getPoolServer(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncPoolServerResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeletePoolServerResponse> execute(@Valid final DeletePoolServerRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                deletePoolServerMethod::execute);
    }

    /*
    PoolContainer
     */

    @Override
    public Uni<GetPoolContainerResponse> execute(@Valid final GetPoolContainerRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                getPoolContainerMethod::execute);
    }

    @Override
    public Uni<FindPoolContainerResponse> execute(@Valid final FindPoolContainerRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                findPoolContainerMethod::execute);
    }

    @Override
    public Uni<ViewPoolContainersResponse> execute(@Valid final ViewPoolContainersRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                viewPoolContainersMethod::execute);
    }

    @Override
    public Uni<SyncPoolContainerResponse> execute(@Valid final SyncPoolContainerRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                syncPoolContainerMethod::execute);
    }

    @Override
    public Uni<SyncPoolContainerResponse> executeWithIdempotency(@Valid final SyncPoolContainerRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getPoolContainer(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncPoolContainerResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeletePoolContainerResponse> execute(@Valid final DeletePoolContainerRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                deletePoolContainerMethod::execute);
    }

    /*
    PoolState
     */

    @Override
    public Uni<GetPoolStateResponse> execute(@Valid final GetPoolStateRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                getPoolStateMethod::execute);
    }

    @Override
    public Uni<UpdatePoolStateResponse> execute(final @Valid UpdatePoolStateRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getPoolModuleClientOperation::getClient,
                PoolApi::execute,
                updatePoolStateMethod::execute);
    }
}
