package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.syncRequest;

import com.omgservers.dto.internal.ChangeWithLogRequest;
import com.omgservers.dto.internal.ChangeWithLogResponse;
import com.omgservers.dto.matchmaker.SyncRequestShardResponse;
import com.omgservers.dto.matchmaker.SyncRequestShardedRequest;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.matchmaker.impl.operation.upsertRequest.UpsertRequestOperation;
import com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.MatchmakerInMemoryCache;
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

    final MatchmakerInMemoryCache matchmakerInMemoryCache;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncRequestShardResponse> syncRequest(SyncRequestShardedRequest request) {
        SyncRequestShardedRequest.validate(request);

        final var requestModel = request.getRequest();
        return internalModule.getChangeService().changeWithLog(new ChangeWithLogRequest(request,
                        (sqlConnection, shardModel) -> upsertRequestOperation
                                .upsertRequest(sqlConnection, shardModel.shard(), requestModel),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Request was created, request=" + requestModel);
                            } else {
                                return logModelFactory.create("Request was updated, request=" + requestModel);
                            }
                        }
                ))
                .map(ChangeWithLogResponse::getResult)
                .invoke(inserted -> matchmakerInMemoryCache.addRequest(requestModel))
                .map(SyncRequestShardResponse::new);
    }
}
