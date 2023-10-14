package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchmakerCommand;

import com.omgservers.dto.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.module.matchmaker.impl.operation.deleteMatchmakerCommand.DeleteMatchmakerCommandOperation;
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
class DeleteMatchmakerCommandMethodImpl implements DeleteMatchmakerCommandMethod {

    final DeleteMatchmakerCommandOperation deleteMatchmakerCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteMatchmakerCommandResponse> deleteMatchmakerCommand(DeleteMatchmakerCommandRequest request) {
        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteMatchmakerCommandOperation
                                        .deleteMatchmakerCommand(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                matchmakerId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteMatchmakerCommandResponse::new);
    }
}
