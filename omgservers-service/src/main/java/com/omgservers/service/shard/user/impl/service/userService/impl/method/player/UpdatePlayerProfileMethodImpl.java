package com.omgservers.service.shard.user.impl.service.userService.impl.method.player;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.user.UpdatePlayerProfileRequest;
import com.omgservers.schema.shard.user.UpdatePlayerProfileResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.user.impl.operation.userPlayer.UpdatePlayerProfileOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdatePlayerProfileMethodImpl implements UpdatePlayerProfileMethod {

    final UpdatePlayerProfileOperation updatePlayerProfileOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<UpdatePlayerProfileResponse> updatePlayerProfile(final ShardModel shardModel,
                                                                final UpdatePlayerProfileRequest request) {
        log.trace("{}", request);

        final var userId = request.getUserId();
        final var playerId = request.getPlayerId();
        final var profile = request.getProfile();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        updatePlayerProfileOperation.execute(changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                userId,
                                playerId,
                                profile))
                .map(ChangeContext::getResult)
                .map(UpdatePlayerProfileResponse::new);
    }
}
