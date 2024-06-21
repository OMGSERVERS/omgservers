package com.omgservers.service.module.root.impl.service.webService.impl;

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
import com.omgservers.service.module.root.impl.service.rootService.RootService;
import com.omgservers.service.module.root.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final RootService rootService;

    @Override
    public Uni<GetRootResponse> getRoot(final GetRootRequest request) {
        return rootService.getRoot(request);
    }

    @Override
    public Uni<SyncRootResponse> syncRoot(final SyncRootRequest request) {
        return rootService.syncRoot(request);
    }

    @Override
    public Uni<DeleteRootResponse> deleteRoot(final DeleteRootRequest request) {
        return rootService.deleteRoot(request);
    }

    @Override
    public Uni<GetRootEntityRefResponse> getRootEntityRef(final GetRootEntityRefRequest request) {
        return rootService.getRootEntityRef(request);
    }

    @Override
    public Uni<FindRootEntityRefResponse> findRootEntityRef(final FindRootEntityRefRequest request) {
        return rootService.findRootEntityRef(request);
    }

    @Override
    public Uni<ViewRootEntityRefsResponse> viewRootEntityRefs(final ViewRootEntityRefsRequest request) {
        return rootService.viewRootEntityRefs(request);
    }

    @Override
    public Uni<SyncRootEntityRefResponse> syncRootEntityRef(final SyncRootEntityRefRequest request) {
        return rootService.syncRootEntityRef(request);
    }

    @Override
    public Uni<DeleteRootEntityRefResponse> deleteRootEntityRef(final DeleteRootEntityRefRequest request) {
        return rootService.deleteRootEntityRef(request);
    }
}
