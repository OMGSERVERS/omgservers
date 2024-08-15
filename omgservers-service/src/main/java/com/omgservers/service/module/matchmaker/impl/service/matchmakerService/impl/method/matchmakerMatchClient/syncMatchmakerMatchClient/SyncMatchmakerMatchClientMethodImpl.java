package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.syncMatchmakerMatchClient;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchClientResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch.hasMatchmakerMatch.HasMatchmakerMatchOperation;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchClient.upsertMatchmakerMatchClient.UpsertMatchmakerMatchClientOperation;
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
class SyncMatchmakerMatchClientMethodImpl implements SyncMatchmakerMatchClientMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertMatchmakerMatchClientOperation upsertMatchmakerMatchClientOperation;
    final CheckShardOperation checkShardOperation;
    final HasMatchmakerMatchOperation hasMatchmakerMatchOperation;

    @Override
    public Uni<SyncMatchmakerMatchClientResponse> syncMatchmakerMatchClient(
            final SyncMatchmakerMatchClientRequest request) {
        log.debug("Sync matchmaker match client, request={}", request);

        final var matchClient = request.getMatchmakerMatchClient();
        final var matchmakerId = matchClient.getMatchmakerId();
        final var matchId = matchClient.getMatchId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasMatchmakerMatchOperation
                                            .hasMatchmakerMatch(sqlConnection, shard, matchmakerId, matchId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertMatchmakerMatchClientOperation
                                                            .upsertMatchmakerMatchClient(
                                                                    context,
                                                                    sqlConnection,
                                                                    shard,
                                                                    matchClient);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "match does not exist or was deleted, id=" + matchId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncMatchmakerMatchClientResponse::new);
    }
}
