package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.matchmaker.impl.operation.matchmaker.HasMatchmakerOperation;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch.UpsertMatchmakerMatchOperation;
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
    public Uni<SyncMatchmakerMatchResponse> execute(final SyncMatchmakerMatchRequest request) {
        log.trace("Requested, {}", request);

        final var shardKey = request.getRequestShardKey();
        final var match = request.getMatchmakerMatch();
        final var matchmakerId = match.getMatchmakerId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasMatchmakerOperation
                                            .execute(sqlConnection, shard, matchmakerId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertMatchmakerMatchOperation.execute(context,
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
