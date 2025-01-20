package com.omgservers.service.shard.root.impl.service.webService.impl.api;

import com.omgservers.schema.model.user.UserRoleEnum;
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
import com.omgservers.service.shard.root.impl.service.webService.WebService;
import com.omgservers.service.operation.server.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RolesAllowed({UserRoleEnum.Names.SERVICE})
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RootApiImpl implements RootApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    public Uni<GetRootResponse> getRoot(final GetRootRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getRoot);
    }

    @Override
    public Uni<SyncRootResponse> syncRoot(final SyncRootRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncRoot);
    }

    @Override
    public Uni<DeleteRootResponse> deleteRoot(final DeleteRootRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRoot);
    }

    @Override
    public Uni<GetRootEntityRefResponse> getRootEntityRef(final GetRootEntityRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getRootEntityRef);
    }

    @Override
    public Uni<FindRootEntityRefResponse> findRootEntityRef(final FindRootEntityRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findRootEntityRef);
    }

    @Override
    public Uni<ViewRootEntityRefsResponse> viewRootEntityRefs(final ViewRootEntityRefsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewRootEntityRefs);
    }

    @Override
    public Uni<SyncRootEntityRefResponse> syncRootEntityRef(final SyncRootEntityRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncRootEntityRef);
    }

    @Override
    public Uni<DeleteRootEntityRefResponse> deleteRootEntityRef(final DeleteRootEntityRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRootEntityRef);
    }
}
