package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchResponse;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatch.DeleteMatchmakerMatchOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchmakerMatchMethodImpl implements DeleteMatchmakerMatchMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteMatchmakerMatchOperation deleteMatchmakerMatchOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteMatchmakerMatchResponse> execute(
            final DeleteMatchmakerMatchRequest request) {
        log.trace("{}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, matchmakerId, id))
                .map(DeleteMatchmakerMatchResponse::new);
    }

    Uni<Boolean> changeFunction(final ShardModel shardModel,
                                final Long matchmakerId,
                                final Long id) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteMatchmakerMatchOperation.execute(changeContext, sqlConnection, shardModel.shard(),
                                matchmakerId, id))
                .map(ChangeContext::getResult);
    }
}
