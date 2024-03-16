package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmakerMatchCommand;

import com.omgservers.model.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandResponse;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.matchmaker.impl.operation.hasMatchmakerMatch.HasMatchmakerMatchOperation;
import com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmakerMatchCommand.UpsertMatchmakerMatchCommandOperation;
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
class SyncMatchmakerMatchCommandMethodImpl implements SyncMatchmakerMatchCommandMethod {

    final UpsertMatchmakerMatchCommandOperation upsertMatchmakerMatchCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasMatchmakerMatchOperation hasMatchmakerMatchOperation;

    @Override
    public Uni<SyncMatchCommandResponse> syncMatchmakerMatchCommand(final SyncMatchCommandRequest request) {
        log.debug("Sync match command, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var matchCommand = request.getMatchCommand();
        final var matchmakerId = matchCommand.getMatchmakerId();
        final var matchId = matchCommand.getMatchId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasMatchmakerMatchOperation
                                            .hasMatchmakerMatch(sqlConnection, shard, matchmakerId, matchId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertMatchmakerMatchCommandOperation
                                                            .upsertMatchmakerMatchCommand(changeContext,
                                                                    sqlConnection,
                                                                    shardModel.shard(),
                                                                    matchCommand);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "match does not exist or was deleted, id=" + matchId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncMatchCommandResponse::new);
    }
}
