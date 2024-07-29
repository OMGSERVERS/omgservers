package com.omgservers.service.handler.tenant;

import com.omgservers.schema.service.system.SyncEventRequest;
import com.omgservers.schema.service.system.SyncEventResponse;
import com.omgservers.schema.module.tenant.GetVersionRequest;
import com.omgservers.schema.module.tenant.GetVersionResponse;
import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.body.internal.VersionBuildingRequestedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.VersionCreatedEventBodyModel;
import com.omgservers.schema.model.version.VersionModeModel;
import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final SystemModule systemModule;
    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    final EventModelFactory eventModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var versionId = body.getId();

        return getVersion(tenantId, versionId)
                .flatMap(version -> {
                    log.info("Version was created, version={}/{}, stageId={}, modes={}, archiveSizeInBytes={}",
                            tenantId,
                            versionId,
                            version.getStageId(),
                            version.getConfig().getModes().stream().map(VersionModeModel::getName).toList(),
                            version.getBase64Archive().getBytes(StandardCharsets.UTF_8).length);

                    final var idempotencyKey = event.getId().toString();

                    // TODO: request building only if there is source code else request deployment
                    return requestVersionBuilding(tenantId, versionId, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<VersionModel> getVersion(final Long tenantId, final Long id) {
        final var request = new GetVersionRequest(tenantId, id);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }

    Uni<Boolean> requestVersionBuilding(final Long tenantId,
                                        final Long versionId,
                                        final String idempotencyKey) {
        final var eventBody = new VersionBuildingRequestedEventBodyModel(tenantId, versionId);
        final var eventModel = eventModelFactory.create(eventBody,
                idempotencyKey + "/" + eventBody.getQualifier());

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }
}
