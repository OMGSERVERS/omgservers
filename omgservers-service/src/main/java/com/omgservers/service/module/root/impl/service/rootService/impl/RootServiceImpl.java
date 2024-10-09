package com.omgservers.service.module.root.impl.service.rootService.impl;

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
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.root.impl.operation.getRootModuleClient.GetRootModuleClientOperation;
import com.omgservers.service.module.root.impl.service.rootService.RootService;
import com.omgservers.service.module.root.impl.service.rootService.impl.method.root.deleteRoot.DeleteRootMethod;
import com.omgservers.service.module.root.impl.service.rootService.impl.method.root.getRoot.GetRootMethod;
import com.omgservers.service.module.root.impl.service.rootService.impl.method.root.syncRoot.SyncRootMethod;
import com.omgservers.service.module.root.impl.service.rootService.impl.method.rootEntityRef.deleteRootEntityRef.DeleteRootEntityRefMethod;
import com.omgservers.service.module.root.impl.service.rootService.impl.method.rootEntityRef.findRootEntityRef.FindRootEntityRefMethod;
import com.omgservers.service.module.root.impl.service.rootService.impl.method.rootEntityRef.getRootEntityRef.GetRootEntityRefMethod;
import com.omgservers.service.module.root.impl.service.rootService.impl.method.rootEntityRef.syncRootEntityRef.SyncRootEntityRefMethod;
import com.omgservers.service.module.root.impl.service.rootService.impl.method.rootEntityRef.viewRootEntityRefs.ViewRootEntityRefsMethod;
import com.omgservers.service.module.root.impl.service.webService.impl.api.RootApi;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
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
    public Uni<GetRootResponse> getRoot(@Valid final GetRootRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::getRoot,
                getRootMethod::getRoot);
    }

    @Override
    public Uni<SyncRootResponse> syncRoot(@Valid final SyncRootRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::syncRoot,
                syncRootMethod::syncRoot);
    }

    @Override
    public Uni<SyncRootResponse> syncRootWithIdempotency(SyncRootRequest request) {
        return syncRoot(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", request.getRoot(), t.getMessage());
                            return Uni.createFrom().item(new SyncRootResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteRootResponse> deleteRoot(@Valid final DeleteRootRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::deleteRoot,
                deleteRootMethod::deleteRoot);
    }

    @Override
    public Uni<GetRootEntityRefResponse> getRootEntityRef(@Valid final GetRootEntityRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::getRootEntityRef,
                getRootEntityRefMethod::getRootEntityRef);
    }

    @Override
    public Uni<FindRootEntityRefResponse> findRootEntityRef(@Valid final FindRootEntityRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::findRootEntityRef,
                findRootEntityRefMethod::findRootEntityRef);
    }

    @Override
    public Uni<ViewRootEntityRefsResponse> viewRootEntityRefs(@Valid final ViewRootEntityRefsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::viewRootEntityRefs,
                viewRootEntityRefsMethod::viewRootEntityRefs);
    }

    @Override
    public Uni<SyncRootEntityRefResponse> syncRootEntityRef(@Valid final SyncRootEntityRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::syncRootEntityRef,
                syncRootEntityRefMethod::syncRootEntityRef);
    }

    @Override
    public Uni<SyncRootEntityRefResponse> syncRootEntityRefWithIdempotency(
            @Valid final SyncRootEntityRefRequest request) {
        return syncRootEntityRef(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}",
                                    request.getRootEntityRef(), t.getMessage());
                            return Uni.createFrom().item(new SyncRootEntityRefResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteRootEntityRefResponse> deleteRootEntityRef(@Valid final DeleteRootEntityRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::deleteRootEntityRef,
                deleteRootEntityRefMethod::deleteRootEntityRef);
    }
}
