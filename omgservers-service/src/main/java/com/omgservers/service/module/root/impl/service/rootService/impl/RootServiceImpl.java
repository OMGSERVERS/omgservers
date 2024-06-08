package com.omgservers.service.module.root.impl.service.rootService.impl;

import com.omgservers.model.dto.root.root.DeleteRootRequest;
import com.omgservers.model.dto.root.root.DeleteRootResponse;
import com.omgservers.model.dto.root.root.GetRootRequest;
import com.omgservers.model.dto.root.root.GetRootResponse;
import com.omgservers.model.dto.root.root.SyncRootRequest;
import com.omgservers.model.dto.root.root.SyncRootResponse;
import com.omgservers.model.dto.root.rootEntityRef.DeleteRootEntityRefRequest;
import com.omgservers.model.dto.root.rootEntityRef.DeleteRootEntityRefResponse;
import com.omgservers.model.dto.root.rootEntityRef.FindRootEntityRefRequest;
import com.omgservers.model.dto.root.rootEntityRef.FindRootEntityRefResponse;
import com.omgservers.model.dto.root.rootEntityRef.GetRootEntityRefRequest;
import com.omgservers.model.dto.root.rootEntityRef.GetRootEntityRefResponse;
import com.omgservers.model.dto.root.rootEntityRef.SyncRootEntityRefRequest;
import com.omgservers.model.dto.root.rootEntityRef.SyncRootEntityRefResponse;
import com.omgservers.model.dto.root.rootEntityRef.ViewRootEntityRefsRequest;
import com.omgservers.model.dto.root.rootEntityRef.ViewRootEntityRefsResponse;
import com.omgservers.service.exception.ExceptionQualifierEnum;
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
class RootServiceImpl implements RootService {

    final DeleteRootEntityRefMethod deleteRootEntityRefMethod;
    final ViewRootEntityRefsMethod viewRootEntityRefsMethod;
    final FindRootEntityRefMethod findRootEntityRefMethod;
    final SyncRootEntityRefMethod syncRootEntityRefMethod;
    final GetRootEntityRefMethod getRootEntityRefMethod;
    final DeleteRootMethod deleteRootMethod;
    final SyncRootMethod syncRootMethod;
    final GetRootMethod getRootMethod;

    final HandleInternalRequestOperation handleInternalRequestOperation;
    final GetRootModuleClientOperation getRootModuleClientOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetRootResponse> getRoot(@Valid final GetRootRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::getRoot,
                getRootMethod::getRoot);
    }

    @Override
    public Uni<SyncRootResponse> syncRoot(@Valid final SyncRootRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
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
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::deleteRoot,
                deleteRootMethod::deleteRoot);
    }

    @Override
    public Uni<GetRootEntityRefResponse> getRootEntityRef(@Valid final GetRootEntityRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::getRootEntityRef,
                getRootEntityRefMethod::getRootEntityRef);
    }

    @Override
    public Uni<FindRootEntityRefResponse> findRootEntityRef(@Valid final FindRootEntityRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::findRootEntityRef,
                findRootEntityRefMethod::findRootEntityRef);
    }

    @Override
    public Uni<ViewRootEntityRefsResponse> viewRootEntityRefs(@Valid final ViewRootEntityRefsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::viewRootEntityRefs,
                viewRootEntityRefsMethod::viewRootEntityRefs);
    }

    @Override
    public Uni<SyncRootEntityRefResponse> syncRootEntityRef(@Valid final SyncRootEntityRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
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
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRootModuleClientOperation::getClient,
                RootApi::deleteRootEntityRef,
                deleteRootEntityRefMethod::deleteRootEntityRef);
    }
}
