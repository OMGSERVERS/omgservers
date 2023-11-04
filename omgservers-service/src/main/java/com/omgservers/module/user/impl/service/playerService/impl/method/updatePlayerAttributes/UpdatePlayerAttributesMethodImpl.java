package com.omgservers.module.user.impl.service.playerService.impl.method.updatePlayerAttributes;

import com.omgservers.model.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.model.dto.user.UpdatePlayerAttributesResponse;
import com.omgservers.module.user.impl.operation.updatePlayerAttributes.UpdatePlayerAttributesOperation;
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
class UpdatePlayerAttributesMethodImpl implements UpdatePlayerAttributesMethod {

    final UpdatePlayerAttributesOperation updatePlayerAttributesOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdatePlayerAttributesResponse> updatePlayerAttributes(UpdatePlayerAttributesRequest request) {
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
