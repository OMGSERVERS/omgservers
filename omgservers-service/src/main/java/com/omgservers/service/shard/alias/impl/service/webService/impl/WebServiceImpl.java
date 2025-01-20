package com.omgservers.service.shard.alias.impl.service.webService.impl;

import com.omgservers.schema.module.alias.DeleteAliasRequest;
import com.omgservers.schema.module.alias.DeleteAliasResponse;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.schema.module.alias.GetAliasRequest;
import com.omgservers.schema.module.alias.GetAliasResponse;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.schema.module.alias.SyncAliasResponse;
import com.omgservers.schema.module.alias.ViewAliasesRequest;
import com.omgservers.schema.module.alias.ViewAliasesResponse;
import com.omgservers.service.shard.alias.impl.service.aliasService.AliasService;
import com.omgservers.service.shard.alias.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final AliasService aliasService;

    @Override
    public Uni<GetAliasResponse> execute(final GetAliasRequest request) {
        return aliasService.execute(request);
    }

    @Override
    public Uni<FindAliasResponse> execute(final FindAliasRequest request) {
        return aliasService.execute(request);
    }

    @Override
    public Uni<ViewAliasesResponse> execute(final ViewAliasesRequest request) {
        return aliasService.execute(request);
    }

    @Override
    public Uni<SyncAliasResponse> execute(final SyncAliasRequest request) {
        return aliasService.execute(request);
    }

    @Override
    public Uni<DeleteAliasResponse> execute(final DeleteAliasRequest request) {
        return aliasService.execute(request);
    }
}
