package com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.lobbyRuntimeRef.deleteLobbyRuntimeRef;

import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefResponse;
import com.omgservers.service.module.lobby.impl.operation.lobbyRuntimeRef.deleteLobbyRuntimeRef.DeleteLobbyRuntimeRefOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteLobbyRuntimeRefMethodImpl implements DeleteLobbyRuntimeRefMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteLobbyRuntimeRefOperation deleteLobbyRuntimeRefOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteLobbyRuntimeRefResponse> deleteLobbyRuntimeRef(final DeleteLobbyRuntimeRefRequest request) {
        log.debug("Requested, {}", request);

        final var lobbyId = request.getLobbyId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteLobbyRuntimeRefOperation
                                        .deleteLobbyRuntimeRef(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                lobbyId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteLobbyRuntimeRefResponse::new);
    }
}
