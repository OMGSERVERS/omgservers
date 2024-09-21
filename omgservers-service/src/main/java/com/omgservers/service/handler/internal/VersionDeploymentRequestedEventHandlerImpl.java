package com.omgservers.service.handler.internal;

import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.VersionDeploymentRequestedEventBodyModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
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
public class VersionDeploymentRequestedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final TenantMatchmakerRequestModelFactory tenantMatchmakerRequestModelFactory;
    final TenantLobbyRequestModelFactory tenantLobbyRequestModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_DEPLOYMENT_REQUESTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionDeploymentRequestedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var versionId = body.getVersionId();

        return getVersion(tenantId, versionId)
                .flatMap(version -> {
                    log.info("Version deployment was requested, version={}/{}", tenantId, versionId);

                    final var idempotencyKey = event.getId().toString();

                    // TODO: creating lobby/matchmaker requests only if developer requested it
                    return syncVersionLobbyRequest(tenantId, versionId, idempotencyKey)
                            .flatMap(created -> syncVersionMatchmakerRequest(tenantId, versionId, idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<TenantVersionModel> getTenantVersion(final Long tenantId, final Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Boolean> syncVersionLobbyRequest(final Long tenantId,
                                         final Long versionId,
                                         final String idempotencyKey) {
        final var versionLobbyRequest = tenantLobbyRequestModelFactory.create(tenantId, versionId, idempotencyKey);
        final var request = new SyncTenantLobbyRequestRequest(versionLobbyRequest);
        return tenantModule.getTenantService().syncVersionLobbyRequestWithIdempotency(request)
                .map(SyncTenantLobbyRequestResponse::getCreated);
    }

    Uni<Boolean> syncVersionMatchmakerRequest(final Long tenantId,
                                              final Long versionId,
                                              final String idempotencyKey) {
        final var versionMatchmaker = tenantMatchmakerRequestModelFactory.create(tenantId, versionId, idempotencyKey);
        final var request = new SyncTenantMatchmakerRequestRequest(versionMatchmaker);
        return tenantModule.getTenantService().syncVersionMatchmakerRequestWithIdempotency(request)
                .map(SyncTenantMatchmakerRequestResponse::getCreated);
    }
}
