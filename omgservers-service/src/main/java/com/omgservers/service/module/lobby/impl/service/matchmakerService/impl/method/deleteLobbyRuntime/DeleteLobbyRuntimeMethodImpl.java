package com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.deleteLobbyRuntime;

import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeResponse;
import com.omgservers.service.module.lobby.impl.operation.deleteLobbyRuntime.DeleteLobbyRuntimeOperation;
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
class DeleteLobbyRuntimeMethodImpl implements DeleteLobbyRuntimeMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteLobbyRuntimeOperation deleteLobbyRuntimeOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteLobbyRuntimeResponse> deleteLobbyRuntime(final DeleteLobbyRuntimeRequest request) {
        log.debug("Delete lobby runtime, request={}", request);

        final var lobbyId = request.getLobbyId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteLobbyRuntimeOperation
                                        .deleteLobbyRuntime(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                lobbyId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteLobbyRuntimeResponse::new);
    }
}
