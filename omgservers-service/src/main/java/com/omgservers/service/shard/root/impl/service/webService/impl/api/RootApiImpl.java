package com.omgservers.service.shard.root.impl.service.webService.impl.api;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.shard.root.root.DeleteRootRequest;
import com.omgservers.schema.shard.root.root.DeleteRootResponse;
import com.omgservers.schema.shard.root.root.GetRootRequest;
import com.omgservers.schema.shard.root.root.GetRootResponse;
import com.omgservers.schema.shard.root.root.SyncRootRequest;
import com.omgservers.schema.shard.root.root.SyncRootResponse;
import com.omgservers.schema.shard.root.rootEntityRef.DeleteRootEntityRefRequest;
import com.omgservers.schema.shard.root.rootEntityRef.DeleteRootEntityRefResponse;
import com.omgservers.schema.shard.root.rootEntityRef.FindRootEntityRefRequest;
import com.omgservers.schema.shard.root.rootEntityRef.FindRootEntityRefResponse;
import com.omgservers.schema.shard.root.rootEntityRef.GetRootEntityRefRequest;
import com.omgservers.schema.shard.root.rootEntityRef.GetRootEntityRefResponse;
import com.omgservers.schema.shard.root.rootEntityRef.SyncRootEntityRefRequest;
import com.omgservers.schema.shard.root.rootEntityRef.SyncRootEntityRefResponse;
import com.omgservers.schema.shard.root.rootEntityRef.ViewRootEntityRefsRequest;
import com.omgservers.schema.shard.root.rootEntityRef.ViewRootEntityRefsResponse;
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
    public Uni<GetRootResponse> execute(final GetRootRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncRootResponse> execute(final SyncRootRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteRootResponse> execute(final DeleteRootRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetRootEntityRefResponse> execute(final GetRootEntityRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindRootEntityRefResponse> execute(final FindRootEntityRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewRootEntityRefsResponse> execute(final ViewRootEntityRefsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncRootEntityRefResponse> execute(final SyncRootEntityRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteRootEntityRefResponse> execute(final DeleteRootEntityRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
