package com.omgservers.service.module.user.impl.service.userService.impl.method.player.deletePlayer;

import com.omgservers.schema.module.user.DeletePlayerRequest;
import com.omgservers.schema.module.user.DeletePlayerResponse;
import com.omgservers.service.module.user.impl.operation.userPlayer.deletePlayer.DeletePlayerOperation;
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
class DeletePlayerMethodImpl implements DeletePlayerMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeletePlayerOperation deletePlayerOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeletePlayerResponse> deletePlayer(final DeletePlayerRequest request) {
        log.trace("{}", request);

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
