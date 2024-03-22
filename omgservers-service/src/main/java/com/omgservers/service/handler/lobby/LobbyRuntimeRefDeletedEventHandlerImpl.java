
package com.omgservers.service.handler.lobby;

import com.omgservers.model.dto.lobby.GetLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeRefResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.lobby.LobbyRuntimeRefDeletedEventBodyModel;
import com.omgservers.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import com.omgservers.service.factory.VersionLobbyRefModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class LobbyRuntimeRefDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    final VersionLobbyRefModelFactory versionLobbyRefModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.LOBBY_RUNTIME_REF_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (LobbyRuntimeRefDeletedEventBodyModel) event.getBody();
        final var lobbyId = body.getLobbyId();
        final var id = body.getId();

        return getLobbyRuntimeRef(lobbyId, id)
                .flatMap(lobbyRuntimeRef -> {
                    final var runtimeId = lobbyRuntimeRef.getRuntimeId();
                    log.info("Lobby runtime ref was deleted, lobbyId={}, runtimeId={} ", lobbyId, runtimeId);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<LobbyRuntimeRefModel> getLobbyRuntimeRef(final Long lobbyId, final Long id) {
        final var request = new GetLobbyRuntimeRefRequest(lobbyId, id);
        return lobbyModule.getLobbyService().getLobbyRuntimeRef(request)
                .map(GetLobbyRuntimeRefResponse::getLobbyRuntimeRef);
    }
}
