package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantLobbyRequestDeletedEventBodyModel;
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
public class TenantLobbyRequestDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_LOBBY_REQUEST_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantLobbyRequestDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantLobbyRequest(tenantId, id)
                .flatMap(tenantLobbyRequest -> {
                    final var deploymentId = tenantLobbyRequest.getDeploymentId();
                    final var lobbyId = tenantLobbyRequest.getLobbyId();
                    log.info("Tenant lobby request was deleted, id={}, deploymentId={}/{}, lobbyId={}",
                            tenantLobbyRequest.getId(),
                            tenantId,
                            deploymentId,
                            lobbyId);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<TenantLobbyRequestModel> getTenantLobbyRequest(final Long tenantId, final Long id) {
        final var request = new GetTenantLobbyRequestRequest(tenantId, id);
        return tenantModule.getService().getTenantLobbyRequest(request)
                .map(GetTenantLobbyRequestResponse::getTenantLobbyRequest);
    }
}
