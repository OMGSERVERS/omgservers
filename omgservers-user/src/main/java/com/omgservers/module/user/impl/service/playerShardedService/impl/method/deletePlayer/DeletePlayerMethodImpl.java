package com.omgservers.module.user.impl.service.playerShardedService.impl.method.deletePlayer;

import com.omgservers.dto.internal.ChangeWithLogRequest;
import com.omgservers.dto.internal.ChangeWithLogResponse;
import com.omgservers.dto.user.DeletePlayerShardResponse;
import com.omgservers.dto.user.DeletePlayerShardedRequest;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.module.user.impl.operation.deletePlayer.DeletePlayerOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeletePlayerMethodImpl implements DeletePlayerMethod {

    final InternalModule internalModule;

    final DeletePlayerOperation deletePlayerOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeletePlayerShardResponse> deletePlayer(final DeletePlayerShardedRequest request) {
        DeletePlayerShardedRequest.validate(request);

        final var userId = request.getUserId();
        final var id = request.getId();
        return internalModule.getChangeService().changeWithLog(new ChangeWithLogRequest(request,
                        ((sqlConnection, shardModel) -> deletePlayerOperation
                                .deletePlayer(sqlConnection, shardModel.shard(), id)),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create(String.format("Player was deleted, " +
                                        "userId=%d, id=%d", userId, id));
                            } else {
                                return null;
                            }
                        }))
                .map(ChangeWithLogResponse::getResult)
                .map(DeletePlayerShardResponse::new);
    }
}
