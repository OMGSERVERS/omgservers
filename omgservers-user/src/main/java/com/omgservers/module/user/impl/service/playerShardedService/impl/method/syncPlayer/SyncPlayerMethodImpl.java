package com.omgservers.module.user.impl.service.playerShardedService.impl.method.syncPlayer;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.user.SyncPlayerShardedRequest;
import com.omgservers.dto.user.SyncPlayerShardedResponse;
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
    public Uni<SyncPlayerShardedResponse> syncPlayer(SyncPlayerShardedRequest request) {
        SyncPlayerShardedRequest.validate(request);

        final var player = request.getPlayer();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> upsertPlayerOperation.upsertPlayer(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                player)))
                .map(ChangeContext::getResult)
                .map(SyncPlayerShardedResponse::new);
    }
}
