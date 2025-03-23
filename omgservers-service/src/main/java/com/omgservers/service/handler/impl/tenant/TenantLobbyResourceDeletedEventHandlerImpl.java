package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import com.omgservers.schema.module.tenant.tenantLobbyResource.GetTenantLobbyResourceRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.GetTenantLobbyResourceResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantLobbyResourceDeletedEventBodyModel;
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
public class TenantLobbyResourceDeletedEventHandlerImpl implements EventHandler {

    final TenantShard tenantShard;
    final LobbyShard lobbyShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_LOBBY_RESOURCE_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantLobbyResourceDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantLobbyResource(tenantId, id)
                .flatMap(tenantLobbyResource -> {
                    log.debug("Deleted, {}", tenantLobbyResource);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<TenantLobbyResourceModel> getTenantLobbyResource(final Long tenantId, final Long id) {
        final var request = new GetTenantLobbyResourceRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantLobbyResourceResponse::getTenantLobbyResource);
    }
}
