package com.omgservers.service.module.user.impl.service.userService.impl.method.player.updatePlayerProfile;

import com.omgservers.schema.module.user.UpdatePlayerProfileRequest;
import com.omgservers.schema.module.user.UpdatePlayerProfileResponse;
import com.omgservers.service.module.user.impl.operation.userPlayer.updatePlayerProfile.UpdatePlayerProfileOperation;
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
class UpdatePlayerProfileMethodImpl implements UpdatePlayerProfileMethod {

    final UpdatePlayerProfileOperation updatePlayerProfileOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdatePlayerProfileResponse> updatePlayerProfile(UpdatePlayerProfileRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var userId = request.getUserId();
                    final var playerId = request.getPlayerId();
                    final var profile = request.getProfile();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                                    updatePlayerProfileOperation.updatePlayerProfile(changeContext,
                                            sqlConnection,
                                            shardModel.shard(),
                                            userId,
                                            playerId,
                                            profile))
                            .map(ChangeContext::getResult);
                })
                .map(UpdatePlayerProfileResponse::new);
    }
}
