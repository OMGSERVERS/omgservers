package com.omgservers.service.shard.alias.impl.service.aliasService.impl;

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
import com.omgservers.service.operation.server.CalculateShardOperation;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
import com.omgservers.service.shard.alias.impl.operation.getAliasModuleClient.GetAliasModuleClientOperation;
import com.omgservers.service.shard.alias.impl.service.aliasService.AliasService;
import com.omgservers.service.shard.alias.impl.service.aliasService.impl.method.DeleteAliasMethod;
import com.omgservers.service.shard.alias.impl.service.aliasService.impl.method.FindAliasMethod;
import com.omgservers.service.shard.alias.impl.service.aliasService.impl.method.GetAliasMethod;
import com.omgservers.service.shard.alias.impl.service.aliasService.impl.method.SyncAliasMethod;
import com.omgservers.service.shard.alias.impl.service.aliasService.impl.method.ViewAliasesMethod;
import com.omgservers.service.shard.alias.impl.service.webService.impl.api.AliasApi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AliasServiceImpl implements AliasService {

    final DeleteAliasMethod deleteAliasMethod;
    final ViewAliasesMethod viewAliasesMethod;
    final FindAliasMethod findAliasMethod;
    final SyncAliasMethod syncAliasMethod;
    final GetAliasMethod getAliasMethod;

    final HandleShardedRequestOperation handleShardedRequestOperation;
    final GetAliasModuleClientOperation getAliasModuleClientOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetAliasResponse> execute(@Valid final GetAliasRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getAliasModuleClientOperation::execute,
                AliasApi::execute,
                getAliasMethod::execute);
    }

    @Override
    public Uni<FindAliasResponse> execute(@Valid final FindAliasRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getAliasModuleClientOperation::execute,
                AliasApi::execute,
                findAliasMethod::execute);
    }

    @Override
    public Uni<ViewAliasesResponse> execute(@Valid final ViewAliasesRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getAliasModuleClientOperation::execute,
                AliasApi::execute,
                viewAliasesMethod::execute);
    }

    @Override
    public Uni<SyncAliasResponse> execute(@Valid final SyncAliasRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getAliasModuleClientOperation::execute,
                AliasApi::execute,
                syncAliasMethod::execute);
    }

    @Override
    public Uni<DeleteAliasResponse> execute(@Valid final DeleteAliasRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getAliasModuleClientOperation::execute,
                AliasApi::execute,
                deleteAliasMethod::execute);
    }
}
