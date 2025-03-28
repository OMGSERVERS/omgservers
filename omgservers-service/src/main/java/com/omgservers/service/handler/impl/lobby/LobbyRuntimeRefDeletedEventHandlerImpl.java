
package com.omgservers.service.handler.impl.lobby;

import com.omgservers.schema.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import com.omgservers.schema.module.lobby.GetLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.GetLobbyRuntimeRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.lobby.LobbyRuntimeRefDeletedEventBodyModel;
import com.omgservers.service.factory.tenant.TenantLobbyRefModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.lobby.LobbyShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class LobbyRuntimeRefDeletedEventHandlerImpl implements EventHandler {

    final TenantShard tenantShard;
    final LobbyShard lobbyShard;

    final TenantLobbyRefModelFactory tenantLobbyRefModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.LOBBY_RUNTIME_REF_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (LobbyRuntimeRefDeletedEventBodyModel) event.getBody();
        final var lobbyId = body.getLobbyId();
        final var id = body.getId();

        return getLobbyRuntimeRef(lobbyId, id)
                .flatMap(lobbyRuntimeRef -> {
                    log.debug("Deleted, {}", lobbyRuntimeRef);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<LobbyRuntimeRefModel> getLobbyRuntimeRef(final Long lobbyId, final Long id) {
        final var request = new GetLobbyRuntimeRefRequest(lobbyId, id);
        return lobbyShard.getService().getLobbyRuntimeRef(request)
                .map(GetLobbyRuntimeRefResponse::getLobbyRuntimeRef);
    }
}
