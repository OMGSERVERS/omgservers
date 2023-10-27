package com.omgservers.module.user.impl.service.playerService.impl.method.syncPlayer;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.user.SyncPlayerRequest;
import com.omgservers.dto.user.SyncPlayerResponse;
import com.omgservers.module.user.impl.operation.upsertPlayer.UpsertPlayerOperation;
import com.omgservers.module.user.impl.operation.validatePlayer.ValidatePlayerOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncPlayerMethodImpl implements SyncPlayerMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final ValidatePlayerOperation validatePlayerOperation;
    final UpsertPlayerOperation upsertPlayerOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncPlayerResponse> syncPlayer(SyncPlayerRequest request) {
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
