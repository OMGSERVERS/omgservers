package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatch;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.matchmaker.SyncMatchRequest;
import com.omgservers.dto.matchmaker.SyncMatchResponse;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.system.factory.EventModelFactory;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.module.matchmaker.impl.operation.upsertMatch.UpsertMatchOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchMethodImpl implements SyncMatchMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertMatchOperation upsertMatchOperation;
    final CheckShardOperation checkShardOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<SyncMatchResponse> syncMatch(SyncMatchRequest request) {
        SyncMatchRequest.validate(request);

        final var match = request.getMatch();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, match))
                .map(SyncMatchResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, MatchModel match) {
        return changeWithContextOperation.<Boolean>changeWithContext((context, sqlConnection) ->
                        upsertMatchOperation.upsertMatch(context, sqlConnection, shardModel.shard(), match))
                .map(ChangeContext::getResult);
    }
}
