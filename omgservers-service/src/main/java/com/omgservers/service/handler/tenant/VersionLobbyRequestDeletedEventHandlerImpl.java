package com.omgservers.service.handler.tenant;

import com.omgservers.model.dto.tenant.GetVersionLobbyRequestRequest;
import com.omgservers.model.dto.tenant.GetVersionLobbyRequestResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.tenant.VersionLobbyRequestDeletedEventBodyModel;
import com.omgservers.model.versionLobbyRequest.VersionLobbyRequestModel;
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
public class VersionLobbyRequestDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_LOBBY_REQUEST_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionLobbyRequestDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionLobbyRequest(tenantId, id)
                .flatMap(versionLobbyRequest -> {
                    final var versionId = versionLobbyRequest.getVersionId();
                    final var lobbyId = versionLobbyRequest.getLobbyId();
                    log.info("Version lobby request was deleted, id={}, version={}/{}, lobbyId={}",
                            versionLobbyRequest.getId(),
                            tenantId,
                            versionId,
                            lobbyId);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<VersionLobbyRequestModel> getVersionLobbyRequest(final Long tenantId, final Long id) {
        final var request = new GetVersionLobbyRequestRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionLobbyRequest(request)
                .map(GetVersionLobbyRequestResponse::getVersionLobbyRequest);
    }
}
