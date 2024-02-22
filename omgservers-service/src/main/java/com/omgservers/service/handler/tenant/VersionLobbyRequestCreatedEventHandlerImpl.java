package com.omgservers.service.handler.tenant;

import com.omgservers.model.dto.lobby.SyncLobbyRequest;
import com.omgservers.model.dto.lobby.SyncLobbyResponse;
import com.omgservers.model.dto.tenant.GetVersionLobbyRequestRequest;
import com.omgservers.model.dto.tenant.GetVersionLobbyRequestResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionLobbyRequestCreatedEventBodyModel;
import com.omgservers.model.versionLobbyRequest.VersionLobbyRequestModel;
import com.omgservers.service.factory.LobbyModelFactory;
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
public class VersionLobbyRequestCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    final LobbyModelFactory lobbyModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_LOBBY_REQUEST_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionLobbyRequestCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionLobbyRequest(tenantId, id)
                .flatMap(versionLobbyRequest -> {
                    final var versionId = versionLobbyRequest.getVersionId();
                    final var lobbyId = versionLobbyRequest.getLobbyId();
                    log.info("Version lobby request was created, version={}/{}, lobbyId={}",
                            tenantId,
                            versionId,
                            lobbyId);

                    return syncLobby(versionLobbyRequest);
                })
                .replaceWithVoid();
    }

    Uni<VersionLobbyRequestModel> getVersionLobbyRequest(final Long tenantId, final Long id) {
        final var request = new GetVersionLobbyRequestRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionLobbyRequest(request)
                .map(GetVersionLobbyRequestResponse::getVersionLobbyRequest);
    }

    Uni<Boolean> syncLobby(final VersionLobbyRequestModel versionLobbyRequest) {
        final var tenantId = versionLobbyRequest.getTenantId();
        final var versionId = versionLobbyRequest.getVersionId();
        final var lobbyId = versionLobbyRequest.getLobbyId();
        final var lobby = lobbyModelFactory.create(lobbyId, tenantId, versionId);
        final var request = new SyncLobbyRequest(lobby);
        return lobbyModule.getLobbyService().syncLobby(request)
                .map(SyncLobbyResponse::getCreated);
    }
}
