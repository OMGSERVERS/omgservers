package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncRequestMethod;

import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.base.InternalModule;
import com.omgservers.application.module.matchmakerModule.impl.operation.upsertRequestOperation.UpsertRequestOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.base.impl.operation.changeOperation.ChangeOperation;
import com.omgservers.dto.matchmakerModule.SyncRequestInternalRequest;
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
    final ChangeOperation changeOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncRequestInternalResponse> syncRequest(SyncRequestInternalRequest request) {
        SyncRequestInternalRequest.validate(request);

        final var requestModel = request.getRequest();
        return changeOperation.changeWithLog(request,
                        (sqlConnection, shardModel) -> upsertRequestOperation
                                .upsertRequest(sqlConnection, shardModel.shard(), requestModel),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Request was created, request=" + requestModel);
                            } else {
                                return logModelFactory.create("Request was updated, request=" + requestModel);
                            }
                        }
                )
                .invoke(inserted -> matchmakerInMemoryCache.addRequest(requestModel))
                .map(SyncRequestInternalResponse::new);
    }
}
