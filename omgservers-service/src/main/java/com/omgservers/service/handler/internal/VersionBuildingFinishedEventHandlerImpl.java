package com.omgservers.service.handler.internal;

import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestModel;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.DeleteTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.DeleteTenantJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.ViewTenantJenkinsRequestsRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.ViewTenantJenkinsRequestsResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.VersionBuildingFinishedEventBodyModel;
import com.omgservers.service.event.body.internal.VersionDeploymentRequestedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.service.event.EventService;
import com.omgservers.service.service.event.dto.SyncEventRequest;
import com.omgservers.service.service.event.dto.SyncEventResponse;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionBuildingFinishedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final EventService eventService;

    final EventModelFactory eventModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_BUILDING_FINISHED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionBuildingFinishedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var versionId = body.getVersionId();

        return getVersion(tenantId, versionId)
                .flatMap(version -> {
                    log.info("Version building was finished, version={}/{}", tenantId, versionId);

                    final var idempotencyKey = version.getIdempotencyKey();

                    return deleteVersionJenkinsRequests(tenantId, versionId)
                            .flatMap(voidItem -> requestVersionDeployment(tenantId, versionId, idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<TenantVersionModel> getTenantVersion(final Long tenantId, final Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Boolean> requestVersionDeployment(final Long tenantId,
                                          final Long versionId,
                                          final String idempotencyKey) {
        final var eventBody = new VersionDeploymentRequestedEventBodyModel(tenantId, versionId);
        final var eventModel = eventModelFactory.create(eventBody,
                idempotencyKey + "/" + eventBody.getQualifier());

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return eventService.syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }

    Uni<Void> deleteVersionJenkinsRequests(final Long tenantId, final Long versionId) {
        return viewVersionJenkinsRequests(tenantId, versionId)
                .flatMap(versionJenkinsRequests -> Multi.createFrom().iterable(versionJenkinsRequests)
                        .onItem().transformToUniAndConcatenate(versionJenkinsRequest ->
                                deleteVersionJenkinsRequest(tenantId, versionJenkinsRequest.getId()))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<TenantJenkinsRequestModel>> viewVersionJenkinsRequests(final Long tenantId,
                                                                    final Long versionId) {
        final var request = new ViewTenantJenkinsRequestsRequest(tenantId, versionId);
        return tenantModule.getTenantService().viewVersionJenkinsRequests(request)
                .map(ViewTenantJenkinsRequestsResponse::getVersionJenkinsRequests);
    }

    Uni<Boolean> deleteVersionJenkinsRequest(final Long tenantId, final Long id) {
        final var request = new DeleteTenantJenkinsRequestRequest(tenantId, id);
        return tenantModule.getTenantService().deleteVersionJenkinsRequest(request)
                .map(DeleteTenantJenkinsRequestResponse::getDeleted);
    }
}
