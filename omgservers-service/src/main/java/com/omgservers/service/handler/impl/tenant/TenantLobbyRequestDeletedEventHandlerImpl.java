package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantLobbyRequestDeletedEventBodyModel;
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
public class TenantLobbyRequestDeletedEventHandlerImpl implements EventHandler {

    final TenantShard tenantShard;
    final LobbyShard lobbyShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_LOBBY_REQUEST_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantLobbyRequestDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantLobbyRequest(tenantId, id)
                .flatMap(tenantLobbyRequest -> {
                    log.debug("Deleted, {}", tenantLobbyRequest);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<TenantLobbyRequestModel> getTenantLobbyRequest(final Long tenantId, final Long id) {
        final var request = new GetTenantLobbyRequestRequest(tenantId, id);
        return tenantShard.getService().getTenantLobbyRequest(request)
                .map(GetTenantLobbyRequestResponse::getTenantLobbyRequest);
    }
}
