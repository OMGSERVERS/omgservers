package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.tenant.GetVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.GetVersionLobbyRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.VersionLobbyRefDeletedEventBodyModel;
import com.omgservers.schema.model.versionLobbyRef.VersionLobbyRefModel;
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
public class VersionLobbyRefDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_LOBBY_REF_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionLobbyRefDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionLobbyRef(tenantId, id)
                .flatMap(versionLobbyRef -> {
                    final var versionId = versionLobbyRef.getVersionId();
                    final var lobbyId = versionLobbyRef.getLobbyId();

                    log.info("Version lobby ref was deleted, id={}, version={}/{}, lobbyId={}",
                            versionLobbyRef.getId(),
                            tenantId,
                            versionId,
                            lobbyId);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<VersionLobbyRefModel> getVersionLobbyRef(final Long tenantId, final Long id) {
        final var request = new GetVersionLobbyRefRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionLobbyRef(request)
                .map(GetVersionLobbyRefResponse::getVersionLobbyRef);
    }
}
