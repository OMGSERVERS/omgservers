package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatch;

import com.omgservers.model.dto.matchmaker.SyncMatchRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchResponse;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.matchmaker.impl.operation.hasMatchmaker.HasMatchmakerOperation;
import com.omgservers.service.module.matchmaker.impl.operation.upsertMatch.UpsertMatchOperation;
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
class SyncMatchMethodImpl implements SyncMatchMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final HasMatchmakerOperation hasMatchmakerOperation;
    final UpsertMatchOperation upsertMatchOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncMatchResponse> syncMatch(final SyncMatchRequest request) {
        log.debug("Sync match, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var match = request.getMatch();
        final var matchmakerId = match.getMatchmakerId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasMatchmakerOperation
                                            .hasMatchmaker(sqlConnection, shard, matchmakerId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertMatchOperation.upsertMatch(context,
                                                            sqlConnection,
                                                            shard,
                                                            match);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "matchmaker does not exist or was deleted, id=" +
                                                                    matchmakerId);
                                                }
                                            }))
                            .map(ChangeContext::getResult);
                })
                .map(SyncMatchResponse::new);
    }
}
