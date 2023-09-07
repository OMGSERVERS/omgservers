package com.omgservers.module.user.impl.service.playerService.impl.method.deletePlayer;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.user.DeletePlayerRequest;
import com.omgservers.dto.user.DeletePlayerResponse;
import com.omgservers.module.user.impl.operation.deletePlayer.DeletePlayerOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeletePlayerMethodImpl implements DeletePlayerMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeletePlayerOperation deletePlayerOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeletePlayerResponse> deletePlayer(final DeletePlayerRequest request) {
        DeletePlayerRequest.validate(request);

        final var userId = request.getUserId();
        final var id = request.getId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deletePlayerOperation.deletePlayer(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                userId,
                                id)))
                .map(ChangeContext::getResult)
                .map(DeletePlayerResponse::new);
    }
}
