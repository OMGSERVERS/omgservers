package com.omgservers.service.shard.user.impl.service.userService.impl.method.player;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.user.SyncPlayerRequest;
import com.omgservers.schema.shard.user.SyncPlayerResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.user.impl.operation.userPlayer.UpsertPlayerOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncPlayerMethodImpl implements SyncPlayerMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertPlayerOperation upsertPlayerOperation;

    @Override
    public Uni<SyncPlayerResponse> syncPlayer(final ShardModel shardModel,
                                              final SyncPlayerRequest request) {
        log.trace("{}", request);

        final var player = request.getPlayer();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> upsertPlayerOperation.execute(
                                changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                player))
                .map(ChangeContext::getResult)
                .map(SyncPlayerResponse::new);
    }
}
