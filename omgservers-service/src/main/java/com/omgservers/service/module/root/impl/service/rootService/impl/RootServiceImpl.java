package com.omgservers.service.module.root.impl.service.rootService.impl;

import com.omgservers.model.dto.root.DeleteRootRequest;
import com.omgservers.model.dto.root.DeleteRootResponse;
import com.omgservers.model.dto.root.GetRootRequest;
import com.omgservers.model.dto.root.GetRootResponse;
import com.omgservers.model.dto.root.SyncRootRequest;
import com.omgservers.model.dto.root.SyncRootResponse;
import com.omgservers.service.module.root.impl.operation.getRootModuleClient.GetRootModuleClientOperation;
import com.omgservers.service.module.root.impl.service.rootService.RootService;
import com.omgservers.service.module.root.impl.service.rootService.impl.method.deleteRoot.DeleteRootMethod;
import com.omgservers.service.module.root.impl.service.rootService.impl.method.getRoot.GetRootMethod;
import com.omgservers.service.module.root.impl.service.rootService.impl.method.syncRoot.SyncRootMethod;
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

    final DeleteRootMethod deleteRootMethod;
    final SyncRootMethod syncRootMethod;
    final GetRootMethod getRootMethod;

    final GetRootModuleClientOperation getMatchServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetRootResponse> getRoot(@Valid final GetRootRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                RootApi::getRoot,
                getRootMethod::getRoot);
    }

    @Override
    public Uni<SyncRootResponse> syncRoot(@Valid final SyncRootRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                RootApi::syncRoot,
                syncRootMethod::syncRoot);
    }

    @Override
    public Uni<DeleteRootResponse> deleteRoot(@Valid final DeleteRootRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                RootApi::deleteRoot,
                deleteRootMethod::deleteRoot);
    }
}
