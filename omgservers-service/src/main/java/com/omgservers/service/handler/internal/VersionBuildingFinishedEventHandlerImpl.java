package com.omgservers.service.handler.internal;

import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.DeleteTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.DeleteTenantJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.ViewTenantJenkinsRequestsRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.ViewTenantJenkinsRequestsResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.VersionBuildingFinishedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.service.event.EventService;
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
        final var tenantVersionId = body.getTenantVersionId();

        return getTenantVersion(tenantId, tenantVersionId)
                .flatMap(version -> {
                    log.info("Version building was finished, tenantVersion={}/{}", tenantId, tenantVersionId);

                    final var idempotencyKey = version.getIdempotencyKey();

                    return deleteTenantJenkinsRequests(tenantId, tenantVersionId);
                })
                .replaceWithVoid();
    }

    Uni<TenantVersionModel> getTenantVersion(final Long tenantId, final Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Void> deleteTenantJenkinsRequests(final Long tenantId, final Long tenantVersionId) {
        return viewTenantJenkinsRequests(tenantId, tenantVersionId)
                .flatMap(tenantJenkinsRequests -> Multi.createFrom().iterable(tenantJenkinsRequests)
                        .onItem().transformToUniAndConcatenate(tenantJenkinsRequest ->
                                deleteTenantJenkinsRequest(tenantId, tenantJenkinsRequest.getId()))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<TenantJenkinsRequestModel>> viewTenantJenkinsRequests(final Long tenantId,
                                                                   final Long tenantVersionId) {
        final var request = new ViewTenantJenkinsRequestsRequest(tenantId, tenantVersionId);
        return tenantModule.getTenantService().viewTenantJenkinsRequests(request)
                .map(ViewTenantJenkinsRequestsResponse::getTenantJenkinsRequests);
    }

    Uni<Boolean> deleteTenantJenkinsRequest(final Long tenantId, final Long id) {
        final var request = new DeleteTenantJenkinsRequestRequest(tenantId, id);
        return tenantModule.getTenantService().deleteTenantJenkinsRequest(request)
                .map(DeleteTenantJenkinsRequestResponse::getDeleted);
    }
}
