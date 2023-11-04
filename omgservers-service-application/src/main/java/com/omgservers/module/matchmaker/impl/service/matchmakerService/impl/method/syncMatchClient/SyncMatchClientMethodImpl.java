package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchClient;

import com.omgservers.model.dto.matchmaker.SyncMatchClientRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchClientResponse;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.matchmaker.impl.operation.upsertMatchClient.UpsertMatchClientOperation;
import com.omgservers.factory.EventModelFactory;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchClientMethodImpl implements SyncMatchClientMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertMatchClientOperation upsertMatchClientOperation;
    final CheckShardOperation checkShardOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<SyncMatchClientResponse> syncMatchClient(SyncMatchClientRequest request) {
        final var matchClient = request.getMatchClient();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, matchClient))
                .map(SyncMatchClientResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, MatchClientModel matchClient) {
        return changeWithContextOperation.<Boolean>changeWithContext((context, sqlConnection) ->
                        upsertMatchClientOperation.upsertMatchClient(context, sqlConnection, shardModel.shard(), matchClient))
                .map(ChangeContext::getResult);
    }
}
