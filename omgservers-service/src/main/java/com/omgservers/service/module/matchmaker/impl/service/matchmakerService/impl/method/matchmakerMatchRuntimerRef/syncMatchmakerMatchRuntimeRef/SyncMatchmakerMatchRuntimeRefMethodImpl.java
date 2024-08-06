package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.syncMatchmakerMatchRuntimeRef;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRuntimeRefResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch.hasMatchmakerMatch.HasMatchmakerMatchOperation;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchRuntimeRef.upsertMatchmakerMatchRuntimeRef.UpsertMatchmakerMatchRuntimeRefOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import com.omgservers.service.server.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
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
    public Uni<SyncMatchmakerMatchRuntimeRefResponse> syncMatchmakerMatchRuntimeRef(
            final SyncMatchmakerMatchRuntimeRefRequest request) {
        log.debug("Sync matchmaker match runtime ref, request={}", request);

        final var matchRuntimeRef = request.getMatchmakerMatchRuntimeRef();
        final var matchmakerId = matchRuntimeRef.getMatchmakerId();
        final var matchId = matchRuntimeRef.getMatchId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasMatchmakerMatchOperation
                                            .hasMatchmakerMatch(sqlConnection, shard, matchmakerId, matchId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertMatchmakerMatchRuntimeRefOperation.upsertMatchmakerMatchRuntimeRef(
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
