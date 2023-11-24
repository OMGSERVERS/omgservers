package com.omgservers.service.module.user.impl.service.playerService.impl.method.updatePlayerProfile;

import com.omgservers.model.dto.user.UpdatePlayerProfileRequest;
import com.omgservers.model.dto.user.UpdatePlayerProfileResponse;
import com.omgservers.service.module.user.impl.operation.updatePlayerProfile.UpdatePlayerProfileOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
        log.debug("Update player profile, request={}", request);

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
