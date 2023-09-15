package com.omgservers.module.user.impl.service.attributeService.impl.method.deleteAttribute;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.user.DeleteAttributeRequest;
import com.omgservers.dto.user.DeleteAttributeResponse;
import com.omgservers.module.user.impl.operation.deleteAttribute.DeleteAttributeOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteAttributeMethodImpl implements DeleteAttributeMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteAttributeOperation deleteAttributeOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteAttributeResponse> deleteAttribute(final DeleteAttributeRequest request) {
        final var userId = request.getUserId();
        final var playerId = request.getPlayerId();
        final var name = request.getName();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deleteAttributeOperation.deleteAttribute(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                userId,
                                playerId,
                                name)))
                .map(ChangeContext::getResult)
                .map(DeleteAttributeResponse::new);
    }
}
