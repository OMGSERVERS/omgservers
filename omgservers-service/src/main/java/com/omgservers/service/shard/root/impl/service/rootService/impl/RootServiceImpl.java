package com.omgservers.service.shard.root.impl.service.rootService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.root.root.DeleteRootRequest;
import com.omgservers.schema.module.root.root.DeleteRootResponse;
import com.omgservers.schema.module.root.root.GetRootRequest;
import com.omgservers.schema.module.root.root.GetRootResponse;
import com.omgservers.schema.module.root.root.SyncRootRequest;
import com.omgservers.schema.module.root.root.SyncRootResponse;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefResponse;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefResponse;
import com.omgservers.schema.module.root.rootEntityRef.GetRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.GetRootEntityRefResponse;
import com.omgservers.schema.module.root.rootEntityRef.SyncRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.SyncRootEntityRefResponse;
import com.omgservers.schema.module.root.rootEntityRef.ViewRootEntityRefsRequest;
import com.omgservers.schema.module.root.rootEntityRef.ViewRootEntityRefsResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.shard.root.impl.operation.getRootModuleClient.GetRootModuleClientOperation;
import com.omgservers.service.shard.root.impl.service.rootService.RootService;
import com.omgservers.service.shard.root.impl.service.rootService.impl.method.root.DeleteRootMethod;
import com.omgservers.service.shard.root.impl.service.rootService.impl.method.root.GetRootMethod;
import com.omgservers.service.shard.root.impl.service.rootService.impl.method.root.SyncRootMethod;
import com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef.DeleteRootEntityRefMethod;
import com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef.FindRootEntityRefMethod;
import com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef.GetRootEntityRefMethod;
import com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef.SyncRootEntityRefMethod;
import com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef.ViewRootEntityRefsMethod;
import com.omgservers.service.shard.root.impl.service.webService.impl.api.RootApi;
import com.omgservers.service.operation.server.CalculateShardOperation;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RootServiceImpl implements RootService {

    final DeleteRootEntityRefMethod deleteRootEntityRefMethod;
    final ViewRootEntityRefsMethod viewRootEntityRefsMethod;
    final FindRootEntityRefMethod findRootEntityRefMethod;
    final SyncRootEntityRefMethod syncRootEntityRefMethod;
    final GetRootEntityRefMethod getRootEntityRefMethod;
    final DeleteRootMethod deleteRootMethod;
    final SyncRootMethod syncRootMethod;
    final GetRootMethod getRootMethod;

    final HandleShardedRequestOperation handleShardedRequestOperation;
    final GetRootModuleClientOperation getRootModuleClientOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetRootResponse> execute(@Valid final GetRootRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::execute,
                getRootMethod::execute);
    }

    @Override
    public Uni<SyncRootResponse> execute(@Valid final SyncRootRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::execute,
                syncRootMethod::execute);
    }

    @Override
    public Uni<SyncRootResponse> executeWithIdempotency(SyncRootRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getRoot(), t.getMessage());
                            return Uni.createFrom().item(new SyncRootResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteRootResponse> execute(@Valid final DeleteRootRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::execute,
                deleteRootMethod::execute);
    }

    @Override
    public Uni<GetRootEntityRefResponse> execute(@Valid final GetRootEntityRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::execute,
                getRootEntityRefMethod::execute);
    }

    @Override
    public Uni<FindRootEntityRefResponse> execute(@Valid final FindRootEntityRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::execute,
                findRootEntityRefMethod::execute);
    }

    @Override
    public Uni<ViewRootEntityRefsResponse> execute(@Valid final ViewRootEntityRefsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::execute,
                viewRootEntityRefsMethod::execute);
    }

    @Override
    public Uni<SyncRootEntityRefResponse> execute(@Valid final SyncRootEntityRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::execute,
                syncRootEntityRefMethod::execute);
    }

    @Override
    public Uni<SyncRootEntityRefResponse> executeWithIdempotency(
            @Valid final SyncRootEntityRefRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}",
                                    request.getRootEntityRef(), t.getMessage());
                            return Uni.createFrom().item(new SyncRootEntityRefResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteRootEntityRefResponse> execute(@Valid final DeleteRootEntityRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::execute,
                deleteRootEntityRefMethod::execute);
    }
}
