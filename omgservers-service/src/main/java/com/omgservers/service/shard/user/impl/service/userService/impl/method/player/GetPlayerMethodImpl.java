package com.omgservers.service.shard.user.impl.service.userService.impl.method.player;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.user.GetPlayerRequest;
import com.omgservers.schema.shard.user.GetPlayerResponse;
import com.omgservers.service.shard.user.impl.operation.userPlayer.SelectPlayerOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPlayerMethodImpl implements GetPlayerMethod {

    final SelectPlayerOperation selectPlayerOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetPlayerResponse> getPlayer(final ShardModel shardModel,
                                            final GetPlayerRequest request) {
        log.debug("{}", request);

        final var userId = request.getUserId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectPlayerOperation
                        .execute(sqlConnection, shardModel.slot(), userId, id))
                .map(GetPlayerResponse::new);
    }
}
