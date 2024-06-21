package com.omgservers.service.module.user.impl.service.userService.impl.method.player.syncPlayer;

import com.omgservers.model.dto.user.SyncPlayerRequest;
import com.omgservers.model.dto.user.SyncPlayerResponse;
import com.omgservers.service.module.user.impl.operation.userPlayer.upsertPlayer.UpsertPlayerOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
        log.debug("Sync player, request={}", request);

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
