package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchCommand;

import com.omgservers.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.dto.matchmaker.SyncMatchCommandResponse;
import com.omgservers.module.matchmaker.impl.operation.upsertMatchCommand.UpsertMatchCommandOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchCommandMethodImpl implements SyncMatchCommandMethod {

    final UpsertMatchCommandOperation upsertMatchCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncMatchCommandResponse> syncMatchCommand(final SyncMatchCommandRequest request) {
        final var matchCommand = request.getMatchCommand();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> upsertMatchCommandOperation
                                        .upsertMatchCommand(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                matchCommand))
                        .map(ChangeContext::getResult))
                .map(SyncMatchCommandResponse::new);
    }
}
