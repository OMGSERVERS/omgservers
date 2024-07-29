package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
import com.omgservers.schema.module.tenant.GetVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.GetVersionLobbyRequestResponse;
import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.body.module.tenant.VersionLobbyRequestCreatedEventBodyModel;
import com.omgservers.schema.model.versionLobbyRequest.VersionLobbyRequestModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.lobby.LobbyModelFactory;
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
                    log.info("Version lobby request was created, id={}, version={}/{}, lobbyId={}",
                            versionLobbyRequest.getId(),
                            tenantId,
                            versionId,
                            lobbyId);

                    return syncLobby(versionLobbyRequest, event.getIdempotencyKey());
                })
                .replaceWithVoid();
    }

    Uni<VersionLobbyRequestModel> getVersionLobbyRequest(final Long tenantId, final Long id) {
        final var request = new GetVersionLobbyRequestRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionLobbyRequest(request)
                .map(GetVersionLobbyRequestResponse::getVersionLobbyRequest);
    }

    Uni<Boolean> syncLobby(final VersionLobbyRequestModel versionLobbyRequest,
                           final String idempotencyKey) {
        final var tenantId = versionLobbyRequest.getTenantId();
        final var versionId = versionLobbyRequest.getVersionId();
        final var lobbyId = versionLobbyRequest.getLobbyId();
        final var lobby = lobbyModelFactory.create(lobbyId,
                tenantId,
                versionId,
                idempotencyKey);
        final var request = new SyncLobbyRequest(lobby);
        return lobbyModule.getLobbyService().syncLobby(request)
                .map(SyncLobbyResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", lobby, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
