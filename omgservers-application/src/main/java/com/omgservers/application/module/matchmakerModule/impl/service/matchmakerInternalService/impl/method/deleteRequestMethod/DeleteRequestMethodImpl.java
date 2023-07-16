package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteRequestMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.matchmakerModule.impl.operation.deleteRequestOperation.DeleteRequestOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.DeleteRequestInternalResponse;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.DeleteRequestInternalRequest;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRequestMethodImpl implements DeleteRequestMethod {

    final InternalModule internalModule;

    final DeleteRequestOperation deleteRequestOperation;
    final CheckShardOperation checkShardOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;
    final PgPool pgPool;

    @Override
    public Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestInternalRequest request) {
        DeleteRequestInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var uuid = request.getUuid();
                    return deleteRequest(shard.shard(), uuid);
                })
                .map(DeleteRequestInternalResponse::new);
    }

    Uni<Boolean> deleteRequest(final int shard,
                               final UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> deleteRequestOperation
                        .deleteRequest(sqlConnection, shard, uuid))
                .invoke(deleted -> {
                    if (deleted) {
                        matchmakerInMemoryCache.removeRequest(uuid);
                    }
                });
    }
}
