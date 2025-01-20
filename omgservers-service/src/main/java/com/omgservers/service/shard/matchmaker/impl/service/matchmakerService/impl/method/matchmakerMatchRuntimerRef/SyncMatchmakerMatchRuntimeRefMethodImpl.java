package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRuntimeRefResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatch.HasMatchmakerMatchOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchRuntimeRef.UpsertMatchmakerMatchRuntimeRefOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchmakerMatchRuntimeRefMethodImpl implements SyncMatchmakerMatchRuntimeRefMethod {

    final UpsertMatchmakerMatchRuntimeRefOperation upsertMatchmakerMatchRuntimeRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasMatchmakerMatchOperation hasMatchmakerMatchOperation;

    @Override
    public Uni<SyncMatchmakerMatchRuntimeRefResponse> execute(
            final SyncMatchmakerMatchRuntimeRefRequest request) {
        log.trace("{}", request);

        final var matchRuntimeRef = request.getMatchmakerMatchRuntimeRef();
        final var matchmakerId = matchRuntimeRef.getMatchmakerId();
        final var matchId = matchRuntimeRef.getMatchId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasMatchmakerMatchOperation
                                            .execute(sqlConnection, shard, matchmakerId, matchId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertMatchmakerMatchRuntimeRefOperation.execute(
                                                            context,
                                                            sqlConnection,
                                                            shard,
                                                            matchRuntimeRef);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "match does not exist or was deleted, id=" + matchId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncMatchmakerMatchRuntimeRefResponse::new);
    }
}
