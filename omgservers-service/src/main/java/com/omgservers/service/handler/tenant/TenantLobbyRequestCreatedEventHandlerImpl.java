package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantLobbyRequestCreatedEventBodyModel;
import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
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
public class TenantLobbyRequestCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    final LobbyModelFactory lobbyModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_LOBBY_REQUEST_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantLobbyRequestCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionLobbyRequest(tenantId, id)
                .flatMap(versionLobbyRequest -> {
                    final var versionId = versionLobbyRequest.getDeploymentId();
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

    Uni<TenantLobbyRequestModel> getVersionLobbyRequest(final Long tenantId, final Long id) {
        final var request = new GetTenantLobbyRequestRequest(tenantId, id);
        return tenantModule.getTenantService().getVersionLobbyRequest(request)
                .map(GetTenantLobbyRequestResponse::getTenantLobbyRequest);
    }

    Uni<Boolean> syncLobby(final TenantLobbyRequestModel versionLobbyRequest,
                           final String idempotencyKey) {
        final var tenantId = versionLobbyRequest.getTenantId();
        final var versionId = versionLobbyRequest.getDeploymentId();
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
