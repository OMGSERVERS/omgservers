package com.omgservers.service.shard.root.impl.service.webService.impl;

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
import com.omgservers.service.shard.root.impl.service.rootService.RootService;
import com.omgservers.service.shard.root.impl.service.webService.WebService;
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
    public Uni<GetRootResponse> execute(final GetRootRequest request) {
        return rootService.execute(request);
    }

    @Override
    public Uni<SyncRootResponse> execute(final SyncRootRequest request) {
        return rootService.execute(request);
    }

    @Override
    public Uni<DeleteRootResponse> execute(final DeleteRootRequest request) {
        return rootService.execute(request);
    }

    @Override
    public Uni<GetRootEntityRefResponse> execute(final GetRootEntityRefRequest request) {
        return rootService.execute(request);
    }

    @Override
    public Uni<FindRootEntityRefResponse> execute(final FindRootEntityRefRequest request) {
        return rootService.execute(request);
    }

    @Override
    public Uni<ViewRootEntityRefsResponse> execute(final ViewRootEntityRefsRequest request) {
        return rootService.execute(request);
    }

    @Override
    public Uni<SyncRootEntityRefResponse> execute(final SyncRootEntityRefRequest request) {
        return rootService.execute(request);
    }

    @Override
    public Uni<DeleteRootEntityRefResponse> execute(final DeleteRootEntityRefRequest request) {
        return rootService.execute(request);
    }
}
