package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatch;

import com.omgservers.model.dto.matchmaker.DeleteMatchRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.matchmaker.impl.operation.deleteMatch.DeleteMatchOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
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
    public Uni<DeleteMatchResponse> deleteMatch(DeleteMatchRequest request) {
        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, matchmakerId, id))
                .map(DeleteMatchResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long matchmakerId, Long id) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteMatchOperation.deleteMatch(changeContext, sqlConnection, shardModel.shard(), matchmakerId, id))
                .map(ChangeContext::getResult);
    }
}
