package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncRequestMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.matchmakerModule.impl.operation.upsertRequestOperation.UpsertRequestOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.application.module.matchmakerModule.model.request.RequestModel;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.SyncRequestInternalResponse;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.SyncRequestInternalRequest;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncRequestMethodImpl implements SyncRequestMethod {

    final InternalModule internalModule;

    final UpsertRequestOperation upsertRequestOperation;
    final CheckShardOperation checkShardOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;
    final PgPool pgPool;

    @Override
    public Uni<SyncRequestInternalResponse> syncRequest(SyncRequestInternalRequest request) {
        SyncRequestInternalRequest.validate(request);

        return Uni.createFrom().voidItem()
                .flatMap(validatedProject -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shard -> {
                    final var requestModel = request.getRequest();
                    return syncRequest(shard.shard(), requestModel);
                })
                .map(SyncRequestInternalResponse::new);
    }

    Uni<Boolean> syncRequest(final int shard, final RequestModel matchmakerRequest) {
        return pgPool.withTransaction(sqlConnection -> upsertRequestOperation
                        .upsertRequest(sqlConnection, shard, matchmakerRequest))
                .invoke(voidItem -> matchmakerInMemoryCache.addRequest(matchmakerRequest));
    }
}
