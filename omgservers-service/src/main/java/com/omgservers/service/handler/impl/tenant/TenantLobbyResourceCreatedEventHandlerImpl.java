package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
import com.omgservers.schema.module.tenant.tenantLobbyResource.GetTenantLobbyResourceRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.GetTenantLobbyResourceResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantLobbyResourceCreatedEventBodyModel;
import com.omgservers.service.factory.lobby.LobbyModelFactory;
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
public class TenantLobbyResourceCreatedEventHandlerImpl implements EventHandler {

    final TenantShard tenantShard;
    final LobbyShard lobbyShard;

    final LobbyModelFactory lobbyModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_LOBBY_RESOURCE_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantLobbyResourceCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantLobbyResource(tenantId, id)
                .flatMap(tenantLobbyResource -> {
                    log.debug("Created, {}", tenantLobbyResource);

                    return syncLobby(tenantLobbyResource, event.getIdempotencyKey());
                })
                .replaceWithVoid();
    }

    Uni<TenantLobbyResourceModel> getTenantLobbyResource(final Long tenantId, final Long id) {
        final var request = new GetTenantLobbyResourceRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantLobbyResourceResponse::getTenantLobbyResource);
    }

    Uni<Boolean> syncLobby(final TenantLobbyResourceModel tenantLobbyResource,
                           final String idempotencyKey) {
        final var tenantId = tenantLobbyResource.getTenantId();
        final var deploymentId = tenantLobbyResource.getDeploymentId();
        final var lobbyId = tenantLobbyResource.getLobbyId();
        final var lobby = lobbyModelFactory.create(lobbyId,
                tenantId,
                deploymentId,
                idempotencyKey);
        final var request = new SyncLobbyRequest(lobby);
        return lobbyShard.getService().syncLobbyWithIdempotency(request)
                .map(SyncLobbyResponse::getCreated);
    }
}
