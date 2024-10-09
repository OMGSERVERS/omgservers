package com.omgservers.service.handler.impl.internal;

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
import com.omgservers.service.event.body.internal.VersionBuildingFailedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
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
public class VersionBuildingFailedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final EventModelFactory eventModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_BUILDING_FAILED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionBuildingFailedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var tenantVersionId = body.getTenantVersionId();

        return getTenantVersion(tenantId, tenantVersionId)
                .flatMap(tenantVersion -> {
                    log.info("Version building was failed, tenantVersion={}/{}", tenantId, tenantVersionId);

                    return deleteTenantJenkinsRequests(tenantId, tenantVersionId);
                })
                .replaceWithVoid();
    }

    Uni<TenantVersionModel> getTenantVersion(final Long tenantId, final Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantModule.getService().getTenantVersion(request)
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
        return tenantModule.getService().viewTenantJenkinsRequests(request)
                .map(ViewTenantJenkinsRequestsResponse::getTenantJenkinsRequests);
    }

    Uni<Boolean> deleteTenantJenkinsRequest(final Long tenantId, final Long id) {
        final var request = new DeleteTenantJenkinsRequestRequest(tenantId, id);
        return tenantModule.getService().deleteTenantJenkinsRequest(request)
                .map(DeleteTenantJenkinsRequestResponse::getDeleted);
    }
}
