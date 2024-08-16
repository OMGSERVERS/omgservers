package com.omgservers.service.handler.internal;

import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.schema.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import com.omgservers.schema.module.tenant.GetVersionRequest;
import com.omgservers.schema.module.tenant.GetVersionResponse;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.DeleteVersionJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.DeleteVersionJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.ViewVersionJenkinsRequestsRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.ViewVersionJenkinsRequestsResponse;
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
        final var versionId = body.getVersionId();

        return getVersion(tenantId, versionId)
                .flatMap(version -> {
                    log.info("Version building was failed, version={}/{}", tenantId, versionId);

                    return deleteVersionJenkinsRequests(tenantId, versionId);
                })
                .replaceWithVoid();
    }

    Uni<VersionModel> getVersion(final Long tenantId, final Long id) {
        final var request = new GetVersionRequest(tenantId, id);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }

    Uni<Void> deleteVersionJenkinsRequests(final Long tenantId, final Long versionId) {
        return viewVersionJenkinsRequests(tenantId, versionId)
                .flatMap(versionJenkinsRequests -> Multi.createFrom().iterable(versionJenkinsRequests)
                        .onItem().transformToUniAndConcatenate(versionJenkinsRequest ->
                                deleteVersionJenkinsRequest(tenantId, versionJenkinsRequest.getId()))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<VersionJenkinsRequestModel>> viewVersionJenkinsRequests(final Long tenantId,
                                                                     final Long versionId) {
        final var request = new ViewVersionJenkinsRequestsRequest(tenantId, versionId);
        return tenantModule.getVersionService().viewVersionJenkinsRequests(request)
                .map(ViewVersionJenkinsRequestsResponse::getVersionJenkinsRequests);
    }

    Uni<Boolean> deleteVersionJenkinsRequest(final Long tenantId, final Long id) {
        final var request = new DeleteVersionJenkinsRequestRequest(tenantId, id);
        return tenantModule.getVersionService().deleteVersionJenkinsRequest(request)
                .map(DeleteVersionJenkinsRequestResponse::getDeleted);
    }
}
