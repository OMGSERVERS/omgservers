package com.omgservers.service.handler.internal;

import com.omgservers.schema.module.tenant.GetVersionRequest;
import com.omgservers.schema.module.tenant.GetVersionResponse;
import com.omgservers.schema.module.tenant.SyncVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.SyncVersionLobbyRequestResponse;
import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRequestResponse;
import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.body.internal.VersionDeploymentRequestedEventBodyModel;
import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.service.factory.tenant.VersionLobbyRequestModelFactory;
import com.omgservers.service.factory.tenant.VersionMatchmakerRequestModelFactory;
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

    final VersionMatchmakerRequestModelFactory versionMatchmakerRequestModelFactory;
    final VersionLobbyRequestModelFactory versionLobbyRequestModelFactory;

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

    Uni<VersionModel> getVersion(final Long tenantId, final Long id) {
        final var request = new GetVersionRequest(tenantId, id);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }

    Uni<Boolean> syncVersionLobbyRequest(final Long tenantId,
                                         final Long versionId,
                                         final String idempotencyKey) {
        final var versionLobbyRequest = versionLobbyRequestModelFactory.create(tenantId, versionId, idempotencyKey);
        final var request = new SyncVersionLobbyRequestRequest(versionLobbyRequest);
        return tenantModule.getVersionService().syncVersionLobbyRequestWithIdempotency(request)
                .map(SyncVersionLobbyRequestResponse::getCreated);
    }

    Uni<Boolean> syncVersionMatchmakerRequest(final Long tenantId,
                                              final Long versionId,
                                              final String idempotencyKey) {
        final var versionMatchmaker = versionMatchmakerRequestModelFactory.create(tenantId, versionId, idempotencyKey);
        final var request = new SyncVersionMatchmakerRequestRequest(versionMatchmaker);
        return tenantModule.getVersionService().syncVersionMatchmakerRequestWithIdempotency(request)
                .map(SyncVersionMatchmakerRequestResponse::getCreated);
    }
}
