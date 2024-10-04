package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantDeploymentCreatedEventBodyModel;
import com.omgservers.service.factory.tenant.TenantLobbyRequestModelFactory;
import com.omgservers.service.factory.tenant.TenantMatchmakerRequestModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantDeploymentCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final TenantMatchmakerRequestModelFactory tenantMatchmakerRequestModelFactory;
    final TenantLobbyRequestModelFactory tenantLobbyRequestModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_DEPLOYMENT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantDeploymentCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getTenantDeployment(tenantId, id)
                .flatMap(tenantDeployment -> {
                    log.info("Tenant deployment was created, tenantDeployment={}/{}, " +
                                    "tenantVersionId={}, " +
                                    "tenantStageId={}",
                            tenantId,
                            id,
                            tenantDeployment.getVersionId(),
                            tenantDeployment.getStageId());

                    // TODO: creating lobby/matchmaker requests only if developer requested it
                    return createTenantLobbyRequest(tenantId, id, idempotencyKey)
                            .flatMap(created -> createTenantMatchmakerRequest(tenantId, id, idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<TenantDeploymentModel> getTenantDeployment(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<Boolean> createTenantLobbyRequest(final Long tenantId,
                                          final Long tenantDeploymentId,
                                          final String idempotencyKey) {
        final var tenantLobbyRequest = tenantLobbyRequestModelFactory.create(tenantId,
                tenantDeploymentId,
                idempotencyKey);
        final var request = new SyncTenantLobbyRequestRequest(tenantLobbyRequest);
        return tenantModule.getTenantService().syncTenantLobbyRequestWithIdempotency(request)
                .map(SyncTenantLobbyRequestResponse::getCreated);
    }

    Uni<Boolean> createTenantMatchmakerRequest(final Long tenantId,
                                               final Long tenantDeploymentId,
                                               final String idempotencyKey) {
        final var tenantMatchmakerRequest = tenantMatchmakerRequestModelFactory
                .create(tenantId, tenantDeploymentId, idempotencyKey);
        final var request = new SyncTenantMatchmakerRequestRequest(tenantMatchmakerRequest);
        return tenantModule.getTenantService().syncTenantMatchmakerRequestWithIdempotency(request)
                .map(SyncTenantMatchmakerRequestResponse::getCreated);
    }
}
