package com.omgservers.service.shard.lobby.impl.service.lobbyService.impl.method.lobby.deleteLobby;

import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import com.omgservers.service.shard.lobby.impl.operation.lobby.deleteLobby.DeleteLobbyOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteLobbyMethodImpl implements DeleteLobbyMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteLobbyOperation deleteLobbyOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteLobbyResponse> deleteLobby(final DeleteLobbyRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteLobbyOperation.deleteLobby(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        id))
                        .map(ChangeContext::getResult))
                .map(DeleteLobbyResponse::new);
    }
}
