package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.syncMatchmakerMatch;

import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchResponse;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.matchmaker.impl.operation.matchmaker.hasMatchmaker.HasMatchmakerOperation;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch.upsertMatchmakerMatch.UpsertMatchmakerMatchOperation;
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
class SyncMatchmakerMatchMethodImpl implements SyncMatchmakerMatchMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final HasMatchmakerOperation hasMatchmakerOperation;
    final UpsertMatchmakerMatchOperation upsertMatchmakerMatchOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncMatchmakerMatchResponse> syncMatchmakerMatch(final SyncMatchmakerMatchRequest request) {
        log.debug("Sync match, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var match = request.getMatchmakerMatch();
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
                                                    return upsertMatchmakerMatchOperation.upsertMatchmakerMatch(context,
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
                .map(SyncMatchmakerMatchResponse::new);
    }
}
