package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.deleteMatch;

import com.omgservers.dto.internal.ChangeWithEventRequest;
import com.omgservers.dto.internal.ChangeWithEventResponse;
import com.omgservers.dto.matchmaker.DeleteMatchShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchShardedResponse;
import com.omgservers.model.event.body.MatchDeletedEventBodyModel;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.matchmaker.impl.operation.deleteMatch.DeleteMatchOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchMethodImpl implements DeleteMatchMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteMatchOperation deleteMatchOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteMatchShardedResponse> deleteMatch(DeleteMatchShardedRequest request) {
        DeleteMatchShardedRequest.validate(request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, matchmakerId, id))
                .map(DeleteMatchShardedResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long matchmakerId, Long id) {
        return changeWithContextOperation.changeWithContext((changeContext, sqlConnection) ->
                deleteMatchOperation.deleteMatch(changeContext, sqlConnection, shardModel.shard(), matchmakerId, id));
    }
}
