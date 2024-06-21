package com.omgservers.service.handler.tenant;

import com.omgservers.model.dto.tenant.versionJenkinsRequest.GetVersionJenkinsRequestRequest;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.GetVersionJenkinsRequestResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.tenant.VersionJenkinsRequestDeletedEventBodyModel;
import com.omgservers.model.versionJenkinsRequest.VersionJenkinsRequestModel;
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
public class VersionJenkinsRequestDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_JENKINS_REQUEST_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionJenkinsRequestDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionJenkinsRequest(tenantId, id)
                .flatMap(versionJenkinsRequest -> {
                    final var versionId = versionJenkinsRequest.getVersionId();
                    final var qualifier = versionJenkinsRequest.getQualifier();
                    log.info("Version jenkins request was deleted, id={}, version={}/{}, qualifier={}",
                            versionJenkinsRequest.getId(),
                            tenantId,
                            versionId,
                            qualifier);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<VersionJenkinsRequestModel> getVersionJenkinsRequest(final Long tenantId, final Long id) {
        final var request = new GetVersionJenkinsRequestRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionJenkinsRequest(request)
                .map(GetVersionJenkinsRequestResponse::getVersionJenkinsRequest);
    }
}
