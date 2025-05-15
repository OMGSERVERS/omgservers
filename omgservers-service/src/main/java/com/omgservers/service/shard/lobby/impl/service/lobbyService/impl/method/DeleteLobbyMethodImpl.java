package com.omgservers.service.shard.lobby.impl.service.lobbyService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.lobby.DeleteLobbyRequest;
import com.omgservers.schema.shard.lobby.DeleteLobbyResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.lobby.impl.operation.lobby.DeleteLobbyOperation;
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

    @Override
    public Uni<DeleteLobbyResponse> execute(final ShardModel shardModel,
                                            final DeleteLobbyRequest request) {
        log.debug("{}", request);

        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteLobbyOperation.execute(changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                id))
                .map(ChangeContext::getResult)
                .map(DeleteLobbyResponse::new);
    }
}
