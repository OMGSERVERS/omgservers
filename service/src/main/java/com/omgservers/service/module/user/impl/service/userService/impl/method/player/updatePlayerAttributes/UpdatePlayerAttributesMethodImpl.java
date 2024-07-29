package com.omgservers.service.module.user.impl.service.userService.impl.method.player.updatePlayerAttributes;

import com.omgservers.schema.module.user.UpdatePlayerAttributesRequest;
import com.omgservers.schema.module.user.UpdatePlayerAttributesResponse;
import com.omgservers.service.module.user.impl.operation.userPlayer.updatePlayerAttributes.UpdatePlayerAttributesOperation;
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
class UpdatePlayerAttributesMethodImpl implements UpdatePlayerAttributesMethod {

    final UpdatePlayerAttributesOperation updatePlayerAttributesOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdatePlayerAttributesResponse> updatePlayerAttributes(UpdatePlayerAttributesRequest request) {
        log.debug("Update player attributes, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var userId = request.getUserId();
                    final var playerId = request.getPlayerId();
                    final var attributes = request.getAttributes();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                                    updatePlayerAttributesOperation.updatePlayerAttributes(
                                            changeContext,
                                            sqlConnection,
                                            shardModel.shard(),
                                            userId,
                                            playerId,
                                            attributes))
                            .map(ChangeContext::getResult);
                })
                .map(UpdatePlayerAttributesResponse::new);
    }
}
