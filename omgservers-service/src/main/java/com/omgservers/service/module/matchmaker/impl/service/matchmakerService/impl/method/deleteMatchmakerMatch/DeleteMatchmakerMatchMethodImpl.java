package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchmakerMatch;

import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.service.module.matchmaker.impl.operation.deleteMatchmakerMatch.DeleteMatchmakerMatchOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    public Uni<DeleteMatchmakerMatchResponse> deleteMatchmakerMatch(
            final DeleteMatchmakerMatchRequest request) {
        log.debug("Delete match, request={}", request);

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
                        deleteMatchmakerMatchOperation.deleteMatchmakerMatch(changeContext, sqlConnection, shardModel.shard(),
                                matchmakerId, id))
                .map(ChangeContext::getResult);
    }
}
