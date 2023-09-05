package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.syncMatchClient;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.matchmaker.SyncMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchClientShardedResponse;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.matchmaker.impl.operation.upsertMatchClient.UpsertMatchClientOperation;
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
    public Uni<SyncMatchClientShardedResponse> syncMatchClient(SyncMatchClientShardedRequest request) {
        SyncMatchClientShardedRequest.validate(request);

        final var matchClient = request.getMatchClient();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, matchClient))
                .map(SyncMatchClientShardedResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, MatchClientModel matchClient) {
        return changeWithContextOperation.<Boolean>changeWithContext((context, sqlConnection) ->
                        upsertMatchClientOperation.upsertMatchClient(context, sqlConnection, shardModel.shard(), matchClient))
                .map(ChangeContext::getResult);
    }
}
