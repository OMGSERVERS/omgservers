package com.omgservers.module.user.impl.service.playerService.impl.method.updatePlayerObject;

import com.omgservers.dto.user.UpdatePlayerObjectRequest;
import com.omgservers.dto.user.UpdatePlayerObjectResponse;
import com.omgservers.module.user.impl.operation.updatePlayerObject.UpdatePlayerObjectOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdatePlayerObjectMethodImpl implements UpdatePlayerObjectMethod {

    final UpdatePlayerObjectOperation updatePlayerObjectOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdatePlayerObjectResponse> updatePlayerObject(UpdatePlayerObjectRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var userId = request.getUserId();
                    final var playerId = request.getPlayerId();
                    final var object = request.getObject();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                                    updatePlayerObjectOperation.updatePlayerObject(changeContext,
                                            sqlConnection,
                                            shardModel.shard(),
                                            userId,
                                            playerId,
                                            object))
                            .map(ChangeContext::getResult);
                })
                .map(UpdatePlayerObjectResponse::new);
    }
}
