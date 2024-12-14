package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.GetTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.GetTenantMatchmakerRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantMatchmakerRefDeletedEventBodyModel;
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
public class TenantMatchmakerRefDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_MATCHMAKER_REF_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantMatchmakerRefDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantMatchmakerRef(tenantId, id)
                .flatMap(tenantMatchmakerRef -> {
                    log.debug("Deleted, {}", tenantMatchmakerRef);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<TenantMatchmakerRefModel> getTenantMatchmakerRef(final Long tenantId, final Long id) {
        final var request = new GetTenantMatchmakerRefRequest(tenantId, id);
        return tenantModule.getService().getTenantMatchmakerRef(request)
                .map(GetTenantMatchmakerRefResponse::getTenantMatchmakerRef);
    }
}
