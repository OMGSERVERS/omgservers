package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchmakerMatchCommand;

import com.omgservers.model.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandResponse;
import com.omgservers.service.module.matchmaker.impl.operation.deleteMatchmakerMatchCommand.DeleteMatchmakerMatchCommandOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchmakerMatchCommandMethodImpl implements DeleteMatchmakerMatchCommandMethod {

    final DeleteMatchmakerMatchCommandOperation deleteMatchmakerMatchCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteMatchCommandResponse> deleteMatchmakerMatchCommand(final DeleteMatchCommandRequest request) {
        log.debug("Delete match command, request={}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteMatchmakerMatchCommandOperation
                                        .deleteMatchmakerMatchCommand(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                matchmakerId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteMatchCommandResponse::new);
    }
}
