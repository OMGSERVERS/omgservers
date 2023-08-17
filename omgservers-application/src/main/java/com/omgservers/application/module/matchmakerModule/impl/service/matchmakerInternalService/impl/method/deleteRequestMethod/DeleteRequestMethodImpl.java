package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteRequestMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.matchmakerModule.impl.operation.deleteRequestOperation.DeleteRequestOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.DeleteRequestInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.DeleteRequestInternalResponse;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRequestMethodImpl implements DeleteRequestMethod {

    final InternalModule internalModule;

    final DeleteRequestOperation deleteRequestOperation;
    final CheckShardOperation checkShardOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestInternalRequest request) {
        DeleteRequestInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return deleteRequest(shard.shard(), id);
                })
                .map(DeleteRequestInternalResponse::new);
    }

    Uni<Boolean> deleteRequest(final int shard,
                               final Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteRequestOperation
                        .deleteRequest(sqlConnection, shard, id)
                        .call(deleted -> {
                            if (deleted) {
                                final var syncLog = logModelFactory.create("Request was deleted, id=" + id);
                                final var syncLogHelpRequest = new SyncLogHelpRequest(syncLog);
                                return internalModule.getLogHelpService().syncLog(syncLogHelpRequest);
                            } else {
                                return Uni.createFrom().voidItem();
                            }
                        }))
                .invoke(deleted -> {
                    if (deleted) {
                        matchmakerInMemoryCache.removeRequest(id);
                    }
                });
    }
}
