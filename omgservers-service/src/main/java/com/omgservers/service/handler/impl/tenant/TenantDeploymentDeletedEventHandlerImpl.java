package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantDeploymentDeletedEventBodyModel;
import com.omgservers.service.factory.tenant.TenantLobbyRequestModelFactory;
import com.omgservers.service.factory.tenant.TenantMatchmakerRequestModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.handler.operation.DeleteTenantLobbiesByTenantDeploymentIdOperation;
import com.omgservers.service.handler.operation.DeleteTenantLobbyRequestsByTenantDeploymentIdOperation;
import com.omgservers.service.handler.operation.DeleteTenantMatchmakerRequestsByTenantDeploymentIdOperation;
import com.omgservers.service.handler.operation.DeleteTenantMatchmakersByTenantDeploymentIdOperation;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantDeploymentDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    final DeleteTenantLobbyRequestsByTenantDeploymentIdOperation
            deleteTenantLobbyRequestsByTenantDeploymentIdOperation;
    final DeleteTenantMatchmakersByTenantDeploymentIdOperation
            deleteTenantMatchmakersByTenantDeploymentIdOperation;
    final DeleteTenantLobbiesByTenantDeploymentIdOperation
            deleteTenantLobbiesByTenantDeploymentIdOperation;
    final DeleteTenantMatchmakerRequestsByTenantDeploymentIdOperation
            deleteTenantMatchmakerRequestsByTenantDeploymentIdOperation;

    final TenantMatchmakerRequestModelFactory tenantMatchmakerRequestModelFactory;
    final TenantLobbyRequestModelFactory tenantLobbyRequestModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_DEPLOYMENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantDeploymentDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var tenantDeploymentId = body.getId();

        return getTenantDeployment(tenantId, tenantDeploymentId)
                .flatMap(tenantDeployment -> {
                    log.debug("Deleted, {}", tenantDeployment);

                    return deleteTenantLobbyRequestsByTenantDeploymentIdOperation.execute(tenantId, tenantDeploymentId)
                            .flatMap(voidItem -> deleteTenantLobbiesByTenantDeploymentIdOperation
                                    .execute(tenantId, tenantDeploymentId))
                            .flatMap(voidItem -> deleteTenantMatchmakerRequestsByTenantDeploymentIdOperation
                                    .execute(tenantId, tenantDeploymentId))
                            .flatMap(voidItem -> deleteTenantMatchmakersByTenantDeploymentIdOperation
                                    .execute(tenantId, tenantDeploymentId));
                })
                .replaceWithVoid();
    }

    Uni<TenantDeploymentModel> getTenantDeployment(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantModule.getService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }
}
