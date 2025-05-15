package com.omgservers.service.shard.user.impl.service.userService.impl.method.player;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.user.DeletePlayerRequest;
import com.omgservers.schema.shard.user.DeletePlayerResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.user.impl.operation.userPlayer.DeletePlayerOperation;
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

    @Override
    public Uni<DeletePlayerResponse> deletePlayer(final ShardModel shardModel,
                                                  final DeletePlayerRequest request) {
        log.debug("{}", request);

        final var userId = request.getUserId();
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deletePlayerOperation.execute(changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                userId,
                                id))
                .map(ChangeContext::getResult)
                .map(DeletePlayerResponse::new);
    }
}
