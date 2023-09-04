package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.syncMatchmaker;

import com.omgservers.dto.internal.ChangeWithEventRequest;
import com.omgservers.dto.internal.ChangeWithEventResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedRequest;
import com.omgservers.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.matchmaker.impl.operation.upsertMatchmaker.UpsertMatchmakerOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchmakerMethodImpl implements SyncMatchmakerMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertMatchmakerOperation upsertMatchmakerOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncMatchmakerShardedResponse> syncMatchmaker(SyncMatchmakerShardedRequest request) {
        SyncMatchmakerShardedRequest.validate(request);

        final var matchmaker = request.getMatchmaker();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, matchmaker))
                .map(SyncMatchmakerShardedResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, MatchmakerModel matchmaker) {
        return changeWithContextOperation.changeWithContext((context, sqlConnection) ->
                upsertMatchmakerOperation.upsertMatchmaker(context, sqlConnection, shardModel.shard(), matchmaker));
    }
}
