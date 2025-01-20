package com.omgservers.service.shard.user.impl.service.userService.impl.method.player.syncPlayer;

import com.omgservers.schema.module.user.SyncPlayerRequest;
import com.omgservers.schema.module.user.SyncPlayerResponse;
import com.omgservers.service.shard.user.impl.operation.userPlayer.upsertPlayer.UpsertPlayerOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncPlayerResponse> syncPlayer(final SyncPlayerRequest request) {
        log.trace("{}", request);

        final var player = request.getPlayer();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> upsertPlayerOperation.upsertPlayer(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                player)))
                .map(ChangeContext::getResult)
                .map(SyncPlayerResponse::new);
    }
}
