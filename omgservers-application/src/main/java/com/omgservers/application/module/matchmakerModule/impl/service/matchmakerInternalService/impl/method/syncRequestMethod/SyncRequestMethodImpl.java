package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncRequestMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.matchmakerModule.impl.operation.upsertRequestOperation.UpsertRequestOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.SyncRequestInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.SyncRequestInternalResponse;
import com.omgservers.application.module.matchmakerModule.model.request.RequestModel;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncRequestMethodImpl implements SyncRequestMethod {

    final InternalModule internalModule;

    final UpsertRequestOperation upsertRequestOperation;
    final CheckShardOperation checkShardOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;

    final LogModelFactory logModelFactory;
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

    Uni<Boolean> syncRequest(final int shard, final RequestModel request) {
        return pgPool.withTransaction(sqlConnection -> upsertRequestOperation
                        .upsertRequest(sqlConnection, shard, request)
                        .call(inserted -> {
                            final var syncLog = logModelFactory.create("Request was sync, request=" + request);
                            final var syncLogHelpRequest = new SyncLogHelpRequest(syncLog);
                            return internalModule.getLogHelpService().syncLog(syncLogHelpRequest);
                        }))
                .invoke(voidItem -> matchmakerInMemoryCache.addRequest(request));
    }
}
