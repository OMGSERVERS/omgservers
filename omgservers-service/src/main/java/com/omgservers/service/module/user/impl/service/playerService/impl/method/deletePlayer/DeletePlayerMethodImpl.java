package com.omgservers.service.module.user.impl.service.playerService.impl.method.deletePlayer;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.model.dto.user.DeletePlayerRequest;
import com.omgservers.model.dto.user.DeletePlayerResponse;
import com.omgservers.service.module.user.impl.operation.deletePlayer.DeletePlayerOperation;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
        log.debug("Delete player, request={}", request);

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
