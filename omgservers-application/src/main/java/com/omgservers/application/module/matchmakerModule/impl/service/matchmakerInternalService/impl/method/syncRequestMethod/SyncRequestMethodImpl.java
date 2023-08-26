package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncRequestMethod;

import com.omgservers.application.module.matchmakerModule.impl.operation.upsertRequestOperation.UpsertRequestOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithLogRequest;
import com.omgservers.dto.internalModule.ChangeWithLogResponse;
import com.omgservers.dto.matchmakerModule.SyncRequestShardRequest;
import com.omgservers.dto.matchmakerModule.SyncRequestInternalResponse;
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
    public Uni<SyncRequestInternalResponse> syncRequest(SyncRequestShardRequest request) {
        SyncRequestShardRequest.validate(request);

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
                .map(SyncRequestInternalResponse::new);
    }
}
