package com.omgservers.service.module.root.impl.service.webService.impl.api;

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
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.service.module.root.impl.service.webService.WebService;
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
