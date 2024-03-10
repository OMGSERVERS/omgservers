package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchRuntimeRef;

import com.omgservers.model.dto.matchmaker.SyncMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchRuntimeRefResponse;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.matchmaker.impl.operation.hasMatch.HasMatchOperation;
import com.omgservers.service.module.matchmaker.impl.operation.upsertMatchRuntimeRef.UpsertMatchRuntimeRefOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchRuntimeRefMethodImpl implements SyncMatchRuntimeRefMethod {

    final UpsertMatchRuntimeRefOperation upsertMatchRuntimeRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasMatchOperation hasMatchOperation;

    @Override
    public Uni<SyncMatchRuntimeRefResponse> syncMatchRuntimeRef(final SyncMatchRuntimeRefRequest request) {
        log.debug("Sync match runtime ref, request={}", request);

        final var matchRuntimeRef = request.getMatchRuntimeRef();
        final var matchmakerId = matchRuntimeRef.getMatchmakerId();
        final var matchId = matchRuntimeRef.getMatchId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasMatchOperation
                                            .hasMatch(sqlConnection, shard, matchmakerId, matchId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertMatchRuntimeRefOperation.upsertMatchRuntimeRef(context,
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
                .map(SyncMatchRuntimeRefResponse::new);
    }
}
