package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.deleteRequest;

import com.omgservers.dto.internal.ChangeWithLogRequest;
import com.omgservers.dto.internal.ChangeWithLogResponse;
import com.omgservers.dto.matchmaker.DeleteRequestShardResponse;
import com.omgservers.dto.matchmaker.DeleteRequestShardedRequest;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.matchmaker.impl.operation.deleteRequest.DeleteRequestOperation;
import com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.MatchmakerInMemoryCache;
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

    final MatchmakerInMemoryCache matchmakerInMemoryCache;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteRequestShardResponse> deleteRequest(DeleteRequestShardedRequest request) {
        DeleteRequestShardedRequest.validate(request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        final var changeWithLogRequest = new ChangeWithLogRequest(request,
                (sqlConnection, shardModel) -> deleteRequestOperation
                        .deleteRequest(sqlConnection, shardModel.shard(), id),
                deleted -> {
                    if (deleted) {
                        return logModelFactory.create(String.format("Request was deleted, " +
                                "matchmakerId=%d, id=%d", matchmakerId, id));
                    } else {
                        return null;
                    }
                });
        return internalModule.getChangeService().changeWithLog(changeWithLogRequest)
                .map(ChangeWithLogResponse::getResult)
                .invoke(deleted -> matchmakerInMemoryCache.removeRequest(id))
                .map(DeleteRequestShardResponse::new);
    }
}
