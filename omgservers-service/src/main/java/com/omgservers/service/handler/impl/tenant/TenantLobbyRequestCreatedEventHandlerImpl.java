package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantLobbyRequestCreatedEventBodyModel;
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
        log.trace("Handle event, {}", event);

        final var body = (TenantLobbyRequestCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantLobbyRequest(tenantId, id)
                .flatMap(tenantLobbyRequest -> {
                    log.debug("Created, {}", tenantLobbyRequest);

                    return syncLobby(tenantLobbyRequest, event.getIdempotencyKey());
                })
                .replaceWithVoid();
    }

    Uni<TenantLobbyRequestModel> getTenantLobbyRequest(final Long tenantId, final Long id) {
        final var request = new GetTenantLobbyRequestRequest(tenantId, id);
        return tenantModule.getService().getTenantLobbyRequest(request)
                .map(GetTenantLobbyRequestResponse::getTenantLobbyRequest);
    }

    Uni<Boolean> syncLobby(final TenantLobbyRequestModel tenantLobbyRequest,
                           final String idempotencyKey) {
        final var tenantId = tenantLobbyRequest.getTenantId();
        final var deploymentId = tenantLobbyRequest.getDeploymentId();
        final var lobbyId = tenantLobbyRequest.getLobbyId();
        final var lobby = lobbyModelFactory.create(lobbyId,
                tenantId,
                deploymentId,
                idempotencyKey);
        final var request = new SyncLobbyRequest(lobby);
        return lobbyModule.getService().syncLobbyWithIdempotency(request)
                .map(SyncLobbyResponse::getCreated);
    }
}
