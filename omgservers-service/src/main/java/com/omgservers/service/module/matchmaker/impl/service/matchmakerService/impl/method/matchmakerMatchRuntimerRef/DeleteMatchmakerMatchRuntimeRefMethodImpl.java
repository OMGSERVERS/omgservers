package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRuntimeRefResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchRuntimeRef.DeleteMatchmakerMatchRuntimeRefOperation;
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
class DeleteMatchmakerMatchRuntimeRefMethodImpl implements DeleteMatchmakerMatchRuntimeRefMethod {

    final DeleteMatchmakerMatchRuntimeRefOperation deleteMatchmakerMatchRuntimeRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteMatchmakerMatchRuntimeRefResponse> execute(final DeleteMatchmakerMatchRuntimeRefRequest request) {
        log.debug("Requested, {}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getMatchId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteMatchmakerMatchRuntimeRefOperation
                                        .execute(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                matchmakerId,
                                                matchId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteMatchmakerMatchRuntimeRefResponse::new);
    }
}
