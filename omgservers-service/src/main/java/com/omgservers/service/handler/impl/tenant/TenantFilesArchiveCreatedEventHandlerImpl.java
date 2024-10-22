package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveModel;
import com.omgservers.schema.module.tenant.tenantFilesArchive.GetTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.GetTenantFilesArchiveResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.VersionBuildingRequestedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantFilesArchiveCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.service.event.EventService;
import com.omgservers.service.service.event.dto.SyncEventRequest;
import com.omgservers.service.service.event.dto.SyncEventResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantFilesArchiveCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final EventService eventService;

    final EventModelFactory eventModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_FILES_ARCHIVE_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantFilesArchiveCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getTenantFilesArchive(tenantId, id)
                .flatMap(tenantFilesArchive -> {
                    log.info("Created, {}", tenantFilesArchive);

                    final var tenantVersionId = tenantFilesArchive.getVersionId();

                    return requestTenantVersionBuilding(tenantId, tenantVersionId, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<TenantFilesArchiveModel> getTenantFilesArchive(final Long tenantId, final Long id) {
        final var request = new GetTenantFilesArchiveRequest(tenantId, id);
        return tenantModule.getService().getTenantFilesArchive(request)
                .map(GetTenantFilesArchiveResponse::getTenantFilesArchive);
    }

    Uni<Boolean> requestTenantVersionBuilding(final Long tenantId,
                                              final Long tenantVersionId,
                                              final String idempotencyKey) {
        final var eventBody = new VersionBuildingRequestedEventBodyModel(tenantId, tenantVersionId);
        final var eventModel = eventModelFactory.create(eventBody,
                idempotencyKey + "/" + eventBody.getQualifier());

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return eventService.syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }
}
