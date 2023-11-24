package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchClient;

import com.omgservers.model.dto.matchmaker.SyncMatchClientRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchClientResponse;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.matchmaker.impl.operation.hasMatch.HasMatchOperation;
import com.omgservers.service.module.matchmaker.impl.operation.upsertMatchClient.UpsertMatchClientOperation;
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
class SyncMatchClientMethodImpl implements SyncMatchClientMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertMatchClientOperation upsertMatchClientOperation;
    final CheckShardOperation checkShardOperation;
    final HasMatchOperation hasMatchOperation;

    @Override
    public Uni<SyncMatchClientResponse> syncMatchClient(final SyncMatchClientRequest request) {
        log.debug("Delete match client, request={}", request);

        final var matchClient = request.getMatchClient();
        final var matchmakerId = matchClient.getMatchmakerId();
        final var matchId = matchClient.getMatchId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasMatchOperation
                                            .hasMatch(sqlConnection, shard, matchmakerId, matchId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertMatchClientOperation.upsertMatchClient(context,
                                                            sqlConnection,
                                                            shard,
                                                            matchClient);
                                                } else {
                                                    throw new ServerSideConflictException(
                                                            "match does not exist or was deleted, " +
                                                                    "id=" + matchId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncMatchClientResponse::new);
    }
}
