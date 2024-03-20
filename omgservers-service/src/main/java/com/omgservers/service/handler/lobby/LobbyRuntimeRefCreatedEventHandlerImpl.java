package com.omgservers.service.handler.lobby;

import com.omgservers.model.dto.lobby.GetLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeRefResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.LobbyRuntimeRefCreatedEventBodyModel;
import com.omgservers.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
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
public class LobbyRuntimeRefCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.LOBBY_RUNTIME_REF_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (LobbyRuntimeRefCreatedEventBodyModel) event.getBody();
        final var lobbyId = body.getLobbyId();
        final var id = body.getId();

        return getLobbyRuntimeRef(lobbyId, id)
                .flatMap(lobbyRuntimeRef -> {
                    final var runtimeId = lobbyRuntimeRef.getRuntimeId();
                    log.info("Lobby runtime ref was created, lobbyId={}, runtimeId={} ", lobbyId, runtimeId);

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
