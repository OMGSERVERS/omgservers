package com.omgservers.service.shard.alias.impl.service.webService.impl.api;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.shard.alias.DeleteAliasRequest;
import com.omgservers.schema.shard.alias.DeleteAliasResponse;
import com.omgservers.schema.shard.alias.FindAliasRequest;
import com.omgservers.schema.shard.alias.FindAliasResponse;
import com.omgservers.schema.shard.alias.GetAliasRequest;
import com.omgservers.schema.shard.alias.GetAliasResponse;
import com.omgservers.schema.shard.alias.SyncAliasRequest;
import com.omgservers.schema.shard.alias.SyncAliasResponse;
import com.omgservers.schema.shard.alias.ViewAliasesRequest;
import com.omgservers.schema.shard.alias.ViewAliasesResponse;
import com.omgservers.service.shard.alias.impl.service.webService.WebService;
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
public class AliasApiImpl implements AliasApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    public Uni<GetAliasResponse> execute(final GetAliasRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindAliasResponse> execute(final FindAliasRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewAliasesResponse> execute(final ViewAliasesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncAliasResponse> execute(final SyncAliasRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteAliasResponse> execute(final DeleteAliasRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
