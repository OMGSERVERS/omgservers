package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.SyncMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.SyncMatchmakerCommandResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmaker.VerifyMatchmakerExistsOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerCommand.UpsertMatchmakerCommandOperation;
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
class SyncMatchmakerCommandMethodImpl implements SyncMatchmakerCommandMethod {

    final UpsertMatchmakerCommandOperation upsertMatchmakerCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final VerifyMatchmakerExistsOperation verifyMatchmakerExistsOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncMatchmakerCommandResponse> execute(final SyncMatchmakerCommandRequest request) {
        log.trace("{}", request);

        final var matchmakerCommand = request.getMatchmakerCommand();
        final var matchmakerId = matchmakerCommand.getMatchmakerId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyMatchmakerExistsOperation
                                            .execute(sqlConnection, shard, matchmakerId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertMatchmakerCommandOperation
                                                            .execute(changeContext,
                                                                    sqlConnection,
                                                                    shardModel.shard(),
                                                                    matchmakerCommand);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "matchmaker does not exist or was deleted, id=" + matchmakerId);
                                                }
                                            })

                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncMatchmakerCommandResponse::new);
    }
}
