package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchCommand;

import com.omgservers.model.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandResponse;
import com.omgservers.module.matchmaker.impl.operation.deleteMatchCommand.DeleteMatchCommandOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchCommandMethodImpl implements DeleteMatchCommandMethod {

    final DeleteMatchCommandOperation deleteMatchCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteMatchCommandResponse> deleteMatchCommand(final DeleteMatchCommandRequest request) {
        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteMatchCommandOperation
                                        .deleteMatchCommand(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                matchmakerId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteMatchCommandResponse::new);
    }
}
